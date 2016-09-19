package me.L2_Envy.MSRM.Alchemy.Effects;

import me.L2_Envy.MSRM.Alchemy.AlchemyManager;

/**
 * Created by berry on 9/19/2016.
 */
public class AlchemyEffectManager {
    public AlchemyManager alchemyManager;
    public AlchemyEffectManager(){

    }
    public void link(AlchemyManager alchemyManager){
        this.alchemyManager = alchemyManager;
    }
}
