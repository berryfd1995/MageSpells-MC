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
                    {1,0,4,0,3,0,4,0,1},
                    {1,1,2,1,1,1,2,1,1},
                    {1,1,2,1,1,1,2,1,1},
                    {1,1,2,2,4,2,2,1,1},
                    {1,1,1,1,1,1,1,1,1}};
    public BrewingMenu(){
        playerinbrewingmenu = new ArrayList<>();
    }
    public void link(AlchemyManager alchemyManager){
        this.alchemyManager = alchemyManager;
        this.mageSpellsManager = alchemyManager.main.mageSpellsManager;
    }
    public void openBrewingMenu(Player player){
        if(!playerinbrewingmenu.contains(player.getName())) {
            player.openInventory(loadInventory());
            playerinbrewingmenu.add(player.getName());
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
        ItemStack[] contents = new ItemStack[54];
        for(int i = 0; i <6; i++){
            for(int j = 0; j < 9; j++){
                int s = ((i*8)+i) + j;
                switch (layout[i][j]){
                    case 0:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-2", "&cIngrediant");
                        break;
                    case 1:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-15");
                        break;
                    case 2:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-14");
                        break;
                    case 3:
                        contents[s] = alchemyManager.main.utils.getItemStack("STAINED_GLASS_PANE-5", "&aBrew");
                        break;
                }
            }
        }
        Inventory inv = Bukkit.createInventory(null,54, ChatColor.DARK_PURPLE +"Brewing Menu");
        inv.setContents(contents);
        return inv;
    }
    public void addItem(){

    }
    public void removeItem(){

    }
}
