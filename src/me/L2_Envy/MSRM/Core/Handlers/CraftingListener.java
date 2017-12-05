package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.CustomItemObject;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 7/23/2016.
 */
public class CraftingListener implements Listener {
    public MageSpellsManager mageSpellsManager;

    public CraftingListener(MageSpellsManager mageSpellsManager) {
        this.mageSpellsManager = mageSpellsManager;
    }

    /*@EventHandler
    public void itemPlaceInCrafting(InventoryClickEvent event) {
        boolean found = false;
        if (event.getInventory() instanceof CraftingInventory) {
            CraftingInventory craftingInventory = (CraftingInventory) event.getInventory();
            for (WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()) {
                ItemStack[] matrix = craftingInventory.getMatrix();
                if (mageSpellsManager.main.utils.compareMatrixes(wandObject.getMatrix(), matrix)) {
                    found = true;
                    craftingInventory.setResult(wandObject.getWandItemStack());
                }
            }
            if (!found) {
                for (SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()) {
                    if (spellObject.isCraftingenabled()) {
                        ItemStack[] matrix = craftingInventory.getMatrix();
                        if (mageSpellsManager.main.utils.compareMatrixes(spellObject.getMatrix(), matrix)) {
                            found = true;
                            craftingInventory.setResult(spellObject.getSpellbook());
                        }
                    }
                }
            }
            if (!found) {
                for (CustomItemObject customItemObject : mageSpellsManager.main.utils.customitems) {
                    ItemStack[] matrix = craftingInventory.getMatrix();
                    if (mageSpellsManager.main.utils.compareMatrixes(customItemObject.getMatrix(), matrix)) {
                        found = true;
                        craftingInventory.setResult(customItemObject.getCustomitem());
                    }
                }
            }
        }
    }*/

    @EventHandler
    public void onShowResult(PrepareItemCraftEvent event) {
        boolean found = false;
        for (WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()) {
            if (event.getRecipe().getResult().equals(wandObject.getWandItemStack())) {
                found = true;
                ItemStack[] matrix = event.getInventory().getMatrix();
                if (!mageSpellsManager.main.utils.compareMatrixes(wandObject.getMatrix(), matrix)) {
                    event.getInventory().setResult(null);
                } else {
                }
            }
        }
        if (!found) {
            for (SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()) {
                if (spellObject.isCraftingenabled()) {
                    if (event.getRecipe().getResult().equals(spellObject.getSpellbook())) {
                        found = true;
                        ItemStack[] matrix = event.getInventory().getMatrix();
                        if (!mageSpellsManager.main.utils.compareMatrixes(spellObject.getMatrix(), matrix)) {
                            event.getInventory().setResult(null);

                        } else {
                        }
                    }
                }
            }
        }
        if (!found) {
            for (CustomItemObject customItemObject : mageSpellsManager.main.utils.customitems) {
                if (event.getRecipe().getResult().equals(customItemObject)) {
                    found = true;
                    ItemStack[] matrix = event.getInventory().getMatrix();
                    if (!mageSpellsManager.main.utils.compareMatrixes(customItemObject.getMatrix(), matrix)) {
                        event.getInventory().setResult(null);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCrafting(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (mageSpellsManager.mageManager.isMage(player)) {
                PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
                boolean found = false;
                for (WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()) {
                    if (event.getInventory().getResult() != null) {
                        if (event.getInventory().getResult().equals(wandObject.getWandItemStack())) {
                            found = true;
                            if ((event.getWhoClicked().hasPermission("magespells.wand." + wandObject.getWandnode()) || !mageSpellsManager.isNodeSystemEnabled())
                                    && (!mageSpellsManager.levelingManager.isLevelingEnabled() || playerObject.getLevel() >= wandObject.getRequiredleveltocraft())
                                    && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))) {
                            } else {
                                event.setCancelled(true);
                                event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot craft this item!");
                            /*System.out.println(event.getWhoClicked().hasPermission("magespells.wand." + wandObject.getWandnode()) + "||"+!mageSpellsManager.isNodeSystemEnabled());
                            System.out.println(!mageSpellsManager.levelingManager.isLevelingEnabled()+"||"+(playerObject.getLevel() >= wandObject.getRequiredleveltocraft()));
                            System.out.println(!mageSpellsManager.spellLearningManager.isLearningEnabled() +"||"+ playerObject.knowsWand(wandObject));
                            System.out.println(playerObject.getLevel() + ">" + wandObject.getRequiredleveltocraft());*/
                            }
                        }
                    }
                }
                if (!found) {
                    for (SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()) {
                        if (spellObject.isCraftingenabled()) {
                            if (event.getInventory().getResult() != null) {

                                if (event.getInventory().getResult().equals(spellObject.getSpellbook())) {
                                    found = true;
                                    if ((event.getWhoClicked().hasPermission("magespells.spell." + spellObject.getSpellNode()) || !mageSpellsManager.isNodeSystemEnabled())
                                            && (!mageSpellsManager.levelingManager.isLevelingEnabled() || playerObject.getLevel() >= spellObject.getRequiredleveltocraft())
                                            && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))) {

                                    } else {
                                        event.setCancelled(true);
                                        event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot craft this spell!");
                                 /*System.out.println(event.getWhoClicked().hasPermission("magespells.wand." + spellObject.getSpellNode()) + "||"+!mageSpellsManager.isNodeSystemEnabled());
                                 System.out.println(!mageSpellsManager.levelingManager.isLevelingEnabled()+"||"+(playerObject.getLevel() >= spellObject.getRequiredleveltocraft()));
                                 System.out.println(!mageSpellsManager.spellLearningManager.isLearningEnabled() +"||"+ playerObject.knowsSpell(spellObject));
                                 System.out.println(playerObject.getLevel() + ">" + spellObject.getRequiredleveltocraft());*/
                                    }
                                }
                            }
                        }
                    }
                }
                if (!found) {
                    for (CustomItemObject customItemObject : mageSpellsManager.main.utils.customitems) {
                        if (event.getInventory().getResult() != null) {

                            if (event.getInventory().getResult().equals(customItemObject)) {
                                found = true;
                                if (!mageSpellsManager.levelingManager.isLevelingEnabled() || playerObject.getLevel() >= customItemObject.getRequiredlevel()) {

                                } else {
                                    event.setCancelled(true);
                                    event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot craft this item!");
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
