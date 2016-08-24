package me.L2_Envy.MSRM.Core.Effects;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

/**
 * Created by Daniel on 7/24/2016.
 */
public class PotionEffectManager {
    public MageSpellsManager mageSpellsManager;
    public PotionEffectManager(){

    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void playPotionEffect(ActiveSpellObject activeSpellObject, LivingEntity livingEntity){
        for (PotionEffect potion : activeSpellObject.getPotionEffects()) {
            livingEntity.addPotionEffect(potion);
        }
    }
}
