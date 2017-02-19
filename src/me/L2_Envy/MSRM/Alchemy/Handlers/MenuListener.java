package me.L2_Envy.MSRM.Alchemy.Handlers;

import me.L2_Envy.MSRM.Alchemy.AlchemyManager;
import me.L2_Envy.MSRM.Alchemy.GUI.BrewingMenu;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.peer.LabelPeer;

/**
 * Created by Daniel on 7/28/2016.
 */
public class MenuListener implements Listener {
    private AlchemyManager alchemyManager;
    private BrewingMenu brewingMenu;
    private MageSpellsManager mageSpellsManager;
    public MenuListener(AlchemyManager alchemyManager){
        this.alchemyManager = alchemyManager;
        this.brewingMenu = alchemyManager.brewingMenu;
        this.mageSpellsManager = alchemyManager.main.mageSpellsManager;
    }
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        if(brewingMenu.inBrewingMenu(event.getPlayer())){
            brewingMenu.closeBrewingMenu(event.getPlayer());
        }
    }
    @EventHandler
    public void selectMenu(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(alchemyManager.main.mageSpellsManager.mageManager.isMage(player)) {
                if (brewingMenu.inBrewingMenu(player)){
                    int slot = event.getRawSlot();
                    switch (slot){
                        case 11:
                            break;
                        case 15:
                            break;
                        case 40: // result
                            if(event.getCursor() == null ||event.getCursor().getType() == Material.AIR|| event.getCursor().getType() == Material.GLASS_BOTTLE ||
                                    event.getCursor().getType() == Material.POTION || event.getCursor().getType() == Material.SPLASH_POTION
                                    || event.getCursor().getType() == Material.LINGERING_POTION){

                            }else{
                                checkAction(event,event.getAction());
                            }
                            break;
                        case 13://brew
                            if(alchemyManager.canCombine(event.getInventory().getItem(11), event.getInventory().getItem(15), event.getInventory().getItem(40))){
                                //combine duration
                                alchemyManager.combineItems(event.getInventory().getItem(11), event.getInventory().getItem(15), event.getInventory().getItem(40));
                            }
                            break;
                        default:
                            if(slot >=54 && slot <= 89) {

                            }else{
                                checkAction(event, event.getAction());
                            }
                    }
                   /* checkAction(event, event.getAction());
                    if (event.getCurrentItem() != null && event.getCursor() != null) {
                        event.setCancelled(true);
                    }*/
                }
                if (mageSpellsManager.playerInterface.inPlayerInterface(player)) {
                    int slot = event.getSlot();
                    switch (slot) {
                        /*case 7:
                            mageSpellsManager.playerInterface.closePlayerInterface(player);
                            brewingMenu.openBrewingMenu(player);
                            break;*/
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
        if(brewingMenu.inBrewingMenu(player)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void exitInterface(InventoryCloseEvent event){
        Player player =(Player) event.getPlayer();
        if(brewingMenu.inBrewingMenu(player)){
            if(player.getOpenInventory().getItem(11) != null){
                player.getInventory().addItem(player.getOpenInventory().getItem(11));
            }
            if(player.getOpenInventory().getItem(15) != null){
                player.getInventory().addItem(player.getOpenInventory().getItem(15));
            }
            if(player.getOpenInventory().getItem(40) != null){
                player.getInventory().addItem(player.getOpenInventory().getItem(40));
            }
            brewingMenu.closeBrewingMenu(player);
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
