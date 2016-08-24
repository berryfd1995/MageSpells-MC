package me.L2_Envy.MSRM.Core.SpellBook;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Daniel on 7/23/2016.
 */
public class SpellBookManager {
    public MageSpellsManager mageSpellsManager;

    public SpellBookManager() {

    }

    public void link(MageSpellsManager mageSpellsManager) {
        this.mageSpellsManager = mageSpellsManager;
    }

    public boolean isSpellBook(ItemStack itemStack){
        for(SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()){
            if(itemStack.equals(spellObject.getSpellbook())){
                return true;
            }
        }
        return false;
    }
    public boolean isSpellBookIgnoreAmount(ItemStack itemStack){
        for(SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()){
            ItemStack book = spellObject.getSpellbook();
            if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(book.getItemMeta().getDisplayName()) &&
                    book.getType().equals(itemStack.getType()) && book.getItemMeta().getLore().equals(itemStack.getItemMeta().getLore())){
                return true;
            }
        }
        return false;
    }
    public boolean isSameSpellBookIgnoreAmount(ItemStack book, ItemStack itemStack){
        if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(book.getItemMeta().getDisplayName()) &&
                book.getType().equals(itemStack.getType()) && book.getItemMeta().getLore().equals(itemStack.getItemMeta().getLore())){
            return true;
        }else{
            return false;
        }
    }
    public SpellObject getSpellFromBook(ItemStack itemStack){
        for(SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()){
            if(itemStack.equals(spellObject.getSpellbook())){
                return spellObject;
            }
        }
        return null;
    }
    public boolean shouldDrop(double chance) {
        Random r = new Random();
        double num = r.nextDouble();
        chance = chance * .01;
        return num <= chance;
    }
}
