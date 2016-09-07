package me.L2_Envy.MSRM.Core.Effects.Preset;

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
    public SpellEffect Run(){
        spelllocation.add(vector);
        return null;
    }
    public void onHit(LivingEntity livingEntity){

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
    public SpellEffect spellEndingSeq(){
        return null;
    }
    public boolean shouldEnd(){
        return false;
    }
    public SpellEffect auraRun(){
        ActiveSpellObject activeSpellObject = getActiveSpell();
        Location fromloc = spelllocation.clone().add(15, 100, 15);
        SpellEffect spellEffect = new Meteor2();
        spellEffect.setInitialLocation(fromloc);
        vector = getRandomLocation(activeSpellObject.getAuraradius()).toVector().subtract(fromloc.toVector()).multiply(2);
        vector.normalize();
        activeSpellObject.clearSprayHit();
        activeSpellObject.clearBoltHit();
        activeSpellObject.setLocation(fromloc);
        activeSpellObject.setInitialLoc(fromloc);
        spellEffect.setInitialVector(vector);
        spellEffect.setActiveSpell(activeSpellObject);
        return spellEffect;
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
        switch(random.nextInt(1)+1){
            case 0:
                i = 1;
                break;
            case 1:
                i = -1;
                break;
            default:
                break;
        }
        return randomNum*i;
    }
}
