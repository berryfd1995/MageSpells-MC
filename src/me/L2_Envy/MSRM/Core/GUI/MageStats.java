package me.L2_Envy.MSRM.Core.GUI;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Daniel on 8/10/2016.
 */
public class MageStats {
    private ArrayList<String> playerinmagestats;
    private MageSpellsManager mageSpellsManager;
    public MageStats(){
        playerinmagestats = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void openMageStats(Player player){
        player.openInventory(loadInventory(player));
        playerinmagestats.add(player.getName());
        //Get Player Data
    }
    public void closeMageStats(Player player){
        if(playerinmagestats.contains(player.getName())){
            playerinmagestats.remove(player.getName());
        }
    }
    public boolean inMageStats(Player player){
        return playerinmagestats.contains(player.getName());
    }

    private Inventory loadInventory(Player player){
        Inventory inventory = Bukkit.createInventory(null,9, ChatColor.BLUE + "Mage Stats");
        if(mageSpellsManager.mageManager.isMage(player)){
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            TeamObject teamObject = mageSpellsManager.teamManager.getTeam(playerObject);
            String teamname;
            if(teamObject == null){
                teamname = "None";
            }else{
                teamname = teamObject.getTeamname();
            }
            inventory.setItem(0,mageSpellsManager.main.utils.getItemStack("BOOK","&bStats",
                    "&6Name: " + player.getName()
                            + "/&6Team Name: " + teamname
                            + "/&6Level: " + playerObject.getLevel()
                            + "/&6Current xp: " + playerObject.getExperience()
                            + "/&6Spells Learned: " + (playerObject.getSpellObjects().size())
                            + " out of " + (mageSpellsManager.spellManager.getSpellObjects().size())
                            + "/&6Wands Learned: " + playerObject.getWandObjects().size()
                            + " out of " + mageSpellsManager.wandManager.getWandObjects().size()));
        }
        return inventory;
    }
}
