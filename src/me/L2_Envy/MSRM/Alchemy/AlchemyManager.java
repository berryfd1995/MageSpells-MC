package me.L2_Envy.MSRM.Alchemy;

import me.L2_Envy.MSRM.Alchemy.Effects.AlchemyEffectManager;
import me.L2_Envy.MSRM.Alchemy.GUI.BrewingMenu;
import me.L2_Envy.MSRM.Main;

/**
 * Created by berry on 9/19/2016.
 */
public class AlchemyManager {
    public Main main;
    public AlchemyEffectManager alchemyEffectManager;
    public BrewingMenu brewingMenu;
    public AlchemyManager(){
        alchemyEffectManager = new AlchemyEffectManager();
        brewingMenu = new BrewingMenu();
    }
    public void linkAll(Main main){
        this.main = main;
        alchemyEffectManager.link(this);
        brewingMenu.link(this);
    }
}
