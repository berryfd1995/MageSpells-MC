package me.L2_Envy.MSRM.Core.Objects;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import org.bukkit.Sound;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Daniel on 7/23/2016.
 */
public class SpellObject{
    private String name;
    private String displayname;
    private String lore;
    private String spellnode;
    private boolean boltenabled;
    private int boltradius;
    private double boltdamage;
    private boolean auraenabled;
    private int auratime;
    private int auraradius;
    private double auradamage;
    private boolean sprayenabled;
    private int sprayradius;
    private double spraydamage;
    private int armorpiercing;
    private int moneycost;
    private int manacost;
    private int cooldown;
    private int chargetime;
    private int traveldistance;
    private int requiredleveltobind;
    private int requiredleveltocast;
    private int requiredleveltodrop;
    private boolean affectmobs;
    private boolean affectself;
    private boolean affectenemys;
    private boolean affectteammates;
    private ItemStack spellbook;
    private SpellEffect spellEffect;
    private Sound sound;
    private float soundvolume;
    private float soundpitch;
    private ArrayList<ParticleObject> particleObjects;
    private ArrayList<PotionEffect> potionEffects;
    private boolean mobdropsenabled;
    private HashMap<EntityType, Double> mobDrops;
    private boolean itemcostenabled;
    private HashMap<ItemStack, Integer> itemcost;

    public SpellObject(String name, String displayname, String spellnode, String lore, boolean boltenabled, int boltradius, double boltdamage, boolean auraenabled, int auratime, int auraradius, double auradamage,boolean sprayenabled, int sprayradius, double spraydamage, int armorpiercing, int moneycost, int manacost, int cooldown, int chargetime, int traveldistance, int requiredleveltobind,int requiredleveltocast, int requiredleveltodrop,boolean affectmobs, boolean affectself, boolean affectenemys, boolean affectteammates, ItemStack spellbook, SpellEffect spellEffect, Sound sound, float soundvolume, float soundpitch, ArrayList<ParticleObject> particleObjects, ArrayList<PotionEffect> potionEffects, boolean mobdropsenabled, HashMap<EntityType, Double> mobDrops, boolean itemcostenabled, HashMap<ItemStack, Integer> itemcost){
        this.name = name;
        this.displayname = displayname;
        this.spellnode = spellnode;
        this.lore = lore;
        this.boltenabled = boltenabled;
        this.boltradius = boltradius;
        this.boltdamage = boltdamage;
        this.auraenabled = auraenabled;
        this.auratime = auratime;
        this.auraradius = auraradius;
        this.auradamage = auradamage;
        this.sprayenabled = sprayenabled;
        this.sprayradius = sprayradius;
        this.spraydamage = spraydamage;
        this.armorpiercing = armorpiercing;
        this.moneycost = moneycost;
        this.manacost = manacost;
        this.cooldown = cooldown;
        this.chargetime = chargetime;
        this.traveldistance = traveldistance;
        this.requiredleveltobind = requiredleveltobind;
        this.requiredleveltocast = requiredleveltocast;
        this.requiredleveltodrop = requiredleveltodrop;
        this.affectmobs = affectmobs;
        this.affectself = affectself;
        this.affectenemys = affectenemys;
        this.affectteammates = affectteammates;
        this.spellbook = spellbook;
        this.spellEffect = spellEffect;
        this.sound = sound;
        this.soundvolume = soundvolume;
        this.soundpitch = soundpitch;
        this.particleObjects = particleObjects;
        this.potionEffects = potionEffects;
        this.mobdropsenabled = mobdropsenabled;
        this.mobDrops = mobDrops;
        this.itemcostenabled = itemcostenabled;
        this.itemcost = itemcost;
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

    public boolean isBoltenabled() {
        return boltenabled;
    }

    public int getBoltradius() {
        return boltradius;
    }

    public double getBoltdamage() { return  boltdamage; }

    public boolean isAuraenabled() {
        return auraenabled;
    }

    public int getAuratime() {
        return auratime;
    }

    public int getAuraradius() {
        return auraradius;
    }

    public double getAuradamage() { return auradamage; }

    public boolean isSprayenabled() {
        return sprayenabled;
    }

    public int getSprayradius() {
        return sprayradius;
    }

    public double getSpraydamage() {
        return spraydamage;
    }

    public int getArmorpiercing() {
        return armorpiercing;
    }

    public int getMoneycost() {
        return moneycost;
    }

    public int getManacost() {
        return manacost;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getChargetime() {
        return chargetime;
    }

    public int getTraveldistance() {
        return traveldistance;
    }

    public int getRequiredLevelToBind() {
        return requiredleveltobind;
    }

    public int getRequiredLevelToCast() {
        return requiredleveltocast;
    }

    public int getRequiredLevelToDrop() {
        return requiredleveltodrop;
    }

    public boolean isAffectmobs() {
        return affectmobs;
    }

    public boolean isAffectself() {
        return affectself;
    }

    public boolean isAffectenemys() {
        return affectenemys;
    }

    public boolean isAffectteammates() {
        return affectteammates;
    }

    public ItemStack getSpellbook() {
        return spellbook;
    }

    public SpellEffect getSpellEffect() {
        return spellEffect;
    }

    public Sound getSound() {
        return sound;
    }

    public float getSoundvolume() {
        return soundvolume;
    }

    public float getSoundpitch() {
        return soundpitch;
    }

    public ArrayList<ParticleObject> getParticleObjects() {
        return particleObjects;
    }

    public ArrayList<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public boolean isMobdropsenabled() {
        return mobdropsenabled;
    }

    public HashMap<EntityType, Double> getMobDrops() {
        return mobDrops;
    }

    public boolean isItemcostenabled() {
        return itemcostenabled;
    }

    public HashMap<ItemStack, Integer> getItemcost() {
        return itemcost;
    }

    public String getSpellNode(){
        return spellnode.toLowerCase();
    }

    public static class CompId implements Comparator<SpellObject> {
        @Override
        public int compare(SpellObject arg0, SpellObject arg1) {
            return arg0.getName().compareTo(arg1.getName());
        }
    }
    /**
    private SpellEffect spellEffect;
    private boolean affectTeammates;
    private boolean affectEnemy;
    private boolean affectSelf;
    private boolean affectMobs;
    private boolean mobDropsEnabled;
    private ArrayList<ParticleObject> particleObjects;
    private HashMap<EntityType, Double> mobdrops = new HashMap<>();
    private HashMap<ItemStack, Integer> itemCost = new HashMap<>();
    private double maxDistance;
    private String name;
    private String bookTitle;
    private String bookLore;
    private double damage;
    private double cost;
    private int cooldown;
    private int minimumlevel;
    private ArrayList<PotionEffect> potionList;
    private int radius;
    private int timer;
    private int moneyCost;
    private Sound sound;
    private float volume;
    private float pitch;
    //loaded in from config
    //MUST HAVE ALL THESE FOR ANY SPELL
    public SpellObject(String name){
        this.name = name;
    }
    public SpellObject(String name, SpellEffect spellEffect, int minimumlevel,
                       ArrayList<ParticleObject> particleObjects,
                       ArrayList<PotionEffect> potionList, HashMap<EntityType, Double> mobdrops, HashMap<ItemStack, Integer> itemcost, boolean mobDropsEnabled,
                       double damage, int radius,
                       double maxDistance, int timer, double cost, int cooldown, boolean affectTeammates, boolean affectEnemy, boolean
                               affectMobs, int
                               moneyCost, boolean affectSelf, Sound sound, float pitch, float volume, String bookTitle,
                       String bookLore) {
        this.particleObjects = particleObjects;
        this.name = name;
        this.potionList = potionList;
        this.damage = damage;
        this.radius = radius;
        this.maxDistance = maxDistance;
        this.timer = timer;
        this.cost = cost;
        this.affectEnemy = affectEnemy;
        this.affectTeammates = affectTeammates;
        this.moneyCost = moneyCost;
        this.affectSelf = affectSelf;
        this.sound = sound;
        this.pitch = pitch;
        this.volume = volume;
        this.affectMobs = affectMobs;
        this.mobdrops = mobdrops;
        this.mobDropsEnabled = mobDropsEnabled;
        this.bookTitle = bookTitle;
        this.bookLore = bookLore;
        this.cooldown = cooldown;
        this.itemCost = itemcost;
        this.minimumlevel = minimumlevel;
        this.spellEffect = spellEffect;
    }


    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookLore() {
        return bookLore;
    }

    public boolean getMobDropsEnabled() {
        return mobDropsEnabled;
    }

    public HashMap<EntityType, Double> getMobs() {
        return mobdrops;
    }

    public HashMap<ItemStack, Integer> getItemCost() {
        return itemCost;
    }

    public boolean getAffectMobs() {
        return affectMobs;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public Sound getSound() {
        return sound;
    }

    public boolean getAffectSelf() {
        return affectSelf;
    }

    public int getMoneyCost() {
        return moneyCost;
    }

    public boolean getAffectTeammates() {
        return affectTeammates;
    }

    public boolean getAffectEnemy() {
        return affectEnemy;
    }

    public double getCost() {
        return cost;
    }

    public int getCooldown(){
        return cooldown;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getMinimumlevel() {
        return minimumlevel;
    }

    public int getRadius() {
        return radius;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public double getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ParticleObject> getParticles() {
        return particleObjects;
    }

    public ArrayList<PotionEffect> getPotion() {
        return potionList;
    }

    public SpellEffect getSpellEffect(){
        return spellEffect;
    }
     **/
}
