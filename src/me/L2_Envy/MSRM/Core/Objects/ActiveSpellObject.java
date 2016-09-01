package me.L2_Envy.MSRM.Core.Objects;

import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Daniel on 7/23/2016.
 */
public class ActiveSpellObject extends SpellObject {
    private Vector vectorPath;
    private Player player;
    private ArrayList<UUID> sprayHit;
    private ArrayList<UUID> boltHit;
    private Location location;
    private Location initialLoc;
    private int timerTask;
    private int auratimertask;
    private int auratimeleft;
    public ActiveSpellObject(SpellObject spellObject, Location location, Player player){
        super(spellObject.getName(),spellObject.getSpellNode(),spellObject.getDisplayname(), spellObject.getLore(), spellObject.isBoltenabled(), spellObject.getBoltradius(), spellObject.getBoltdamage(), spellObject.isAuraenabled(), spellObject.getAuratime(),spellObject.getAuraradius(), spellObject.getAuradamage(),spellObject.isSprayenabled(),spellObject.getSprayradius(),spellObject.getSpraydamage(),spellObject.getArmorpiercing(),spellObject.getMoneycost(),spellObject.getManacost(),spellObject.getCooldown(),spellObject.getChargetime(),spellObject.getTraveldistance(),spellObject.getRequiredLevelToBind(),spellObject.getRequiredLevelToCast(),spellObject.getRequiredLevelToDrop(),spellObject.isAffectmobs(), spellObject.isAffectself(),spellObject.isAffectenemys(),spellObject.isAffectteammates(),spellObject.getSpellbook(),spellObject.getSpellEffect(),spellObject.getSound(),spellObject.getSoundvolume(),spellObject.getSoundpitch(),spellObject.getParticleObjects(),spellObject.getPotionEffects(),spellObject.isMobdropsenabled(),spellObject.getMobDrops(),spellObject.isItemcostenabled(),spellObject.getItemcost());
        this.player = player;
        this.location = location;
        this.initialLoc = location.clone();
        this.auratimeleft = spellObject.getAuratime();
        getSpellEffect().setInitialLocation(location.clone());
        this.boltHit = new ArrayList<>();
        this.sprayHit = new ArrayList<>();
    }
    public void setBoltHit(LivingEntity livingEntity){
        boltHit.add(livingEntity.getUniqueId());
    }
    public void setSprayHit(LivingEntity livingEntity) {
        sprayHit.add(livingEntity.getUniqueId());
    }

    public boolean didSprayHit(LivingEntity livingEntity) {
        return sprayHit.contains(livingEntity.getUniqueId());
        /*for(UUID uuid : sprayHit){
            if(uuid.equals(livingEntity.getUniqueId())){
                return true;
            }
        }
        return false;*/
    }
    public boolean didBoltHit(LivingEntity livingEntity){
        return boltHit.contains(livingEntity.getUniqueId());
    }

    public void setVectorPath(Vector vectorPath) {
        this.vectorPath = vectorPath;
    }

    public Vector getVectorPath() {
        return vectorPath;
    }

    public void clearSprayHit(){
        this.sprayHit = new ArrayList<>();
    }

    public Location getInitialLoc() {
        return initialLoc;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Player getCaster() {
        return player;
    }

    public void setTimerTask(int taskID) {
        this.timerTask = taskID;
    }

    public int getTimerTask() {
        return timerTask;
    }

    public int getAuratimertask(){
        return auratimertask;
    }
    public void setAuratimertask(int auratimertask){
        this.auratimertask = auratimertask;
    }
    public int getAuratimeleft(){
        return auratimeleft;
    }
    public void tickauratimer(){
        auratimeleft--;
    }


}
