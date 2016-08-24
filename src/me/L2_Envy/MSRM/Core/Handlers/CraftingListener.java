package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

}
