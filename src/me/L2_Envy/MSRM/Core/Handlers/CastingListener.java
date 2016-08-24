package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 7/23/2016.
 */
public class CastingListener implements Listener {
    public MageSpellsManager mageSpellsManager;
    public CastingListener(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    @EventHandler
    public void onSpellCast(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if(player.getInventory().getItemInMainHand().equals(itemStack)) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (mageSpellsManager.mageManager.isMage(player)) {
                    PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
                    SpellObject spellObject = mageSpellsManager.wandManager.getSpellFromPrimary(itemStack);
                    WandObject wandObject = mageSpellsManager.wandManager.getWandFromItem(itemStack);
                    if(spellObject != null && wandObject != null){
                        mageSpellsManager.castingManager.castSpell(player,playerObject, spellObject, wandObject);
                    }
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (mageSpellsManager.mageManager.isMage(player)) {
                    PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
                    SpellObject spellObject = mageSpellsManager.wandManager.getSpellFromSecondary(itemStack);
                    WandObject wandObject = mageSpellsManager.wandManager.getWandFromItem(itemStack);
                    if(spellObject != null && wandObject != null){
                        mageSpellsManager.castingManager.castSpell(player,playerObject, spellObject, wandObject);
                    }
                }
            }
        }
    }
}
