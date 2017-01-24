package me.L2_Envy.MSRM.Core.GUI;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by Daniel on 1/23/2017.
 */
public class SpellInfoUI {
    private MageSpellsManager mageSpellsManager;
    private ArrayList<String> playersinmenu;
    public SpellInfoUI(){
        playersinmenu = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void closeSpellInfoUI(Player player){
        if(playersinmenu.contains(player.getName())){
            playersinmenu.remove(player.getName());
        }
    }
    public boolean inSpellInfoUI(Player player){
        return playersinmenu.contains(player.getName());
    }

    public Inventory loadInventory(Player player, SpellObject spellObject){
        Inventory inventory = Bukkit.createInventory(null,27, ChatColor.BLUE + "Spell: " + spellObject.getDisplayname());
        if(mageSpellsManager.mageManager.isMage(player)){
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            //Setup inventory
            /*
                -compatible wands
                -spell info like damage, armour piercing
                -effects
                -
             */
            if(playerObject.knowsSpell(spellObject)){

            }else{

            }
            //if admin
            if(player.hasPermission("")){

            }
        }
        return inventory;
    }
    public Inventory loadInventory(Player player, WandObject wandObject){
        Inventory inventory = Bukkit.createInventory(null,27, ChatColor.BLUE + "Wand: " + wandObject.getDisplayname());
        if(mageSpellsManager.mageManager.isMage(player)){
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            //Setup inventory
            /*
                -compatible wands
                -spell info like damage, armour piercing
                -effects
                -
             */
            if(playerObject.knowsWand(wandObject)){

            }else{

            }
            //if admin
            if(player.hasPermission("")){

            }
        }
        return inventory;
    }
}
