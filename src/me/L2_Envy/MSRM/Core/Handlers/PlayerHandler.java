package me.L2_Envy.MSRM.Core.Handlers;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Daniel on 8/8/2016.
 */
public class PlayerHandler implements Listener{
    public MageSpellsManager mageSpellsManager;
    public PlayerHandler(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    @EventHandler
    public void loadPlayerData(PlayerJoinEvent event){
        if(event.getPlayer().hasPermission("magespells.mage")) {
            mageSpellsManager.main.pluginManager.playerConfig.loadPlayerData(event.getPlayer().getName(), event.getPlayer().getUniqueId());
        }
    }
    @EventHandler
    public void savePlayerData(PlayerQuitEvent event){
        if(event.getPlayer().hasPermission("magespells.mage")) {
            if (mageSpellsManager.main.mageSpellsManager.mageManager.isMage(event.getPlayer())) {
                PlayerObject playerObject = mageSpellsManager.mageManager.getMage(event.getPlayer().getUniqueId());
                mageSpellsManager.manaManager.removeManaTask(playerObject);
                mageSpellsManager.main.pluginManager.playerConfig.savePlayerData(mageSpellsManager.main.mageSpellsManager.mageManager.getMage(event.getPlayer().getUniqueId()));
            }
        }
    }
}
