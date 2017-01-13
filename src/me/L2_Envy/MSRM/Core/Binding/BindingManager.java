package me.L2_Envy.MSRM.Core.Binding;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Daniel on 7/23/2016.
 */
public class BindingManager {
    public MageSpellsManager mageSpellsManager;
    public BindingManager(){
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }

    public void bind(Player player, ItemStack spellbook){

    }

    public void bind(Player player, SpellObject spellObject, ItemStack itemStack, boolean primary){
        PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
        if(playerObject != null && itemStack != null && spellObject != null){
            WandObject wandObject = mageSpellsManager.wandManager.getWandFromItem(itemStack);
            if(wandObject != null){
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta != null){
                    if(removeSpellBookAndWand(player, spellObject, itemStack)) {
                        if (primary) {
                            ItemStack finalwand =setPrimarySpell(itemStack, spellObject);
                            player.getInventory().addItem(finalwand);
                        } else {
                            ItemStack finalwand =setSecondarySpell(itemStack, spellObject);
                            player.getInventory().addItem(finalwand);
                        }
                    }
                }
            }
        }
    }
    public ItemStack setPrimarySpell(ItemStack itemStack, SpellObject spell){
        ItemStack itemStack1 = itemStack;
        ItemMeta itemMeta = itemStack1.getItemMeta();
        if(itemMeta != null){
            if(itemMeta.hasLore()){
                itemMeta.setLore(Arrays.asList(mageSpellsManager.main.utils.colorize("&6Primary: " + spell.getDisplayname()),itemMeta.getLore().get(1)));
            }else{
                itemMeta.setLore(Arrays.asList(mageSpellsManager.main.utils.colorize("&6Primary: " + spell.getDisplayname()),""));
            }
            itemStack1.setItemMeta(itemMeta);
            return itemStack1;
        }
        return null;
    }
    public ItemStack setSecondarySpell(ItemStack itemStack, SpellObject spell){
        ItemStack itemStack1 = itemStack;
        ItemMeta itemMeta = itemStack1.getItemMeta();
        if(itemMeta != null){
            if(itemMeta.hasLore()){
                itemMeta.setLore(Arrays.asList(mageSpellsManager.main.utils.colorize(itemMeta.getLore().get(0)),mageSpellsManager.main.utils.colorize("&6Secondary: " + spell.getDisplayname())));
            }else{
                itemMeta.setLore(Arrays.asList("",mageSpellsManager.main.utils.colorize("&6Secondary: " + spell.getDisplayname())));
            }
            itemStack1.setItemMeta(itemMeta);
            return itemStack1;
        }
        return null;
    }
    @SuppressWarnings( "deprecation" )
    public boolean removeSpellBookAndWand(Player player,SpellObject spellObject,ItemStack wanditem){
        try {
            boolean tookspellbook = false;
            boolean tookwanditem = false;
            Inventory inventory = player.getInventory();
            ItemStack[] itemStacks = inventory.getContents();
            for(int i = 0; i < itemStacks.length; i++){
                if(itemStacks[i] != null){
                    ItemStack itemStack = itemStacks[i];
                    if(!tookspellbook) {
                        if (mageSpellsManager.spellBookManager.isSameSpellBookIgnoreAmount(itemStack, spellObject.getSpellbook())) {
                            if (itemStack.getAmount() == 1) {
                                itemStacks[i] = new ItemStack(Material.AIR);
                                tookspellbook = true;
                            } else {
                                itemStack.setAmount(itemStack.getAmount() - 1);
                                tookspellbook = true;
                            }
                        }
                    }
                    if(!tookwanditem) {
                        if (mageSpellsManager.wandManager.isSameWandIgnoreAmount(wanditem, itemStack)) {
                            if (itemStack.getAmount() == 1) {
                                itemStacks[i] = new ItemStack(Material.AIR);
                                tookwanditem = true;
                            } else {
                                itemStack.setAmount(itemStack.getAmount() - 1);
                                tookwanditem = true;
                            }
                        }
                    }
                }
            }
            player.getInventory().setContents(itemStacks);
            player.updateInventory();
            return tookspellbook && tookwanditem;
        }catch(Exception ex){
            return false;
        }
    }
}
