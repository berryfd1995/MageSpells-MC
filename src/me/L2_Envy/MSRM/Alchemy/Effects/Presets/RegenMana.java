package me.L2_Envy.MSRM.Alchemy.Effects.Presets;

import me.L2_Envy.MSRM.Alchemy.Interfaces.MagePotionEffect;
import me.L2_Envy.MSRM.Alchemy.Objects.ActiveMagePotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

/**
 * Created by berry on 9/19/2016.
 */
public class RegenMana implements MagePotionEffect {
    private String name;
    public RegenMana(){

    }
    @Override
    public void onHit(LivingEntity livingEntity) {

    }

    @Override
    public void setMagePotion(ActiveMagePotion activeMagePotion) {

    }

    @Override
    public String getName() {
        return name;
    }
}
