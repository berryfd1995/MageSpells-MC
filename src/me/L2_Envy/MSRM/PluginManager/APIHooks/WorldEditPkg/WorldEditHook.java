package me.L2_Envy.MSRM.PluginManager.APIHooks.WorldEditPkg;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Daniel on 7/24/2016.
 */
public class WorldEditHook {
    private WorldEditAPI worldEditAPI;
    public WorldEditHook(){

    }
    public void link(WorldEditAPI worldEditAPI){
        this.worldEditAPI = worldEditAPI;
    }
    public boolean allowSpellInRegion(Location location) {
        boolean allow = false;
        if (worldEditAPI.worldGuardPlugin != null && worldEditAPI.worldEditPlugin != null) {
            ApplicableRegionSet set = worldEditAPI.worldGuardPlugin.getRegionManager(location.getWorld()).getApplicableRegions(location);
            if (set.size() > 0) {
                ProtectedRegion highestP = null;
                for (ProtectedRegion p : set) {
                    if(highestP == null){
                        highestP = p;
                    }else if(p.getPriority() > highestP.getPriority()){
                        highestP = p;
                    }
                }
                if (highestP.getFlag(DefaultFlag.PVP) != StateFlag.State.DENY  ) {
                    allow = true;
                }
            } else {
                allow = true;
            }
        }
        return allow;
    }
}
