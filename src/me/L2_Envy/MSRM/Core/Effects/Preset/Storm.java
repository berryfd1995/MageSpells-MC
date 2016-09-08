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
public class Storm implements SpellEffect{
    private String name = "storm";
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
        livingEntity.getLocation().getWorld().strikeLightning(livingEntity.getLocation());
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
    public void spellEndingSeq(){
    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun(){
        int radius = 0;
        if(activeSpellObject.isAuraenabled()) {
            radius = activeSpellObject.getAuraradius();
        }else if(activeSpellObject.isSprayenabled()) {
            radius = activeSpellObject.getSprayradius();
        }else if(activeSpellObject.isBoltenabled()){
            radius = activeSpellObject.getBoltradius();
        }else if (radius == 0){
            radius = 5;
        }
        spelllocation.getWorld().strikeLightning(getRandomLocation(radius));
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
