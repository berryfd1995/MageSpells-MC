package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 7/25/2016.
 */
public class WandConfig {
    public Main main;
    public WandConfig(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void loadWandConfigs() {
        File location = new File(main.getDataFolder() + "/Wands/");
        if (location.exists()) {
            main.logger.info("Loading Wands...");
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                        String wandName  = FilenameUtils.getBaseName(fileData.getName());
                        WandObject wandData = deformatData(wandName, config);
                        if(wandData != null) {
                            main.mageSpellsManager.wandManager.addWandObject(wandData);
                            main.logger.info(wandData.getWandName() + " loaded.");
                        }else{
                            main.logger.info("Error loading " + wandName);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error with file " + fileData.getName());
                    e.printStackTrace();
                }
            }
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating Wands Folder...");
            location.mkdirs();
            firstLoad();
            loadWandConfigs();
        }
    }
    public void firstLoad() {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry("BasicWand.yml");
            File efile = new File("plugins/MageSpellsRemastered/Wands/", entry.getName());

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
    public WandObject deformatData(String wandName, YamlConfiguration config) {
        try {
            String displayname = main.utils.colorize(config.getString("Name"));
            int requiredleveltocraft = config.getInt("RequiredLevelToCraft");
            int requiredleveltouse = config.getInt("RequiredLevelToUse");
            int requiredleveltobind = config.getInt("RequiredLevelToBind");
            String wandtype = config.getString("Wand_Material");
            String wandrecipe = config.getString("Recipe");
            ItemStack wand = main.utils.getItemStack(wandtype, displayname);
            String[] materials = wandrecipe.split(",");
            ShapedRecipe shapedRecipe = main.utils.loadRecipie(materials, wand);
            return new WandObject(wandName,displayname,requiredleveltocraft,requiredleveltouse,requiredleveltobind,wand, shapedRecipe);
        }catch(Exception ex) {
            return null;
        }
    }
}
