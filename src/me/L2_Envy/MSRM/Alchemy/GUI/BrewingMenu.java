package me.L2_Envy.MSRM.Alchemy.GUI;

import me.L2_Envy.MSRM.Alchemy.AlchemyManager;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by berry on 9/20/2016.
 */
public class BrewingMenu {
    private AlchemyManager alchemyManager;
    private MageSpellsManager mageSpellsManager;
    private ArrayList<String> playerinbrewingmenu;
    public BrewingMenu(){
        playerinbrewingmenu = new ArrayList<>();
    }
    public void link(AlchemyManager alchemyManager){
        this.alchemyManager = alchemyManager;
        this.mageSpellsManager = alchemyManager.main.mageSpellsManager;
    }
    public void openBrewingMenu(Player player){
        if(!playerinbrewingmenu.contains(player.getName())) {
            playerinbrewingmenu.add(player.getName());
            player.openInventory(loadInventory());
        }
    }
    public void closeBrewingMenu(Player player){
        if(playerinbrewingmenu.contains(player.getName())) {
            playerinbrewingmenu.remove(player.getName());
        }
    }
    public boolean inBrewingMenu(Player player){
        return playerinbrewingmenu.contains(player.getName());
    }
    private Inventory loadInventory(){
        /**
         * [00][01][02][03][04][05][06][07][08]
         * [09][10][11][12][13][14][15[16][17]
         * [18][19][20][21][22][23][24][25][26]
         * [27][28][29][30][31][32][33][34][35]
         * [36][37][38][39][40][41][42][43][44]
         * [45][46][47][48][49][50][51][52][53]
         **/
        /**
         * [*][*][*][*][*][*][*][*][*]
         * [*][*][ ][ ][ ][ ][ ][*][*]
         * [*][ ][ ][ ][ ][ ][ ][ ][*]
         * [*][ ][ ][ ][ ][ ][ ][ ][*]
         * [*][*][ ][ ][ ][ ][ ][*][*]
         * [*][*][*][*][*][*][*][*][*]
         **/
        Inventory inv = Bukkit.createInventory(null,54, ChatColor.DARK_PURPLE +"Brewing Menu");
        inv.setItem(53, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-13", ChatColor.GREEN +"Create Potion"));
        return inv;
    }
}
