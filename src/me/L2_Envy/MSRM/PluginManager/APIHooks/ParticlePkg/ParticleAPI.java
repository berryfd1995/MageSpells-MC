package me.L2_Envy.MSRM.PluginManager.APIHooks.ParticlePkg;

import me.L2_Envy.MSRM.PluginManager.PluginManager;

/**
 * Created by Daniel on 7/24/2016.
 */
public class ParticleAPI {
    public PluginManager pluginManager;
    public ParticleAPI(){
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }
}
