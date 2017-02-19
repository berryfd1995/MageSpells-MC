package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Alchemy.Objects.MagePotion;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 2/17/2017.
 */
public class PotionConfig {
    public Main main;
    public PotionConfig(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void loadPotionConfigs() {
       /* File location = new File(main.getDataFolder() + "/Potions/");
        if (location.exists()) {
            main.logger.info("Loading Potions...");
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                        String potionName  = FilenameUtils.getBaseName(fileData.getName());
                        MagePotion magePotion = deformatData(potionName, config);
                        if(magePotion != null) {
                            main.alchemyManager.addCustomPotion(magePotion);
                            //main.logger.info(wandData.getWandName() + " loaded.");
                        }else{
                            main.logger.info("Error loading " + potionName);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error with file " + fileData.getName());
                    e.printStackTrace();
                }
            }
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating Potions Folder...");
            location.mkdirs();
            //firstLoad("BasicWand.yml");
            loadPotionConfigs();
        }*/
    }
    public void firstLoad(String wand) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(wand);
            File efile = new File("plugins/MageSpellsRemastered/Potions/", entry.getName());

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
    public MagePotion deformatData(String potionName, YamlConfiguration config) {
        try {
            return null;
        }catch(Exception ex) {
            return null;
        }
    }
}
