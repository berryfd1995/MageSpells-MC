package me.L2_Envy.MSRM.Core.GUI;

import it.unimi.dsi.fastutil.Hash;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 8/10/2016.
 */
public class SpellUI {
    private HashMap<String, Integer> playerinspellui;
    private HashMap<Integer,ArrayList<SpellObject>> spellpages;
    private MageSpellsManager mageSpellsManager;
    public SpellUI(){
        playerinspellui = new HashMap<>();
        spellpages = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void openSpellUI(Player player){
        player.openInventory(loadInventory(player, 1));
        playerinspellui.put(player.getName(), 1);
        //Get Player Data
    }
    public void closeSpellUI(Player player){
        if(playerinspellui.containsKey(player.getName())){
            playerinspellui.remove(player.getName());
        }
    }
    public boolean inSpellUI(Player player){
        return playerinspellui.containsKey(player.getName());
    }

    private Inventory loadInventory(Player player, int page){
        if(mageSpellsManager.mageManager.isMage(player)) {
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Spell Menu - Page: " + page);
            inventory.setItem(0, mageSpellsManager.main.utils.getItemStack("EMERALD", "&cPrevious Page"));
            inventory.setItem(8, mageSpellsManager.main.utils.getItemStack("EMERALD", "&cNext Page"));
            if(spellpages != null) {
                if (!spellpages.isEmpty()) {
                    for (int i = 0; i < spellpages.get(page).size(); i++) {//slot to item ex 0<9
                        SpellObject spellObject = spellpages.get(page).get(i);
                        if ((playerObject.getLevel() >= spellObject.getRequiredLevelToCast() || !mageSpellsManager.levelingManager.isLevelingEnabled())
                                && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))
                                && (!mageSpellsManager.isNodeSystemEnabled() || player.hasPermission("magespells.spell." + spellObject.getSpellNode()))) {
                            String lore = spellObject.getLore() + "/&6BoltDamage: " + spellObject.getBoltdamage()
                                    + "/&6AuraDamage: " + spellObject.getAuradamage()
                                    + "/&6SprayDamage: " + spellObject.getSpraydamage()
                                    + "/&6Armor Piercing: " + spellObject.getArmorpiercing()
                                    + "/&6Mana Cost: " + spellObject.getManacost()
                                    + "/&6Money Cost: " + spellObject.getMoneycost()
                                    + "/&6Charge Time: " + spellObject.getChargetime()
                                    + "/&6Cooldown Time: " + spellObject.getCooldown()
                                    + "/&6Special Effect: " + spellObject.getSpellEffect()
                                    + "/&6Required Level: " + spellObject.getRequiredLevelToCast();
                            if (spellObject.isItemcostenabled()) {
                                lore = lore + "/&6Item Cost: ";
                                for (ItemStack itemStack : spellObject.getItemcost().keySet()) {
                                    if (mageSpellsManager.main.utils.itemStackIsCustomItem(itemStack)) {
                                        lore = lore + "/&6" + itemStack.getItemMeta().getDisplayName() + " x" + spellObject.getItemcost().get(itemStack);
                                    } else {
                                        lore = lore + "/&6" + ItemNames.lookup(itemStack) + " x" + spellObject.getItemcost().get(itemStack);
                                    }
                                }
                            }
                            inventory.setItem(i + 1, mageSpellsManager.main.utils.getItemStack("NETHER_STAR", spellObject.getDisplayname(), lore));

                            //Add Item Cost
                        } else {
                            //Add lore here
                            String lore = "";
                            if(!(playerObject.getLevel() >= spellObject.getRequiredLevelToCast()) && mageSpellsManager.levelingManager.isLevelingEnabled()){
                                lore ="/&6Required Level: " + spellObject.getRequiredLevelToCast();
                            }
                            if(mageSpellsManager.spellLearningManager.isLearningEnabled() && !playerObject.knowsSpell(spellObject)){
                                lore = "/&6You have not learned this spell yet!";
                            }
                            if(mageSpellsManager.isNodeSystemEnabled() && !player.hasPermission("magespells.spell." + spellObject.getSpellNode())){
                                lore = "/&6You do not have permission to see this spell!";
                            }
                            inventory.setItem(i + 1, mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-15", spellObject.getDisplayname(),
                                    spellObject.getLore() + lore
                                            + "/&4This spell is currently unavaliable!"));
                        }
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
            if (spellpages.containsKey(page)) {
                if (spellpages.containsKey(page+1)) {
                    player.openInventory(loadInventory(player, page+1));
                    playerinspellui.put(player.getName(), page + 1);
                } else {
                    if(page != 1) {
                        player.openInventory(loadInventory(player, 1));
                        playerinspellui.put(player.getName(), 1);
                    }
                }
            }
        }
    }
    public void previousPage(Player player){
        int page = getPage(player);
        if(page != 0) {
            if (spellpages.containsKey(page)) {
                if (spellpages.containsKey(page - 1)) {
                    player.openInventory(loadInventory(player,page-1));
                    playerinspellui.put(player.getName(), page-1);
                } else {
                    if(page != spellpages.size()) {
                        player.openInventory(loadInventory(player, spellpages.size()));
                        playerinspellui.put(player.getName(), spellpages.size());
                    }
                }
            }
        }else{
        }
    }
    public int getPage(Player player){
        if(inSpellUI(player)){
            return playerinspellui.get(player.getName());
        }
        return 0;
    }

    public SpellObject getSpellSelected(Player player, int slot){
        return spellpages.get(getPage(player)).get(slot-1);
    }

    //Fix
    public void resortSpellPages(ArrayList<SpellObject> spellObjects){
        spellpages = new HashMap<>();
        int maxpages = spellObjects.size() /7; //Max pages is how many times 7 can go into the size.
        int lastpageamount = spellObjects.size() %7; //grabs the remainder
        if(lastpageamount > 0){//if there are more. then increase the last page.
            maxpages +=1;
        }else if(lastpageamount == 0){ //if it equals 0 then set the last page to 7
            lastpageamount = 7;
        }
        //i = page #
        //j = item in page 1-7
        for(int i = 0; i < maxpages; i++){ // do the amount of pages. 0 to maxpages -1
            ArrayList<SpellObject> spellObjectArrayList = new ArrayList<>();//new array list for the page... should change to array.
            if(i != maxpages-1){//pages leading up to last page.
                for(int j = 0; j <7; j++){
                    int s = j + 7*i;
                    spellObjectArrayList.add(spellObjects.get(s));
                }
                spellpages.put(i+1, spellObjectArrayList);
            }else{
                for(int j = 0; j <lastpageamount; j++){
                    int s = j + 7*i;
                    spellObjectArrayList.add(spellObjects.get(s));
                }
                spellpages.put(i+1, spellObjectArrayList);
            }
        }

    }
    //LEVEL AND ALPHABETICAL
}
