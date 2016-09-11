package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.GUI.*;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 8/9/2016.
 */
public class InventoryListener implements Listener{
    private MageSpellsManager mageSpellsManager;
    private PlayerInterface playerInterface;
    private SpellUI spellUI;
    private WandBag wandBag;
    private MageStats mageStats;
    private WandUI wandUI;
    private BindingMenu bindingMenu;

    public InventoryListener(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
        playerInterface = mageSpellsManager.playerInterface;
        spellUI = mageSpellsManager.spellUI;
        wandBag = mageSpellsManager.wandBag;
        mageStats = mageSpellsManager.mageStats;
        wandUI = mageSpellsManager.wandUI;
        bindingMenu = mageSpellsManager.bindingMenu;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        if(playerInterface.inPlayerInterface(event.getPlayer())){
            playerInterface.closePlayerInterface(event.getPlayer());
        }
        if(spellUI.inSpellUI(event.getPlayer())){
            spellUI.closeSpellUI(event.getPlayer());
        }
        if(mageStats.inMageStats(event.getPlayer())){
            mageStats.closeMageStats(event.getPlayer());
        }
        if(wandBag.inWandBag(event.getPlayer())){
            wandBag.closeWandBag(event.getPlayer());
        }
        if(wandUI.inWandUI(event.getPlayer())){
            wandUI.closeWandUI(event.getPlayer());
        }
        if(bindingMenu.inSpellBindingMenu(event.getPlayer())){
            bindingMenu.closeSpellBindingMenu(event.getPlayer());
        }else if(bindingMenu.inWandBindingMenu(event.getPlayer())){
            bindingMenu.closeWandBindingMenu(event.getPlayer());
        }else if(bindingMenu.inSelectionMenu(event.getPlayer())){
            bindingMenu.closeSelectionmenu(event.getPlayer());
        }
    }
    @EventHandler
    public void selectMenu(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(mageSpellsManager.mageManager.isMage(player)) {
                if (spellUI.inSpellUI(player)) {
                    int slot = event.getSlot();
                    switch (slot) {
                        case 0:
                            spellUI.previousPage(player);
                            break;
                        case 8:
                            spellUI.nextPage(player);
                            break;
                        default:
                            break;
                    }
                    event.setCancelled(true);
                }
                if (wandUI.inWandUI(player)) {
                    int slot = event.getSlot();
                    switch (slot) {
                        case 0:
                            wandUI.previousPage(player);
                            break;
                        case 8:
                            wandUI.nextPage(player);
                            break;
                        default:
                            break;
                    }
                    event.setCancelled(true);
                }
                if (mageStats.inMageStats(player)) {
                    event.setCancelled(true);
                }
                if (wandBag.inWandBag(player)) {
                    if (event.getCurrentItem() != null && event.getCursor() != null) {
                        if ((!mageSpellsManager.wandManager.isWand(event.getCurrentItem()) && event.getCursor().getType() == Material.AIR) || (!mageSpellsManager.wandManager.isWand(event.getCursor()) && event.getCurrentItem().getType() == Material.AIR)) {
                            event.setCancelled(true);
                        }
                    }
                }
                if (bindingMenu.inSpellBindingMenu(player)) {
                    if (event.getCurrentItem() != null && event.getCursor() != null) {
                        if (mageSpellsManager.spellBookManager.isSpellBook(event.getCurrentItem())) {
                            ItemStack itemStack = event.getCurrentItem().clone();
                            itemStack.setAmount(1);
                            SpellObject spellObject = mageSpellsManager.spellBookManager.getSpellFromBook(itemStack);
                            bindingMenu.openWandBindingMenu(player, spellObject);
                            bindingMenu.closeSpellBindingMenu(player);
                        }
                        event.setCancelled(true);
                    }
                } else if (bindingMenu.inWandBindingMenu(player)) {
                    if (event.getCurrentItem() != null && event.getCursor() != null) {
                        if (mageSpellsManager.wandManager.isWand(event.getCurrentItem())) {
                            ItemStack itemStack = event.getCurrentItem().clone();
                            itemStack.setAmount(1);
                            bindingMenu.openSelectionMenu(player, itemStack);
                            bindingMenu.closeWandBindingMenu(player);
                        }
                        event.setCancelled(true);
                    }
                } else if (bindingMenu.inSelectionMenu(player)) {
                    int slot = event.getSlot();
                    switch (slot) {
                        case 3:
                            mageSpellsManager.bindingManager.bind(player, bindingMenu.getSpellBookFromSelectionMenu(player), bindingMenu.getItemStackFromMenu(player), true);
                            bindingMenu.closeSelectionmenu(player);
                            player.closeInventory();
                            break;
                        case 5:
                            mageSpellsManager.bindingManager.bind(player, bindingMenu.getSpellBookFromSelectionMenu(player), bindingMenu.getItemStackFromMenu(player), false);
                            bindingMenu.closeSelectionmenu(player);
                            player.closeInventory();
                            break;
                        default:
                            break;
                    }

                    event.setCancelled(true);
                }
                if (playerInterface.inPlayerInterface(player)) {
                    int slot = event.getSlot();
                    switch (slot) {
                        case 2:
                            playerInterface.closePlayerInterface(player);
                            wandUI.openWandUI(player);
                            break;
                        case 3:
                            playerInterface.closePlayerInterface(player);
                            spellUI.openSpellUI(player);
                            break;
                        case 4:
                            playerInterface.closePlayerInterface(player);
                            wandBag.openWandBag(player);
                            System.out.println("called:" + slot);
                            break;
                        case 5:
                            playerInterface.closePlayerInterface(player);
                            mageStats.openMageStats(player);
                            break;
                        case 6:
                            playerInterface.closePlayerInterface(player);
                            bindingMenu.openSpellBindingMenu(player);
                        default:
                            break;
                    }
                    event.setCancelled(true);
                }
            }
        }

    }
    @EventHandler
    public void otherOption(InventoryDragEvent event){
        Player player = (Player) event.getWhoClicked();
        if(playerInterface.inPlayerInterface(player)){
            event.setCancelled(true);
        }
        if(spellUI.inSpellUI(player)){
            event.setCancelled(true);
        }
        if(mageStats.inMageStats(player)){
            event.setCancelled(true);
        }
        if(wandBag.inWandBag(player)){
            if(event.getCursor() != null) {
                if (!mageSpellsManager.wandManager.isWand(event.getCursor())) {
                    event.setCancelled(true);
                }
            }
        }
        if(wandUI.inWandUI(player)){
            event.setCancelled(true);
        }
        if(bindingMenu.inSpellBindingMenu(player)){
            event.setCancelled(true);
        }
        if(bindingMenu.inWandBindingMenu(player)){
            event.setCancelled(true);
        }
        if(bindingMenu.inSelectionMenu(player)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void exitInterface(InventoryCloseEvent event){
        Player player =(Player) event.getPlayer();
        if(playerInterface.inPlayerInterface(player)){
            playerInterface.closePlayerInterface(player);
        }
        if(spellUI.inSpellUI(player)){
            spellUI.closeSpellUI(player);
        }
        if(mageStats.inMageStats(player)){
            mageStats.closeMageStats(player);
        }
        if(wandBag.inWandBag(player)){
            wandBag.closeWandBag(player);
        }
        if(wandUI.inWandUI(player)){
            wandUI.closeWandUI(player);
        }
        if(bindingMenu.inSpellBindingMenu(player)){
            bindingMenu.closeSpellBindingMenu(player);
        }else if(bindingMenu.inWandBindingMenu(player)){
            bindingMenu.closeWandBindingMenu(player);
        }else if(bindingMenu.inSelectionMenu(player)){
            bindingMenu.closeSelectionmenu(player);
        }
    }
    private void checkAction(InventoryClickEvent event, InventoryAction action) {
        switch (action) {
            case CLONE_STACK:
                // If I'm correct, this is a creative action
                // At the time of writing, not 100% sure what the triggers were
                // But most people don't deal with creative ;3
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                break;
            case COLLECT_TO_CURSOR:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Double clicking to collect a certain item
                break;
            case DROP_ALL_CURSOR:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Clicking next to the inventory with items on the cursor
            case DROP_ALL_SLOT:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // When you CTRL+click a slot, it drops all the items from the slot
                break;
            case HOTBAR_MOVE_AND_READD:
                // This only happens when stuff gets moved towards the
                //  hotbar
                // Stealing items
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                break;
            case HOTBAR_SWAP:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Swapping an item with the hotbar
                // This is the number key swapping, you can get the number from
                // InventoryClickEvent#getHotbarButton() (returns 0-8 instead of 1-9)
                break;
            case MOVE_TO_OTHER_INVENTORY:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Shift clicking with basically, when there are two inventories involved
                // Like when you are interacting with a chest
                break;
            case NOTHING:
                // Did we even do anything? I guess not
                break;
            case PICKUP_ONE:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            case PICKUP_HALF:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            case PICKUP_SOME:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            case PICKUP_ALL:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Picking up an item from a slot, with an empty cursor
                break;
            case PLACE_ALL:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            case PLACE_ONE:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            case PLACE_SOME:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Related to placing an item in an empty slot
                break;
            case SWAP_WITH_CURSOR:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // Clicking with something on the cursor
                // If the slot is empty, look at the PLACE actions above
                break;
            case UNKNOWN:
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
                // How am I supposed to handle that what
                //  Bukkit cannot even properly name xD
                break;
        }
    }
}
