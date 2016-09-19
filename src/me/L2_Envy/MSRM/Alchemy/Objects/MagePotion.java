package me.L2_Envy.MSRM.Alchemy.Objects;

import me.L2_Envy.MSRM.Core.Objects.ParticleObject;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

/**
 * Created by berry on 9/19/2016.
 */
public class MagePotion {
    private String name;
    private String displayname;
    private String lore;

    private boolean affectMobs;
    private boolean affectEnemy;
    private boolean affectTeam;
    private boolean affectSelf;

    private int duration;
    private int amplification;

    private ArrayList<ParticleObject> particleObjects;
    private ArrayList<PotionEffect> potionEffects;
    public MagePotion(){

    }
}
