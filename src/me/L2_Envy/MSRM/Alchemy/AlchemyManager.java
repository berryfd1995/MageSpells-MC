package me.L2_Envy.MSRM.Alchemy;

import me.L2_Envy.MSRM.Alchemy.Effects.AlchemyEffectManager;
import me.L2_Envy.MSRM.Alchemy.GUI.BrewingMenu;
import me.L2_Envy.MSRM.Alchemy.Objects.MagePotion;
import me.L2_Envy.MSRM.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by berry on 9/19/2016.
 */
public class AlchemyManager {
    public Main main;
    public AlchemyEffectManager alchemyEffectManager;
    public BrewingMenu brewingMenu;
    private ArrayList<MagePotion> customPotions;
    public AlchemyManager(){
        alchemyEffectManager = new AlchemyEffectManager();
        brewingMenu = new BrewingMenu();
        customPotions = new ArrayList<>();
    }
    public void linkAll(Main main){
        this.main = main;
        alchemyEffectManager.link(this);
        brewingMenu.link(this);
    }
    public void addCustomPotion(MagePotion magePotion){
        if(!customPotions.contains(magePotion)){
            customPotions.add(magePotion);
        }
    }
    private void setupRecipies(){

    }
    public boolean canCombine(ItemStack ingredient1, ItemStack ingredient2, ItemStack result){
        return combineItems(ingredient1,ingredient2,result) != null;
    }
    public ItemStack combineItems(ItemStack ingredient1, ItemStack ingredient2, ItemStack result){
        /*
        -check if custom item (You may not need to)
        -check result for potion if no potion in ingredient1 or 2
        -check for recipies
        -MAYBE CHANGE RESULT TO WHERE YOU CAN"T CLICK ON IT, SO IT DISPLAYS ITEM
        -does there really need to be a bottle?
         */
        ItemStack newresult= null;
        if(result != null){
            if(isTypeBottle(result)){
                for(MagePotion magePotion : customPotions){
                    if(magePotion.getCustomRecipe().isRecipe(ingredient1,ingredient2)){
                        newresult = magePotion.getPotion();
                    }
                }
            }
        }else if(isTypeBottle(ingredient1) || isTypeBottle(ingredient2)){
            for(MagePotion magePotion : customPotions){
                if(magePotion.getCustomRecipe().isRecipe(ingredient1,ingredient2)){
                    newresult = magePotion.getPotion();
                }
            }
        }
        return newresult;
    }
    private boolean isTypeBottle(ItemStack itemStack){
        return  itemStack.getType() == Material.GLASS_BOTTLE ||
                itemStack.getType() == Material.POTION || itemStack.getType() == Material.SPLASH_POTION
                || itemStack.getType() == Material.LINGERING_POTION;
    }
}
