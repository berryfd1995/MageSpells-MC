package me.L2_Envy.MSRM.Alchemy.Objects;

import me.L2_Envy.MSRM.Core.Objects.ParticleObject;
import net.minecraft.server.v1_11_R1.Item;
import org.bukkit.inventory.ItemStack;
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

    private CustomRecipe customRecipe;
    private ItemStack potion;
    public MagePotion(String name, String displayname, String lore, boolean affectMobs,
                      boolean affectEnemys, boolean affectTeam, boolean affectSelf, int duration,
                      int amplification, ArrayList<ParticleObject> particleObjects,
                      ArrayList<PotionEffect> potionEffects, CustomRecipe customRecipe, ItemStack potion){
        this.name = name;
        this.displayname = displayname;
        this.lore = lore;
        this.affectEnemy = affectEnemys;
        this.affectMobs = affectMobs;
        this.affectSelf = affectSelf;
        this.affectTeam = affectTeam;
        this.duration = duration;
        this.amplification = amplification;
        this.particleObjects = particleObjects;
        this.potionEffects = potionEffects;
        this.customRecipe = customRecipe;
        this.potion = potion;
    }
    public String getName() {
        return name;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getLore() {
        return lore;
    }

    public boolean isAffectMobs() {
        return affectMobs;
    }

    public boolean isAffectEnemy() {
        return affectEnemy;
    }

    public boolean isAffectTeam() {
        return affectTeam;
    }

    public boolean isAffectSelf() {
        return affectSelf;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplification() {
        return amplification;
    }

    public ArrayList<ParticleObject> getParticleObjects() {
        return particleObjects;
    }

    public ArrayList<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public CustomRecipe getCustomRecipe() {
        return customRecipe;
    }

    public ItemStack getPotion() {
        return potion;
    }

}
