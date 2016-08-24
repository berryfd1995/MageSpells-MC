package me.L2_Envy.MSRM.PluginManager.APIHooks.VaultPkg;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Created by Daniel on 7/24/2016.
 */
public class VaultHook {
    private VaultAPI vaultAPI;
    public VaultHook(){

    }
    public void link(VaultAPI vaultAPI){
        this.vaultAPI = vaultAPI;
    }
    public boolean hasMoney(Player player,int something){
        return false;
    }

}
