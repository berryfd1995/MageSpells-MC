package me.L2_Envy.MSRM.PluginManager.Config;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_10_R1.Overridden;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Daniel on 7/25/2016.
 */
public class PlayerConfig {
    public Main main;
    public PlayerConfig(){

    }
    public void link(Main main){
        this.main = main;
    }

    public void checkFolder() {
        File location = new File(main.getDataFolder() + "/PlayerData/");
        if (location.exists()) {
            //deformats, saves to pregamedata
        } else {
            main.logger.info("Creating PlayerData Folder...");
            location.mkdirs();
            //firstLoad();
        }
    }
    public void firstLoad() {
        try {
            String home = getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .getPath().replaceAll("%20", " ");
            JarFile jar = new JarFile(home);
            ZipEntry entry = jar.getEntry(".yml");
            File efile = new File("plugins/MageSpellsRemastered/PlayerData/", entry.getName());

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
    public void loadPlayerData(String name, UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(main, () ->{
            boolean found = false;
            File location = new File(main.getDataFolder() + "/PlayerData/");
            if (location.exists()) {
                for (File fileData : location.listFiles()) {
                    try {
                        if(!fileData.isHidden()) {
                            String playerUUID  = FilenameUtils.getBaseName(fileData.getName());
                            UUID uuid1 = UUID.fromString(playerUUID);
                            if(uuid.equals(uuid1)) {
                                YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                                PlayerObject playerObject = deformatData(config, fileData);
                                if(playerObject != null){
                                    main.mageSpellsManager.mageManager.addMage(playerObject);
                                    main.mageSpellsManager.manaManager.scheduleManaTask(playerObject);
                                }
                                found = true;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("File " + fileData.getName() + " corrupted.");
                        e.printStackTrace();
                    }
                }
                if(!found) {
                    createPlayerData(name, uuid);
                }
            } else {
                location.mkdirs();
                firstLoad();
            }
        });
    }
    public PlayerObject loadPlayerData(UUID uuid){
        File location = new File(main.getDataFolder() + "/PlayerData/");
        if (location.exists()) {
            for (File fileData : location.listFiles()) {
                try {
                    if(!fileData.isHidden()) {
                        String playerUUID  = FilenameUtils.getBaseName(fileData.getName());
                        UUID uuid1 = UUID.fromString(playerUUID);
                        if(uuid.equals(uuid1)) {
                            YamlConfiguration config = YamlConfiguration.loadConfiguration(fileData);
                            PlayerObject playerObject = deformatData(config, fileData);
                            if(playerObject != null){
                                return playerObject;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("File " + fileData.getName() + " corrupted.");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private void createPlayerData(String name, UUID uuid){
        PlayerObject playerObject;
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Wand Bag");
        File playerFile = createPlayerFile(uuid);
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerObject = new PlayerObject(name,uuid,1,0L,playerFile,playerConfig,new ArrayList<>(), new ArrayList<>(),inventory);
        main.mageSpellsManager.mageManager.addMage(playerObject);
        savePlayerData(playerObject);
        main.mageSpellsManager.manaManager.scheduleManaTask(playerObject);
    }
    public void savePlayerData(PlayerObject playerObject){
        Bukkit.getScheduler().runTaskAsynchronously(main,() -> {
            try {
                formatData(playerObject);
                playerObject.getPlayerconfig().save(playerObject.getPlayerFile());
                main.mageSpellsManager.mageManager.removeMage(playerObject);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    public void savePlayerDataNOW(PlayerObject playerObject){
        try {
            formatData(playerObject);
            playerObject.getPlayerconfig().save(playerObject.getPlayerFile());
            main.mageSpellsManager.mageManager.removeMage(playerObject);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private PlayerObject deformatData(YamlConfiguration config, File playerfile) {
        try {
            String name;
            if(config.contains("Name")){
                name = config.getString("Name");
            }else{
                return null;
            }
            UUID uuid;
            if(config.contains("UUID")){
                uuid = UUID.fromString(config.getString("UUID"));
            }else{
                return null;
            }
            int level =1;
            if(config.contains("Level")){
                level = config.getInt("Level");
            }
            long experience = 0;
            if(config.contains("Experience")){
                experience = config.getLong("Experience");
            }
            ArrayList<SpellObject> spellObjects =  new ArrayList<>();
            if(config.contains("SpellsLearned")){
                ArrayList<String> spellnames = (ArrayList) config.getStringList("SpellsLearned");
                for(String spell : spellnames){
                    SpellObject spellob =main.mageSpellsManager.spellManager.getSpellFromName(spell);
                    if(spellob != null){
                        spellObjects.add(spellob);
                    }
                }
            }
            ArrayList<WandObject> wandObjects =  new ArrayList<>();
            if(config.contains("WandsLearned")){
                ArrayList<String> wandnames = (ArrayList) config.getStringList("WandsLearned");
                for(String wand : wandnames){
                    WandObject wandObj =main.mageSpellsManager.wandManager.getWandFromName(wand);
                    if(wandObj != null){
                        wandObjects.add(wandObj);
                    }
                }
            }
            Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Wand Bag");
            if(config.contains("WandBag")){
                inventory.setContents(ItemSerialization.fromBase64(config.getString("WandBag")).getContents());
            }
            return new PlayerObject(name,uuid,level,experience,playerfile,config,spellObjects, wandObjects,inventory);
        }catch(Exception ex) {
            return null;
        }
    }
    private void formatData(PlayerObject playerObject){
        if (playerObject.getName() != null) {
            playerObject.getPlayerconfig().createSection("Name");
            playerObject.getPlayerconfig().set("Name", main.utils.getOfflinePlayerFromUUID(playerObject.getUuid()).getName());
        }
        if(playerObject.getUuid() != null){
            playerObject.getPlayerconfig().createSection("UUID");
            playerObject.getPlayerconfig().set("UUID", playerObject.getUuid().toString());
        }
        playerObject.getPlayerconfig().createSection("Level");
        playerObject.getPlayerconfig().set("Level",playerObject.getLevel());
        playerObject.getPlayerconfig().createSection("Experience");
        playerObject.getPlayerconfig().set("Experience", playerObject.getExperience());
        if(playerObject.getSpellObjects() != null){
            if(!playerObject.getSpellObjects().isEmpty()){
                playerObject.getPlayerconfig().createSection("SpellsLearned");
                ArrayList<String> spells = new ArrayList<>();
                for(SpellObject spellObject : playerObject.getSpellObjects()){
                    spells.add(spellObject.getName());
                }
                playerObject.getPlayerconfig().set("SpellsLearned", spells);
            }
        }
        if(playerObject.getWandObjects() != null){
            if(!playerObject.getWandObjects().isEmpty()){
                playerObject.getPlayerconfig().createSection("WandsLearned");
                ArrayList<String> wands = new ArrayList<>();
                for(WandObject wandObject : playerObject.getWandObjects()){
                    wands.add(wandObject.getWandName());
                }
                playerObject.getPlayerconfig().set("WandsLearned", wands);
            }
        }
        if(playerObject.getInventory() != null){
            playerObject.getPlayerconfig().createSection("WandBag");
            playerObject.getPlayerconfig().set("WandBag", ItemSerialization.toBase64(playerObject.getInventory()));
        }
    }
    private File createPlayerFile(UUID uuid) {
        try {
            File gameFile = new File(main.getDataFolder() + "/PlayerData/", uuid.toString() + ".yml");
            if (!gameFile.exists()) {
                gameFile.createNewFile();
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            });
            return gameFile;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    static class ItemSerialization {
        public static String toBase64(Inventory inventory) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

                // Write the size of the inventory
                dataOutput.writeInt(inventory.getSize());

                // Save every element in the list
                for (int i = 0; i < inventory.getSize(); i++) {
                    dataOutput.writeObject(inventory.getItem(i));
                }

                // Serialize that array
                dataOutput.close();
                return Base64Coder.encodeLines(outputStream.toByteArray());
            } catch (Exception e) {
                throw new IllegalStateException("Unable to save item stacks.", e);
            }
        }

        public static Inventory fromBase64(String data) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
                BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
                Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

                // Read the serialized inventory
                for (int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, (ItemStack) dataInput.readObject());
                }
                dataInput.close();
                return inventory;
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Could not load inventory");
                e.printStackTrace();
            }
            return null;
        }
    }
}
