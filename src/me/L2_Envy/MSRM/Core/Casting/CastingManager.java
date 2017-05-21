package me.L2_Envy.MSRM.Core.Casting;

import me.L2_Envy.MSRM.Core.Effects.Preset.HomingSpellEffect;
import me.L2_Envy.MSRM.Core.Effects.Preset.NormalEffect;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

/**
 * Created by Daniel on 7/23/2016.
 */
public class CastingManager {
    public MageSpellsManager mageSpellsManager;
    public CastingManager(){
        HomingSpellEffect homingSpellEffect = new HomingSpellEffect();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void castSpell(Player player, PlayerObject playerObject, SpellObject spellObject, WandObject wandObject){
        if(!playerObject.isOnCooldown()&&playerObject.isReadytocast() && !playerObject.isCharging() &&
                (!mageSpellsManager.levelingManager.isLevelingEnabled() ||(playerObject.getLevel() >= spellObject.getRequiredLevelToCast() &&
                playerObject.getLevel() >= wandObject.getRequiredleveltouse()))
                && (!mageSpellsManager.isNodeSystemEnabled() ||( player.hasPermission("magespells.wand." + wandObject.getWandnode())
                && player.hasPermission("magespells.spell." + spellObject.getSpellNode()) && wandObject.isSpellCompatible(spellObject.getSpellNode())))
                && mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(player.getLocation())) {
            if(mageSpellsManager.manaManager.hasAtLeastMana(playerObject, spellObject.getManacost())) {
                if(spellObject.isItemcostenabled()) {
                    if(hasItemCost(player, spellObject)) {
                        if(spellObject.getManacost() >0) {
                            mageSpellsManager.manaManager.removeMana(playerObject, spellObject.getManacost());
                        }
                        takeItemCosts(player, spellObject);
                        if (spellObject.getChargetime() > 0) {
                            mageSpellsManager.manaManager.scheduleChargeTask(player.getName(), playerObject, spellObject, spellObject.getChargetime());
                        }else{
                            doSpellCasting(player.getName(), spellObject);
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "You do not have the required items.");
                    }
                }else{
                    if(spellObject.getManacost() > 0) {
                        mageSpellsManager.manaManager.removeMana(playerObject, spellObject.getManacost());
                    }
                    if (spellObject.getChargetime() > 0) {
                        mageSpellsManager.manaManager.scheduleChargeTask(player.getName(), playerObject, spellObject, spellObject.getChargetime());
                    }else{
                        doSpellCasting(player.getName(), spellObject);
                    }
                }
            }
        }
    }
    public void doSpellCasting(String name, SpellObject spellObject){
        Player player  = mageSpellsManager.main.utils.getOnlinePlayerFromName(name);
        PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
        if(spellObject.getCooldown() > 0){
            mageSpellsManager.manaManager.scheduleCooldownTask(playerObject,spellObject.getCooldown());
        }
        if(spellObject.getCastcommand() != null && !spellObject.getCastcommand().isEmpty() && spellObject.getCastcommand().length() > 0){
            player.setOp(true);
            player.performCommand(spellObject.getCastcommand());
            player.setOp(false);
        }
        mageSpellsManager.activeSpellManager.shootSpell(mageSpellsManager.spellEffectManager.setupSpellEffect(spellObject, player));
    }
    @SuppressWarnings( "deprecation" )
    public void takeItemCosts(Player player, SpellObject spell){
        Inventory inventory = player.getInventory();
        HashMap<ItemStack, Integer> itemCosts = spell.getItemcost();
        for(ItemStack is : itemCosts.keySet()) {
            int amount = itemCosts.get(is);
            ItemStack[] itemStacks = inventory.getContents();
            for(int i = 0; i < itemStacks.length; i++){
                if(amount >0) {
                    if (itemStacks[i] != null) {
                        ItemStack itemStack = itemStacks[i];
                        ItemStack itemStack1 = itemStack.clone();
                        itemStack1.setAmount(1);
                        //is custom item
                        if(!mageSpellsManager.wandManager.isWand(itemStack)) {
                            if (mageSpellsManager.main.utils.itemStackIsCustomItem(is)) {
                                if (is.equals(itemStack1)) {
                                    if (itemStack.getAmount() > amount) {
                                        itemStack.setAmount(itemStack.getAmount() - amount);
                                        amount = 0;
                                    } else {
                                        amount = amount - itemStack.getAmount();
                                        itemStacks[i] = new ItemStack(Material.AIR);
                                    }
                                }
                            } else {
                                if (itemStack.getType() == is.getType() && itemStack.getDurability() == is.getDurability()) {
                                    if (itemStack.hasItemMeta() && is.hasItemMeta()) {
                                        if (itemStack.getItemMeta().hasDisplayName() && is.getItemMeta().hasDisplayName()) {
                                            if (itemStack.getItemMeta().hasLore() && is.getItemMeta().hasLore()) {
                                                if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(is.getItemMeta().getDisplayName()) && itemStack.getItemMeta().getLore().equals(is.getItemMeta().getLore())) {
                                                    if (itemStack.getAmount() > amount) {
                                                        itemStack.setAmount(itemStack.getAmount() - amount);
                                                        amount = 0;
                                                    } else {
                                                        amount = amount - itemStack.getAmount();
                                                        itemStacks[i] = new ItemStack(Material.AIR);
                                                    }
                                                }
                                            } else {
                                                if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(is.getItemMeta().getDisplayName())) {
                                                    if (itemStack.getAmount() > amount) {
                                                        itemStack.setAmount(itemStack.getAmount() - amount);
                                                        amount = 0;
                                                    } else {
                                                        amount = amount - itemStack.getAmount();
                                                        itemStacks[i] = new ItemStack(Material.AIR);
                                                    }
                                                }
                                            }
                                        } else if (itemStack.getItemMeta().hasLore() && is.getItemMeta().hasLore()) {
                                            if (itemStack.getItemMeta().getLore().equals(is.getItemMeta().getLore())) {
                                                if (itemStack.getAmount() > amount) {
                                                    itemStack.setAmount(itemStack.getAmount() - amount);
                                                    amount = 0;
                                                } else {
                                                    amount = amount - itemStack.getAmount();
                                                    itemStacks[i] = new ItemStack(Material.AIR);
                                                }
                                            }
                                        }
                                    } else {
                                        if (itemStack.getAmount() > amount) {
                                            itemStack.setAmount(itemStack.getAmount() - amount);
                                            amount = 0;
                                        } else {
                                            amount = amount - itemStack.getAmount();
                                            itemStacks[i] = new ItemStack(Material.AIR);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            player.getInventory().setContents(itemStacks);
        }
        player.updateInventory();
    }
    public boolean hasItemCost(Player player, SpellObject spell){
        Inventory inventory = player.getInventory();
        HashMap<ItemStack, Integer> itemCosts = spell.getItemcost();
        boolean has = true;
        for(ItemStack is : itemCosts.keySet()) {
            if (!inventory.containsAtLeast(is, itemCosts.get(is))){
                has = false;
            }
        }
        return has;
    }
}
