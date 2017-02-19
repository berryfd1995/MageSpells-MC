package me.L2_Envy.MSRM.Alchemy.Objects;

import me.L2_Envy.MSRM.Alchemy.Interfaces.MagePotionEffect;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by berry on 9/19/2016.
 */
public class ActiveMagePotion extends MagePotion{
    private Player caster;
    private Item potion;
    public ActiveMagePotion(MagePotion magePotion){
        super(magePotion.getName(), magePotion.getDisplayname(), magePotion.getLore(), magePotion.isAffectMobs(),magePotion.isAffectEnemy(),
        magePotion.isAffectTeam(), magePotion.isAffectSelf(), magePotion.getDuration(), magePotion.getAmplification(),magePotion.getParticleObjects(),
                magePotion.getPotionEffects(),magePotion.getCustomRecipe(),magePotion.getPotion());
    }
}
