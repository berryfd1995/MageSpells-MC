package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/5/2016.
 */
public class Vampire implements SpellEffect{
    private String name = "vampire";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void Run(){
        spelllocation.add(vector);
    }
    public void onHit(LivingEntity livingEntity){
        double currenthealth = activeSpellObject.getCaster().getHealth();
        double auradamage = activeSpellObject.getAuradamage() * 0.25;
        double boltdamage = activeSpellObject.getBoltdamage()* 0.25;
        double spraydamage = activeSpellObject.getSpraydamage()* 0.25;
        if(activeSpellObject.isAuraenabled()){
            if(currenthealth + auradamage >= 20){
                activeSpellObject.getCaster().setHealth(20);
            }else{
                activeSpellObject.getCaster().setHealth(currenthealth + auradamage);
            }
        }else if(activeSpellObject.isBoltenabled()){
            if(currenthealth + boltdamage >= 20){
                activeSpellObject.getCaster().setHealth(20);
            }else{
                activeSpellObject.getCaster().setHealth(currenthealth + boltdamage);
            }
        }else if(activeSpellObject.isSprayenabled()){
            if(currenthealth + spraydamage >= 20){
                activeSpellObject.getCaster().setHealth(20);
            }else{
                activeSpellObject.getCaster().setHealth(currenthealth + spraydamage);
            }
        }

    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void setInitialVector(Vector vector){
        this.vector = vector;
    }
    public Location plotSpellPoint(){
        return spelllocation;
    }
    public String getName(){
        return name;
    }
    public void initialSetup(){

    }
    public Location getSpellLocation(){
        return spelllocation;
    }
    public SpellEffect spellEndingSeq(){
        return null;
    }
    public boolean shouldEnd(){
        return false;
    }
}
