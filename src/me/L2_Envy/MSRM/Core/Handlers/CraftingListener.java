package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 7/23/2016.
 */
public class CraftingListener implements Listener{
    public MageSpellsManager mageSpellsManager;
    public CraftingListener(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    @EventHandler
    public void onCrafting(CraftItemEvent event){
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(mageSpellsManager.mageManager.isMage(player)) {
                PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
                 for (WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()) {
                    if (event.getRecipe().getResult().equals(wandObject.getWandItemStack())) {
                        if ((event.getWhoClicked().hasPermission("magespells.wand." + wandObject.getWandnode())|| !mageSpellsManager.isNodeSystemEnabled())
                                && (!mageSpellsManager.levelingManager.isLevelingEnabled()|| playerObject.getLevel() >= wandObject.getRequiredleveltocraft())
                                && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))) {
                        }else{
                            event.setCancelled(true);
                            event.getWhoClicked().sendMessage(ChatColor.RED+"You cannot craft this item!");
                            System.out.println(event.getWhoClicked().hasPermission("magespells.wand." + wandObject.getWandnode()) + "||"+!mageSpellsManager.isNodeSystemEnabled());
                            System.out.println(!mageSpellsManager.levelingManager.isLevelingEnabled()+"||"+(playerObject.getLevel() >= wandObject.getRequiredleveltocraft()));
                            System.out.println(!mageSpellsManager.spellLearningManager.isLearningEnabled() +"||"+ playerObject.knowsWand(wandObject));
                            System.out.println(playerObject.getLevel() + "<" + wandObject.getRequiredleveltocraft());
                        }
                    }
                }
            }
        }

    }

}
