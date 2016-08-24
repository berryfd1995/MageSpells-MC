package me.L2_Envy.MSRM.Core.GUI;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 8/10/2016.
 */
public class WandUI {
    private HashMap<String, Integer> playerinwandui;
    private HashMap<Integer,ArrayList<WandObject>> wandpages;
    private MageSpellsManager mageSpellsManager;
    public WandUI(){
        playerinwandui = new HashMap<>();
        wandpages = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void openWandUI(Player player){
        player.openInventory(loadInventory(player, 1));
        playerinwandui.put(player.getName(), 1);
        //Get Player Data
    }
    public void closeWandUI(Player player){
        if(playerinwandui.containsKey(player.getName())){
            playerinwandui.remove(player.getName());
        }
    }
    public boolean inWandUI(Player player){
        return playerinwandui.containsKey(player.getName());
    }

    private Inventory loadInventory(Player player, int page){
        if(mageSpellsManager.mageManager.isMage(player)) {
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Wand Menu - Page: " + page);
            inventory.setItem(0, mageSpellsManager.main.utils.getItemStack("EMERALD", "&cPrevious Page"));
            inventory.setItem(8, mageSpellsManager.main.utils.getItemStack("EMERALD", "&cNext Page"));
            if(wandpages != null || !wandpages.isEmpty()) {
                for (int i = 0; i < wandpages.get(page).size(); i++) {//slot to item ex 0<9
                    WandObject wandObject = wandpages.get(page).get(i);
                    if (playerObject.getLevel() >= wandObject.getRequiredleveltouse() && playerObject.knowsWand(wandObject)) {
                        String lore = "&6Required Level: " + wandObject.getRequiredleveltocraft() + "/&6Crafting Recipe:/";
                        lore = lore + ("&6[1][2][3]/");
                        lore = lore + ("&6[4][5][6]/");
                        lore = lore + ("&6[7][8][9]/");
                        int count = 1;
                        for (String s : wandObject.getShapedRecipe().getShape()) {
                            for (int j = 0; j < 3; j++) {
                                for (Character c : wandObject.getShapedRecipe().getIngredientMap().keySet()) {
                                    if (c.equals(s.charAt(j))) {
                                        ItemStack is = wandObject.getShapedRecipe().getIngredientMap().get(c);
                                        if (is != null) {
                                            if (is.hasItemMeta()) {
                                                if (is.getItemMeta().hasDisplayName()) {
                                                    lore = lore + ("&6" + count + ": &b" + is.getItemMeta().getDisplayName() + "/");
                                                } else {
                                                    lore = lore + ("&6" + count + ": &b" + ItemNames.lookup(is) + "/");
                                                }
                                            } else {
                                                lore = lore + ("&6" + count + ": &b" + ItemNames.lookup(is) + "/");
                                            }
                                            count++;
                                        } else {
                                            lore = lore + ("&6" + count + ": &bNothing/");
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                        inventory.setItem(i + 1, mageSpellsManager.main.utils.getItemStack(wandObject.getWandItemStack().getType().toString(),
                                wandObject.getDisplayname(), lore));
                    } else {
                        inventory.setItem(i + 1, mageSpellsManager.main.utils.getItemStack(
                                "STAINED_GLASS_PANE-15", wandObject.getDisplayname(),
                                "&6Required Level: " + wandObject.getRequiredleveltouse()
                                        + "/&4You have not discovered this wand yet!"));
                    }
                }
            }
            return inventory;
        }else{
            return null;
        }
    }

    /**
     * Index 0: page 1
     * Index 1 : page 2
     * Index 2 : page 3
     * @param player
     */
    public void nextPage(Player player){
        int page = getPage(player);
        if(page != 0) {
            if (wandpages.containsKey(page)) {
                if (wandpages.containsKey(page+1)) {
                    player.openInventory(loadInventory(player, page+1));
                    playerinwandui.put(player.getName(), page + 1);
                } else {
                    if(page != 1) {
                        player.openInventory(loadInventory(player, 1));
                        playerinwandui.put(player.getName(), 1);
                    }
                }
            }
        }
    }
    public void previousPage(Player player){
        int page = getPage(player);
        if(page != 0) {
            if (wandpages.containsKey(page)) {
                if (wandpages.containsKey(page - 1)) {
                    player.openInventory(loadInventory(player,page-1));
                    playerinwandui.put(player.getName(), page-1);
                } else {
                    if(page != wandpages.size()) {
                        player.openInventory(loadInventory(player, wandpages.size()));
                        playerinwandui.put(player.getName(), wandpages.size());
                    }
                }
            }
        }
    }
    public int getPage(Player player){
        if(inWandUI(player)){
            return playerinwandui.get(player.getName());
        }
        return 0;
    }
    public void resortWandPages(ArrayList<WandObject> wandObjects){
        int maxpages = wandObjects.size() /7;
        int lastpageamount = wandObjects.size() %7;
        if(lastpageamount > 0){
            maxpages +=1;
        }
        //i = page #
        //j = item in page 1-7
        for(int i = 0; i < maxpages; i++){
            ArrayList<WandObject> wandObjectArrayList = new ArrayList<>();
            if(i != maxpages-1){
                for(int j = 0; j <7; j++){
                    int s = j + j*i;
                    wandObjectArrayList.add(wandObjects.get(s));
                }
                wandpages.put(i+1, wandObjectArrayList);
            }else{
                for(int j = 0; j <lastpageamount; j++){
                    int s = j + 7*i;
                    wandObjectArrayList.add(wandObjects.get(s));
                }
                wandpages.put(i+1, wandObjectArrayList);
            }
        }
    }
}
