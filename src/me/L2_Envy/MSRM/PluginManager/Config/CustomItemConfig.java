package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.CustomItemObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.*;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 8/9/2016.
 */
public class CustomItemConfig {
    public Main main;
    public CustomItemConfig(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void loadCustomItemConfigs() {
        File location = new File(main.getDataFolder() + "/CustomItems/");
        if (location.exists()) {
            main.logger.info("Loading Custom Items...");
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                        String itemname  = FilenameUtils.getBaseName(fileData.getName());
                        CustomItemObject itemData = deformatData(itemname, config);
                        if(itemData != null) {
                            main.utils.addCustomItemObject(itemData);
                            main.logger.info(itemData.getItemname() + " loaded.");
                        }else{
                            main.logger.info("Error loading " + itemname);
                        }
                    }
                } catch (Exception e) {
                    main.logger.info("Error with file " + fileData.getName());
                    e.printStackTrace();
                }
            }
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating Custom Items Folder...");
            location.mkdirs();
            firstLoad("Blood.yml");
            firstLoad("Heart_Of_Ice.yml");
            firstLoad("EarthRune.yml");
            firstLoad("AirRune.yml");
            firstLoad("WaterRune.yml");
            firstLoad("FireRune.yml");
            loadCustomItemConfigs();
        }
    }
    public void firstLoad(String item) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(item);
            File efile = new File("plugins/MageSpellsRemastered/CustomItems/", entry.getName());

            InputStream in =
                    new BufferedInputStream(jar.getInputStream(entry));
            OutputStream out =
                    new BufferedOutputStream(new FileOutputStream(efile));
            byte[] buffer = new byte[2048];
            for (; ; ) {
                int nBytes = in.read(buffer);
                if (nBytes <= 0) break;
                out.write(buffer, 0, nBytes);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public CustomItemObject deformatData(String itemname, YamlConfiguration config) {
        try {
            String displayname = main.utils.colorize(config.getString("Name"));
            String lore = main.utils.colorize(config.getString("Lore"));
            String material = config.getString("Material");
            String recipe = config.getString("Recipe");
            String[] recipearray = recipe.split(",");
            int requiredlevel = config.getInt("RequiredLevel");
            ItemStack itemStack = main.utils.getItemStack(material,displayname,lore);
            ShapedRecipe shapedRecipe = main.utils.loadRecipie(recipearray, itemStack);
            return new CustomItemObject(itemname,requiredlevel,itemStack,shapedRecipe);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
