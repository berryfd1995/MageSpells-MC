package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * Created by Daniel on 7/28/2016.
 */
public class EnchantingListener implements Listener{
    public MageSpellsManager mageSpellsManager;
    public EnchantingListener(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
}
