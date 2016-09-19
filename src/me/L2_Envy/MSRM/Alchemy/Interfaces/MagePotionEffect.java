package me.L2_Envy.MSRM.Alchemy.Interfaces;

import me.L2_Envy.MSRM.Alchemy.Objects.ActiveMagePotion;
import me.L2_Envy.MSRM.Alchemy.Objects.MagePotion;
import org.bukkit.entity.LivingEntity;

/**
 * Created by berry on 9/19/2016.
 */
public interface MagePotionEffect {
    public void setMagePotion(ActiveMagePotion activeMagePotion);
    public void onHit(LivingEntity livingEntity);
    public String getName();
}
