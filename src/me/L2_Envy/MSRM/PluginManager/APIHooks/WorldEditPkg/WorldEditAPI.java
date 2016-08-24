package me.L2_Envy.MSRM.PluginManager.APIHooks.WorldEditPkg;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.L2_Envy.MSRM.PluginManager.PluginManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by Daniel on 7/23/2016.
 */
public class WorldEditAPI {
    public PluginManager pluginManager;
    public WorldGuardPlugin worldGuardPlugin;
    public WorldEditPlugin worldEditPlugin;
    private WorldEditHook worldEditHook;
    public WorldEditAPI(){
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
        worldEditPlugin = getWorldEdit();
        worldGuardPlugin = getWorldGuard();
        try {
            if(worldGuardPlugin != null && worldEditPlugin != null) {
                this.worldEditHook = new WorldEditHook();
                this.worldEditHook.link(this);
            }else{
                this.worldEditHook = null;
            }
        } catch (NoClassDefFoundError e) {
            this.worldEditHook = null;
            pluginManager.main.logger.info("WorldEdit and WorldGuard were not loaded!");
        }
    }
    public boolean allowSpellInRegion(Location location){
        if(worldEditHook != null) {
            return worldEditHook.allowSpellInRegion(location);
        }else{
            return true;
        }
    }
    private WorldEditPlugin getWorldEdit() {
        Plugin plugin = pluginManager.main.getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null;
        }
        return (WorldEditPlugin) plugin;
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = pluginManager.main.getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
        return (WorldGuardPlugin) plugin;
    }
}
