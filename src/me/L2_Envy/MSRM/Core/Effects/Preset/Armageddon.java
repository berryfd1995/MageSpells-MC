package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by Daniel on 9/7/2016.
 */
public class Armageddon  implements SpellEffect{
    private String name = "armageddon";
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
    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void auraEndingSequence(){

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
    public void spellEndingSeq(){
    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun(){
        ActiveSpellObject activeSpellObject = MageSpellsAPI.cloneActiveSpellObject(getActiveSpell());
        int radius = 0;
        if(activeSpellObject.isSprayenabled()) {
            radius = activeSpellObject.getSprayradius();
        }else if(activeSpellObject.isAuraenabled()){
            radius = activeSpellObject.getAuraradius();
        }else if(activeSpellObject.isBoltenabled()){
            radius = activeSpellObject.getBoltradius();
        }else if (radius == 0){
            radius = 5;
        }
        Location toloc = getRandomLocation(radius);
        Location fromloc = toloc.clone().add(getRandomValue(15), 100, getRandomValue(15));
        SpellEffect spellEffect = new Meteor2();
        try {
            String var = activeSpellObject.getVariables().get(0);
            int i = Integer.parseInt(var);
            activeSpellObject.setBoltdamage(i);
        }catch(Exception e){
            activeSpellObject.setBoltdamage(5);
        }
        activeSpellObject.setBoltradius(radius);
        activeSpellObject.setBoltenabled(true);
        activeSpellObject.setTraveldistance(50);
        activeSpellObject.setSprayenabled(false);
        activeSpellObject.setAuraenabled(false);
        spellEffect.setInitialLocation(fromloc);
        Vector vector1 = MageSpellsAPI.createNewVector(activeSpellObject.getSpellObject(),toloc, fromloc);
        activeSpellObject.clearSprayHit();
        activeSpellObject.clearBoltHit();
        activeSpellObject.setLocation(fromloc);
        activeSpellObject.setInitialLoc(fromloc);
        spellEffect.setInitialVector(vector1);
        spellEffect.setActiveSpell(activeSpellObject);
        MageSpellsAPI.shootSpell(spellEffect);
    }
    public Location getRandomLocation(int range){
        Location location = spelllocation.clone();
        int x = getRandomValue(range);
        int z = getRandomValue(range);
        return location.add(x,0,z);
    }
    public int getRandomValue(int range){
        Random random = new Random();
        int randomNum = random.nextInt(range) + 1;
        int i=1;
        switch(random.nextInt(2)+1){
            case 0:
                i = 1;
                break;
            case 1:
                i = -1;
                break;
            case 2:
                i = 1;
                break;
            default:
                break;
        }
        return randomNum*i;
    }
}
