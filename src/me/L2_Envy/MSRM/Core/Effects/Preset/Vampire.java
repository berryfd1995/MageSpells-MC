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
        int amount = 25;
        try{
            String var = activeSpellObject.getVariables().get(0);
            amount = Integer.parseInt(var);
        }catch (NumberFormatException e){

        }
        double percentage  = amount * .01;
        double currenthealth = activeSpellObject.getCaster().getHealth();
        double auradamage = activeSpellObject.getAuradamage() * percentage;
        double boltdamage = activeSpellObject.getBoltdamage()* percentage;
        double spraydamage = activeSpellObject.getSpraydamage()* percentage;
        if(activeSpellObject.isAuraenabled()){
            if(currenthealth + auradamage >= 20){
                activeSpellObject.getCaster().setHealth(20);
            }else{
                activeSpellObject.getCaster().setHealth(currenthealth + auradamage);
            }
        }
        if(activeSpellObject.isBoltenabled()){
            if(currenthealth + boltdamage >= 20){
                activeSpellObject.getCaster().setHealth(20);
            }else{
                activeSpellObject.getCaster().setHealth(currenthealth + boltdamage);
            }
        }
        if(activeSpellObject.isSprayenabled()){
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
    public void spellEndingSeq(){
    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun(){
    }
    public void auraEndingSequence(){

    }
}
