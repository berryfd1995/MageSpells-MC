package me.L2_Envy.MSRM.Alchemy.Objects;

import net.minecraft.server.v1_11_R1.Item;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 2/17/2017.
 */
public class CustomRecipe {

    private ItemStack ingredient1;
    private ItemStack ingredient2;
    private int duration;
    public CustomRecipe(ItemStack itemStack, ItemStack itemStack2, int duration){
        this.ingredient1 = itemStack;
        this.ingredient2 = itemStack2;
        this.duration = duration;
    }
    public ItemStack getIngredient1() {
        return ingredient1;
    }

    public ItemStack getIngredient2() {
        return ingredient2;
    }

    public int getDuration() {
        return duration;
    }
    public boolean isRecipe(ItemStack i, ItemStack i2){
        return (i.equals(ingredient1) && i2.equals(ingredient2)) || (i.equals(ingredient2) && i2.equals(ingredient1));
    }
}
