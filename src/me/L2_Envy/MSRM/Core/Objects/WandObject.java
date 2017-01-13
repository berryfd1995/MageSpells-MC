package me.L2_Envy.MSRM.Core.Objects;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 7/23/2016.
 */
public class WandObject{
    private String wandname;
    private String displayname;
    private String wandnode;
    private ArrayList<String> compatiblespellnodes;
    private int requiredleveltocraft;
    private int requiredleveltouse;
    private int requiredleveltobind;
    private ItemStack wandItemStack;
    private ShapedRecipe shapedRecipe;
    private boolean mobdropsenabled;
    private HashMap<EntityType, Double> mobDrops;
    public WandObject(String wandname, String displayname, int requiredleveltocraft, int requiredleveltouse, int requiredleveltobind, ItemStack wandItemStack, ShapedRecipe shapedRecipe, boolean mobdropsenabled, HashMap<EntityType, Double> mobDrops, String wandnode, ArrayList<String> compatiblespells){
        this.wandname = wandname;
        this.displayname = displayname;
        this.requiredleveltocraft = requiredleveltocraft;
        this.requiredleveltouse = requiredleveltouse;
        this.requiredleveltobind = requiredleveltobind;
        this.wandItemStack = wandItemStack;
        this.shapedRecipe = shapedRecipe;
        this.mobdropsenabled = mobdropsenabled;
        this.mobDrops = mobDrops;
        this.wandnode = wandnode;
        this.compatiblespellnodes = compatiblespells;
    }
    public boolean isMobdropsenabled(){
        return mobdropsenabled;
    }
    public HashMap<EntityType, Double> getMobDrops(){
        return mobDrops;
    }
    public String getWandName() {
        return wandname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public int getRequiredleveltocraft() {
        return requiredleveltocraft;
    }

    public int getRequiredleveltouse() {
        return requiredleveltouse;
    }

    public int getRequiredleveltobind() {
        return requiredleveltobind;
    }

    public ItemStack getWandItemStack() {
        return wandItemStack;
    }
    public ShapedRecipe getShapedRecipe(){
        return shapedRecipe;
    }
    public String getWandnode(){
        return wandnode.toLowerCase();
    }
    public boolean isSpellCompatible(String node){
        for(String compatiblespell : compatiblespellnodes){
            if(node.toLowerCase().equalsIgnoreCase(compatiblespell.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    public static class CompId implements Comparator<WandObject> {
        @Override
        public int compare(WandObject arg0, WandObject arg1) {
            return arg0.getWandName().compareTo(arg1.getWandName());
        }
    }
}
