package me.L2_Envy.MSRM.Core.Wands;

import com.mysql.fabric.xmlrpc.base.Array;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Daniel on 7/23/2016.
 */
public class WandManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<WandObject> wandObjects;
    private boolean wandlearningenabled;
    public WandManager(){
        wandObjects = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public boolean hasWandObject(WandObject wandObject){
        return wandObjects.contains(wandObject);
    }
    public void addWandObject(WandObject wandObject){
        if(!wandObjects.contains(wandObject)){
            wandObjects.add(wandObject);
            sortWands();
        }
    }
    public void setEnableWandLearning(boolean enable){
        this.wandlearningenabled = enable;
    }
    public boolean isWandLearningEnabled(){
        return wandlearningenabled;
    }
    public void removeWandObject(WandObject wandObject){
        if(!wandObjects.contains(wandObject)){
            wandObjects.remove(wandObject);
        }
    }
    public void sortWands() {
        Collections.sort(wandObjects, new WandObject.CompId());
        mageSpellsManager.wandUI.resortWandPages(wandObjects);
    }
    public ArrayList<WandObject> getWandObjects(){
        return wandObjects;
    }
    public boolean isWand(ItemStack itemStack){
        if(itemStack.hasItemMeta()) {
            for (WandObject wandObject : wandObjects) {
                if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(wandObject.getDisplayname())) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isSameWandIgnoreAmount(ItemStack itemStack1, ItemStack itemStack2){
        if(itemStack1.getItemMeta().getDisplayName().equals(itemStack2.getItemMeta().getDisplayName()) &&
                itemStack1.getType().equals(itemStack2.getType())){
            if(itemStack1.getItemMeta().hasLore() && itemStack2.getItemMeta().hasLore()){
                if(itemStack1.getItemMeta().getLore().equals(itemStack2.getItemMeta().getLore())){
                    return true;
                }
            }else if(!itemStack1.getItemMeta().hasLore() && !itemStack2.getItemMeta().hasLore()){
                return true;
            }
        }
        return false;
    }
    public boolean isSameWandIgnoreAmountAndLore(ItemStack itemStack1, ItemStack itemStack2){
        if(itemStack1.getItemMeta().getDisplayName().equals(itemStack2.getItemMeta().getDisplayName()) &&
                itemStack1.getType().equals(itemStack2.getType())){
            return true;
        }
        return false;
    }
    public WandObject getWandFromItem(ItemStack itemStack){
        for (WandObject wandObject : wandObjects) {
            if (wandObject.getDisplayname().equalsIgnoreCase(itemStack.getItemMeta().getDisplayName())){
                return wandObject;
            }
        }
        return null;
    }
    public ItemStack getWandItemStack(String name){
        for (WandObject wandObject : wandObjects) {
            if (wandObject.getWandName().equalsIgnoreCase(name.toLowerCase())){
                return wandObject.getWandItemStack();
            }
        }
        return null;
    }
    public WandObject getWandFromName(String name){
        for (WandObject wandObject : wandObjects) {
            if (wandObject.getWandName().equalsIgnoreCase(name.toLowerCase())){
                return wandObject;
            }
        }
        return null;
    }

    public SpellObject getSpellFromPrimary(ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.hasItemMeta()){
                if(itemStack.getItemMeta().hasLore()){
                    String lore = itemStack.getItemMeta().getLore().get(0);
                    if(lore.contains(": ")) {
                        String primary = lore.split(": ")[1];
                        return mageSpellsManager.spellManager.getSpellFromDisplayName(primary);
                    }
                }
            }
        }
        return null;
    }
    public SpellObject getSpellFromSecondary(ItemStack itemStack){
        if (itemStack != null) {
            if (itemStack.hasItemMeta()){
                if(itemStack.getItemMeta().hasLore()){
                    if(itemStack.getItemMeta().getLore().size() ==2){
                        String lore = itemStack.getItemMeta().getLore().get(1);
                        if(lore.contains(": ")) {
                            String secondary = lore.split(": ")[1];
                            return mageSpellsManager.spellManager.getSpellFromDisplayName(secondary);
                        }
                    }
                }
            }
        }
        return null;
    }
}
