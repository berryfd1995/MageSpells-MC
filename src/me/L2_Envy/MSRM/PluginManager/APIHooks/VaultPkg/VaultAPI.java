package me.L2_Envy.MSRM.PluginManager.APIHooks.VaultPkg;

import me.L2_Envy.MSRM.PluginManager.PluginManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Created by Daniel on 7/24/2016.
 */
public class VaultAPI {
    public PluginManager pluginManager;
    public static Economy economy = null;
    public VaultHook vaultHook;
    public VaultAPI(){
        vaultHook = new VaultHook();
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
        this.vaultHook.link(this);
    }
    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = pluginManager.main.getServer().getServicesManager().getRegistration(net
                .milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
    public Economy getEconomy(){
        return economy;
    }
}
