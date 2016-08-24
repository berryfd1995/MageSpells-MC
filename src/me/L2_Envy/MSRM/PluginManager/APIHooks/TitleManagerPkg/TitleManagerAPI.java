package me.L2_Envy.MSRM.PluginManager.APIHooks.TitleManagerPkg;

import me.L2_Envy.MSRM.PluginManager.PluginManager;

/**
 * Created by Daniel on 7/23/2016.
 */
public class TitleManagerAPI {
    public PluginManager pluginManager;
    public TitleManagerAPI(){
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }
}
