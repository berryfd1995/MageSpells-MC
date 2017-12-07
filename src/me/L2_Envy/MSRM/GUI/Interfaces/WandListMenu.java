package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import me.L2_Envy.MSRM.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class WandListMenu extends UserInterface {
    private static final int[][] FORMAT = {{1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,2}};
    private WandObject[] wandObjects;
    private int page;
    public WandListMenu(Player player, int page){
        super(player);
        //check page
        int maxpages = getMaxPages();
        if(page < 0){
            this.page = maxpages;
        }else if(page > maxpages){
            this.page = 0;
        }
        getWandsOnPage();
    }
    @Override
    public Inventory loadInventory() {
        PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.RED + "Wand Menu - Page: " + (page+1));
        ItemStack[] contents = new ItemStack[27];
        for(int i = 0; i < FORMAT.length; i++) {
            for (int j = 0; j < FORMAT[i].length; j++) {
                int k = ((i * 8) + i) + j;
                switch (FORMAT[i][j]) {
                    case 0:
                        if(k-1 < wandObjects.length) {
                            WandObject wandObject = wandObjects[k - 1];
                            if ((playerObject.getLevel() >= wandObject.getRequiredleveltouse() || !Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                                    && (!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))
                                    && (!Main.getMageSpellsManager().isNodeSystemEnabled() || (getPlayer().hasPermission("magespells.wand." + wandObject.getWandnode())))) {
                                String lore = "&6Required Level: " + wandObject.getRequiredleveltocraft() + "/&6Crafting Recipe:/";
                                lore = lore + ("&6[1][2][3]/");
                                lore = lore + ("&6[4][5][6]/");
                                lore = lore + ("&6[7][8][9]/");
                                int count = 1;
                                for (String s : wandObject.getShapedRecipe().getShape()) {
                                    for (int l = 0; l < 3; l++) {
                                        for (Character c : wandObject.getShapedRecipe().getIngredientMap().keySet()) {
                                            if (c.equals(s.charAt(l))) {
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
                                contents[k] = Utils.getItemStack(wandObject.getWandItemStack().getType().toString(),
                                        wandObject.getDisplayname(), lore);
                            } else {
                                String lore = "";
                                if(!(playerObject.getLevel() >= wandObject.getRequiredleveltouse()) && Main.getMageSpellsManager().levelingManager.isLevelingEnabled()){
                                    lore = "&6Required Level: " + wandObject.getRequiredleveltouse();
                                }
                                if(Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() && !playerObject.knowsWand(wandObject)){
                                    lore = "/&6You have not learned this wand yet!";
                                }
                                if(Main.getMageSpellsManager().isNodeSystemEnabled() && !(getPlayer().hasPermission("magespells.wand." + wandObject.getWandnode()))){
                                    lore = "/&6You do not have permission to see this wand!";
                                }
                                contents[k] = Utils.getItemStack(
                                        "STAINED_GLASS_PANE-15", wandObject.getDisplayname(), lore
                                                + "/&4This wand is currently unavaliable!");
                            }
                        }
                        break;
                    case 1:
                        contents[k] = Utils.getItemStack("EMERALD", "&cPrevious Page");
                        break;
                    case 2:
                        contents[k] = Utils.getItemStack("EMERALD", "&cNext Page");
                        break;
                }
            }
        }
        inventory.setContents(contents);
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {
        //check if allowed to use wand
        switch(slot){
            case 0:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new WandListMenu(getPlayer(),page--));
                break;
            case 26:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new WandListMenu(getPlayer(),page++));
                break;
            default:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new WandInfoMenu(getPlayer(),wandObjects[slot -1]));
                break;
        }
    }
    private void getWandsOnPage(){
        int startIndex = page * 25;
        int endIndex = startIndex + 24;
        if(endIndex >= Main.getMageSpellsManager().wandManager.getWandObjects().size()){
            endIndex = Main.getMageSpellsManager().wandManager.getWandObjects().size()-1;
        }

        ArrayList<WandObject> wandCollection = new ArrayList<>(Main.getMageSpellsManager().wandManager.getWandObjects().subList(startIndex,endIndex));
        wandObjects = wandCollection.toArray(new WandObject[wandCollection.size()]);
    }
    private int getMaxPages(){
        ArrayList<WandObject> wandCollection = Main.getMageSpellsManager().wandManager.getWandObjects();
        if(wandCollection.size() > 0){
            return (wandCollection.size() / 25) + (wandCollection.size() % 25 > 0 ? 1 : 0);
        }else{
            return 0;
        }
    }
}
