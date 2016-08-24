package me.L2_Envy.MSRM.API;

import me.L2_Envy.MSRM.PluginManager.PluginManager;

/**
 * Created by Daniel on 7/23/2016.
 */
public class MageSpellsAPI {
    public PluginManager pluginManager;
    public MageSpellsAPI(){
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }
}
