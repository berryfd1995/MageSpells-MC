package me.L2_Envy.MSRM.Core.Objects;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Created by Daniel on 8/9/2016.
 */
public class CustomItemObject {
    private String itemname;
    private ItemStack customitem;
    private int requiredlevel;
    private ShapedRecipe shapedRecipe;
    public CustomItemObject(String itemname, int requiredlevel, ItemStack customitem, ShapedRecipe shapedRecipe){
        this.itemname = itemname;
        this.requiredlevel = requiredlevel;
        this.customitem = customitem;
        this.shapedRecipe = shapedRecipe;
    }

    public int getRequiredlevel() {
        return requiredlevel;
    }

    public ItemStack getCustomitem() {
        return customitem;
    }

    public String getItemname() {
        return itemname;
    }

    public ShapedRecipe getShapedRecipe(){
        return shapedRecipe;
    }

}
