package me.L2_Envy.MSRM.Alchemy.GUI;

import me.L2_Envy.MSRM.Alchemy.AlchemyManager;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by berry on 9/20/2016.
 */
public class BrewingMenu {
    private AlchemyManager alchemyManager;
    private MageSpellsManager mageSpellsManager;
    private ArrayList<String> playerinbrewingmenu;
    /*
      0 = AIR
      1 = Black Glass
      2 = Item
      3 = Result
     */
    private static final int[][] layout =
                   {{1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1}};
                   /*{{0,0,0,0,2,0,0,0,0},
                    {0,0,0,2,2,2,0,0,0},
                    {0,0,0,0,2,0,0,0,0},
                    {0,0,0,2,0,2,0,0,0},
                    {0,0,2,0,0,0,2,0,0},
                    {0,2,2,2,2,2,2,2,0}};*/
                   /*{{1,2,2,2,2,2,2,2,1},
                    {2,2,0,0,0,0,0,2,2},
                    {2,0,2,0,0,0,2,0,2},
                    {2,0,0,2,0,2,0,0,2},
                    {2,0,0,0,3,0,0,0,2},
                    {1,2,0,0,0,0,0,2,1}};*/
                   /*{{2,2,2,2,2,2,2,2,2},
                    {0,2,0,0,1,0,0,2,0},
                    {0,0,2,1,0,1,2,0,0},
                    {0,0,1,2,0,2,1,0,0},
                    {0,1,0,0,2,0,0,1,0},
                    {1,1,1,1,1,1,1,1,1}};*/
                   /*{{0,0,0,0,2,0,0,0,0},
                    {1,1,1,2,1,2,1,1,1},
                    {0,1,2,0,0,0,2,1,0},
                    {0,2,1,0,0,0,1,2,0},
                    {2,2,2,2,2,2,2,2,2},
                    {0,0,0,0,1,0,0,0,0}};*/
                   /*{{0,0,0,0,2,0,0,0,0},
                    {0,0,0,1,1,1,0,0,0},
                    {0,2,1,1,3,1,1,2,0},
                    {0,0,1,1,1,1,1,0,0},
                    {0,0,1,1,0,1,1,0,0},
                    {0,2,0,0,0,0,0,2,0}};*/
    /**
     * [00][01][02][03][04][05][06][07][08]
     * [09][10][11][12][13][14][15[16][17]
     * [18][19][20][21][22][23][24][25][26]
     * [27][28][29][30][31][32][33][34][35]
     * [36][37][38][39][40][41][42][43][44]
     * [45][46][47][48][49][50][51][52][53]
     **/
    /**
     * [*][*][*][*][I][*][*][*][*]
     * [*][*][*][I][I][I][*][*][*]
     * [I][I][I][I][I][I][I][I][I]
     * [*][I][I][I][I][I][I][I][*]
     * [*][*][I][I][I][I][I][*][*]
     * [*][I][I][*][*][*][I][I][*]
     */
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
        Inventory inv = Bukkit.createInventory(null,9, ChatColor.DARK_PURPLE +"Brewing Menu");
        inv.setItem(1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-5", ChatColor.GREEN +"Mix Ingredients"));
        inv.setItem(1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-14", ChatColor.GREEN +"Don't Mix"));
        for(int i = 1; i < 9; i++){
            inv.setItem(1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-15"));
        }
        return inv;
    }
    /*private Inventory loadInventory(){
        Inventory inv = Bukkit.createInventory(null,9, ChatColor.DARK_PURPLE +"Brewing Menu");
        inv.setItem(1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-5", ChatColor.GREEN +"Mix Ingredients"));
        inv.setItem(1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-14", ChatColor.GREEN +"Don't Mix"));
        for(int i = 1; i < 9; i++){
            inv.setItem(1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-15"));
        }
        return inv;
    }*/
    /*private Inventory loadInventory(){
        ItemStack[] contents = new ItemStack[54];
        for(int i = 0; i <6; i++){
            for(int j = 0; j < 9; j++){
                int s = ((i*8)+i) + j;
                switch (layout[i][j]){
                    case 0:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-2");
                        break;
                    case 1:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-15");
                        break;
                    case 2:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-15", "&cIngrediant");
                        break;
                    case 3:
                        contents[s] = alchemyManager.main.utils.getItemStack("GLASS_BOTTLE", "&cResult");
                        break;
                }
            }
        }
        Inventory inv = Bukkit.createInventory(null,54, ChatColor.DARK_PURPLE +"Brewing Menu");
        inv.setContents(contents);
        //inv.setItem(53, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-13", ChatColor.GREEN +"Create Potion"));
        return inv;
    }
    public void addItem(){

    }
    public void removeItem(){

    }*/
}
