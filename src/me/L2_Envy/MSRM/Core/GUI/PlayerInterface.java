package me.L2_Envy.MSRM.Core.GUI;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/24/2016.
 */
public class PlayerInterface {
    private MageSpellsManager mageSpellsManager;
    private ArrayList<String> playersininterface;
    public PlayerInterface(){
        playersininterface = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void openPlayerInterface(Player player){
        player.openInventory(loadInventory());
        playersininterface.add(player.getName());
    }
    public void closePlayerInterface(Player player){
        if(playersininterface.contains(player.getName())){
            playersininterface.remove(player.getName());
        }
    }
    public boolean inPlayerInterface(Player player){
        return playersininterface.contains(player.getName());
    }
    public Inventory loadInventory(){
        Inventory inv = Bukkit.createInventory(null,9,ChatColor.DARK_PURPLE +"Menu");
        inv.setItem(2, mageSpellsManager.main.utils.getItemStack("STICK", ChatColor.RED +"Wand Menu"));
        inv.setItem(3, mageSpellsManager.main.utils.getItemStack("BLAZE_POWDER", ChatColor.RED +"Spell Menu"));
        if(mageSpellsManager.wandBag.getEnabled()) {
            inv.setItem(4, mageSpellsManager.main.utils.getItemStack("CHEST", ChatColor.GOLD + "Wand Bag"));
        }
        inv.setItem(5, mageSpellsManager.main.utils.getItemStack("PAPER", ChatColor.BLUE +"Mage Stats"));
        inv.setItem(6, mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", ChatColor.BLUE +"Binding Menu"));
        inv.setItem(7, mageSpellsManager.main.utils.getItemStack("BARRIER"/*"BREWING_STAND_ITEM"*/, ChatColor.GREEN + "COMING SOON"));
        return inv;
    }
}

