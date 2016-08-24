package me.L2_Envy.MSRM.Core.Players;

import com.mysql.fabric.xmlrpc.base.Array;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Daniel on 8/8/2016.
 */
public class MageManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<PlayerObject> mages;
    public MageManager(){
        mages =  new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public boolean isMage(String player){
        for(PlayerObject mage : mages){
            if(mage.getName().equalsIgnoreCase(player)){
                return true;
            }
        }
        return false;
    }
    public boolean isMage(UUID uuid){
        for(PlayerObject mage : mages){
            if(mage.getUuid().equals(uuid)){
                return true;
            }
        }
        return false;
    }
    public boolean isMage(Player player){
        for(PlayerObject mage : mages){
            if(mage.getUuid().equals(player.getUniqueId())){
                return true;
            }
        }
        return false;
    }
    public PlayerObject getMage(UUID uuid){
        for(PlayerObject mage : mages){
            if(mage.getUuid().equals(uuid)){
                return mage;
            }
        }
        return null;
    }
    public PlayerObject loadOfflineMage(UUID uuid){
            return mageSpellsManager.main.pluginManager.playerConfig.loadPlayerData(uuid);
    }
    public PlayerObject loadOfflineMage(String name){
        OfflinePlayer offlinePlayer = mageSpellsManager.main.utils.getOfflinePlayerFromName(name);
        if(offlinePlayer != null) {
            return mageSpellsManager.main.pluginManager.playerConfig.loadPlayerData(offlinePlayer.getUniqueId());
        }else{
            return null;
        }

    }
    public void addMage(PlayerObject playerObject){
        mages.add(playerObject);
    }
    public void saveMages(){
        for(PlayerObject mage : (ArrayList<PlayerObject>) mages.clone()){
            mageSpellsManager.main.pluginManager.playerConfig.savePlayerDataNOW(mage);
        }
    }
    public void removeMage(PlayerObject playerObject){
        if(mages.contains(playerObject)){
            mages.remove(playerObject);
        }
    }
}
