package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by Daniel on 7/23/2016.
 */
public class EntityListener implements Listener{
    public MageSpellsManager mageSpellsManager;
    public EntityListener(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    @EventHandler
    public void dropBook(EntityDeathEvent entityDeathEvent){
        LivingEntity livingEntity = entityDeathEvent.getEntity();
        Player player = entityDeathEvent.getEntity().getKiller();
        if(player != null) {
            if(player.hasPermission("magespells.mage")) {
                if (mageSpellsManager.mageManager.isMage(player)) {
                    for (SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()) {
                        if(spellObject.isMobdropsenabled()) {
                            if (spellObject.getMobDrops().containsKey(livingEntity.getType())) {
                                if (mageSpellsManager.spellBookManager.shouldDrop(spellObject.getMobDrops().get(livingEntity.getType()))) {
                                    entityDeathEvent.getDrops().add(spellObject.getSpellbook());
                                }
                            }
                        }
                    }
                    for(WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()){
                        if(wandObject.isMobdropsenabled()){
                            if(wandObject.getMobDrops().containsKey(livingEntity.getType())){
                                if(mageSpellsManager.spellBookManager.shouldDrop(wandObject.getMobDrops().get(livingEntity.getType()))){
                                    entityDeathEvent.getDrops().add(wandObject.getWandItemStack());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
