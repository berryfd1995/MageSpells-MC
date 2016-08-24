package me.L2_Envy.MSRM.Core.LevelingSystem;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.PluginManager.PluginManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Daniel on 8/9/2016.
 */
public class LevelingManager {
    private MageSpellsManager mageSpellsManager;
    private boolean enableleveling;
    private HashMap<EntityType, Integer> mobexperience;
    private int playerexperience;
    private HashMap<Integer, Long> levelsystem;

    public LevelingManager() {
        mobexperience = new HashMap<>();
        levelsystem = new HashMap<>();
    }

    public void link(MageSpellsManager mageSpellsManager) {
        this.mageSpellsManager = mageSpellsManager;
    }

    public boolean isLevelingEnabled() {
        return enableleveling;
    }

    public void addMobExperience(EntityType entityType, int experience) {
        if (!mobexperience.containsKey(entityType)) {
            mobexperience.put(entityType, experience);
        }
    }

    public void addLevel(int level, Long requiredexperience) {
        if (!levelsystem.containsKey(level)) {
            levelsystem.put(level, requiredexperience);
        }
    }

    public void setEnableleveling(boolean enabled) {
        enableleveling = enabled;
    }

    public void setPlayerexperience(int experience) {
        playerexperience = experience;
    }

    private long getExperience(EntityType entityType) {
        if (entityType.equals(EntityType.PLAYER)) {
            return playerexperience;
        } else {
            if (mobexperience.containsKey(entityType)) {
                return mobexperience.get(entityType);
            }
        }
        return 0;
    }

    private int getRank(Long experiencepoints) {
        int rank = 1;
        for (Integer i : levelsystem.keySet()) {
            if (experiencepoints > levelsystem.get(i)) {
                rank = i;
            }
        }
        return rank;
    }
    public void giveExperience(PlayerObject playerObject, Long experiencepoints){
        playerObject.addExperience(experiencepoints);
        playerObject.setRank(getRank(playerObject.getExperience()));
    }
    public void giveExperience(PlayerObject playerObject, EntityType entityType){
        System.out.println(getExperience(entityType));
        playerObject.addExperience(getExperience(entityType));
        playerObject.setRank(getRank(playerObject.getExperience()));
    }
    public void giveExperience(CommandSender sender, String reciever, Long experiencepoints){
        OfflinePlayer offlinePlayer = mageSpellsManager.main.utils.getOfflinePlayerFromName(reciever);
        if (offlinePlayer != null) {
            if (offlinePlayer.isOnline()) {
                Player player1 = (Player) offlinePlayer;
                PlayerObject playerObject1 =mageSpellsManager.mageManager.getMage(player1.getUniqueId());
                if (playerObject1 != null) {
                    playerObject1.addExperience(experiencepoints);
                    playerObject1.setRank(getRank(playerObject1.getExperience()));
                } else {
                    sender.sendMessage("Could not find player: " + reciever);
                }
            } else {
                PlayerObject playerObject1 = mageSpellsManager.mageManager.loadOfflineMage(reciever);
                if (playerObject1 != null) {
                    playerObject1.addExperience(experiencepoints);
                    playerObject1.setRank(getRank(playerObject1.getExperience()));
                    mageSpellsManager.main.pluginManager.playerConfig.savePlayerData(playerObject1);
                } else {
                    sender.sendMessage("Could not find player: " + reciever);
                }
            }
        } else {
            sender.sendMessage("Could not find player: " + reciever);
        }
    }
}
