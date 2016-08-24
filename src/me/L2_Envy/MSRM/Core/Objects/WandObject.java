package me.L2_Envy.MSRM.Core.Objects;

import net.minecraft.server.v1_10_R1.BlockPortal;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Comparator;

/**
 * Created by Daniel on 7/23/2016.
 */
public class WandObject{
    private String wandname;
    private String displayname;
    private int requiredleveltocraft;
    private int requiredleveltouse;
    private int requiredleveltobind;
    private ItemStack wandItemStack;
    private ShapedRecipe shapedRecipe;
    public WandObject(String wandname, String displayname, int requiredleveltocraft, int requiredleveltouse,int requiredleveltobind, ItemStack wandItemStack, ShapedRecipe shapedRecipe){
        this.wandname = wandname;
        this.displayname = displayname;
        this.requiredleveltocraft = requiredleveltocraft;
        this.requiredleveltouse = requiredleveltouse;
        this.requiredleveltobind = requiredleveltobind;
        this.wandItemStack = wandItemStack;
        this.shapedRecipe = shapedRecipe;
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
    public static class CompId implements Comparator<WandObject> {
        @Override
        public int compare(WandObject arg0, WandObject arg1) {
            return arg0.getWandName().compareTo(arg1.getWandName());
        }
    }
}
