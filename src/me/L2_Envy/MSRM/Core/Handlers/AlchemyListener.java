package me.L2_Envy.MSRM.Core.Handlers;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import org.bukkit.event.Listener;

import java.awt.peer.LabelPeer;

/**
 * Created by Daniel on 7/28/2016.
 */
public class AlchemyListener implements Listener {
    public MageSpellsManager mageSpellsManager;
    public AlchemyListener(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
}