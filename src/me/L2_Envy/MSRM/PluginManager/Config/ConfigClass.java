package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.CustomItemObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
 * Created by Daniel on 7/23/2016.
 */
public class ConfigClass {
    public Main main;
    private FileConfiguration config;
    public ConfigClass(){

    }
    public void link(Main main){
        this.main = main;
    }
    public void loadOtherConfigs() {
        File location = new File(main.getDataFolder() + "/Extras/");
        if (location.exists()) {
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        String fileName  = FilenameUtils.getBaseName(fileData.getName());
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                        if(fileName == "Messages"){
                            loadMessages(config);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error with file");
                    e.printStackTrace();
                }
            }
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating Extras Folder...");
            location.mkdirs();
            export("Messages.yml");
        }
    }
    public void export(String name) {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(name);
            File efile = new File("plugins/MageSpellsRemastered/Extras/", entry.getName());

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
    public boolean loadDefaultConfig(){
        try {
            FileConfiguration config = main.getConfig();
            String path = "Settings.GameplaySettings.";
            boolean enableleveling = config.getBoolean(path + "EnableLeveling");
            main.mageSpellsManager.levelingManager.setEnableleveling(enableleveling);
            for (String entity : config.getConfigurationSection(path + "MobExperience.").getKeys(false)){
                try{
                    EntityType entityType = EntityType.valueOf(entity.toUpperCase());
                    int experience = config.getInt(path + "MobExperience." + entity);
                    main.mageSpellsManager.levelingManager.addMobExperience(entityType,experience);
                }catch(Exception ex){
                    ex.printStackTrace();
                    main.logger.info("Could not register entity " + entity);
                }
            }
            if(enableleveling) {
                int playerexperience = config.getInt(path + "ExpFromPlayers");
                main.mageSpellsManager.levelingManager.setPlayerexperience(playerexperience);
                for (String levelstr : config.getConfigurationSection(path + "LevelSystem.").getKeys(false)) {
                    try {
                        int level = Integer.parseInt(levelstr);
                        long experience = config.getLong(path + "LevelSystem." + levelstr);
                        main.mageSpellsManager.levelingManager.addLevel(level, experience);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        main.logger.info("Could not add level " + levelstr);
                    }
                }
                for (String levelmana : config.getConfigurationSection(path + "ManaLevelSystem").getKeys(false)) {
                    try {
                        int level = Integer.parseInt(levelmana);
                        int manaamount = config.getInt(path + "ManaLevelSystem." + levelmana);
                        main.mageSpellsManager.manaManager.addManaLevel(level, manaamount);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        main.logger.info("Could not add Mana level ");
                    }
                }
            }
            boolean enabledlearning = config.getBoolean(path + "EnableLearning");
            boolean enableSpellBookLearning = config.getBoolean(path + "EnableSpellBookLearning");
            boolean wandlearning = config.getBoolean(path + "EnableWandLearning");
            boolean enablenodesystem = config.getBoolean(path + "EnableNodeSystem");
            boolean enablewandbag;
            if(!config.contains(path + "EnableWandBag")) {
                config.createSection(path +"EnableWandBag");
                config.set(path + "EnableWandBag", true);
            }
            enablewandbag = config.getBoolean(path + "EnableWandBag");
            main.mageSpellsManager.setNodeSystemEnabled(enablenodesystem);
            main.mageSpellsManager.spellLearningManager.setEnablelearning(enabledlearning);
            main.mageSpellsManager.spellLearningManager.setEnableSpellBookLearning(enableSpellBookLearning);
            main.mageSpellsManager.wandManager.setEnableWandLearning(wandlearning);
            main.mageSpellsManager.wandBag.setEnabled(enablewandbag);
            boolean usercreatesteam = config.getBoolean("Settings.TeamSettings.UserCreatesTeam");
            main.mageSpellsManager.teamManager.setUsercreatesteam(usercreatesteam);
            //Mana
            int maxamountofmana = config.getInt("Settings.MaxManaAmount");
            int manaregenrate = config.getInt("Settings.ManaRegenRate");
            String inactivecolor = config.getString("Settings.Colors.InactiveMana");
            String activecolor = config.getString("Settings.Colors.ActiveMana");
            String low = config.getString("Settings.Colors.LowMana");
            String brackets = config.getString("Settings.Colors.ManaBrackets");
            String chargestatus = config.getString("Settings.Colors.ChargeStatusBar");
            String chargeleft = config.getString("Settings.Colors.ChargeLeftBar");
            String chargebrackets = config.getString("Settings.Colors.ChargeBracket");
            String cooldownstatus = config.getString("Settings.Colors.CooldownStatusBar");
            String cooldownleft = config.getString("Settings.Colors.CooldownLeftBar");
            String cooldownbracket = config.getString("Settings.Colors.CooldownBracket");
            boolean displayexactvalue = config.getBoolean("Settings.DisplayExactValue");
            main.mageSpellsManager.manaManager.defineSettings(maxamountofmana,manaregenrate,inactivecolor,activecolor,low,brackets,chargestatus,chargeleft,chargebrackets, cooldownstatus, cooldownleft, cooldownbracket,displayexactvalue);
            main.logger.info("Main configuration file loaded.");
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            main.logger.info("Could not load main configuration file. Please check your config.");
            return false;
        }
    }
    public void loadMessages(YamlConfiguration config) {

    }

}
