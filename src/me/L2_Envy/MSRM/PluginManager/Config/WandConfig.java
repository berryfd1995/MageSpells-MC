package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                            //main.logger.info(wandData.getWandName() + " loaded.");
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
            firstLoad("BasicWand.yml");
            firstLoad("AirStaff.yml");
            firstLoad("DarkStaff.yml");
            firstLoad("LightStaff.yml");
            firstLoad("WaterStaff.yml");
            firstLoad("FireStaff.yml");
            firstLoad("EarthStaff.yml");
            loadWandConfigs();
        }
    }
    public void firstLoad(String wand) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(wand);
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
            ItemStack[] matrix = new ItemStack[9];
            for(int i = 0; i < 9; i++){
                matrix[i] = main.utils.getItemStack(materials[i]);
            }
            boolean mobdropsenabled = config.getBoolean("MobDrops.Settings.Enable");
            HashMap<EntityType, Double> mobDrops = new HashMap<>();
            if(mobdropsenabled){
                for(String mobtype : config.getConfigurationSection("MobDrops.Drops.").getKeys(false)){
                    EntityType entityType;
                    try {
                        entityType = EntityType.valueOf(mobtype.toUpperCase());
                    } catch (Exception e) {
                        entityType = null;
                    }
                    if (entityType != null) {
                        double chance = config.getDouble("MobDrops.Drops." + mobtype + ".Chance");
                        mobDrops.put(entityType, chance);
                    } else {
                        System.out.println("Could not register mob: " + mobtype);
                        System.out.println("Please make sure it is a valid mob!");
                    }
                }
            }
            String wandnode = config.getString("WandNode");
            ArrayList<String> compatiblespells = (ArrayList<String>)config.getStringList("CompatibleSpellNodeList");
            return new WandObject(wandName,displayname,requiredleveltocraft,requiredleveltouse,requiredleveltobind,
                    wand, shapedRecipe,mobdropsenabled, mobDrops, wandnode, compatiblespells, matrix);
        }catch(Exception ex) {
            return null;
        }
    }
}
