package me.L2_Envy.MSRM.Core.GUI;

import com.mysql.fabric.xmlrpc.base.Array;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by Daniel on 8/10/2016.
 */
public class WandBag {
    private ArrayList<String> playersinwandbag;
    private MageSpellsManager mageSpellsManager;
    private boolean enabled;
    public WandBag(){
        playersinwandbag = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void openWandBag(Player player){
        if(mageSpellsManager.mageManager.isMage(player)) {
            player.openInventory(loadInventory(player));
            playersinwandbag.add(player.getName());
        }else{
            player.sendMessage("You are not recoreded as a mage!");
        }
        //Get Player Data
    }
    public boolean getEnabled(){
        return  enabled;
    }
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
    public void closeWandBag(Player player){
        if(playersinwandbag.contains(player.getName())){
            playersinwandbag.remove(player.getName());
        }
    }
    public boolean inWandBag(Player player){
        return playersinwandbag.contains(player.getName());
    }

    private Inventory loadInventory(Player player){
       return mageSpellsManager.mageManager.getMage(player.getUniqueId()).getInventory();
    }
}
