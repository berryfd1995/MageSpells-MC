package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/6/2016.
 */
public class Meteor2 implements SpellEffect{
    private String name = "meteor2";
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
        try {
            String var = activeSpellObject.getVariables().get(1);
            int i = Integer.parseInt(var);
            livingEntity.getLocation().getWorld().createExplosion(livingEntity.getLocation(),i);
        }catch(Exception e){
            livingEntity.getLocation().getWorld().createExplosion(livingEntity.getLocation(),4.0F);
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
    public boolean shouldEnd(){
        return false;
    }
    public void spellEndingSeq(){
        try {
            String var = activeSpellObject.getVariables().get(1);
            int i = Integer.parseInt(var);
            spelllocation.getWorld().createExplosion(spelllocation,i);
        }catch(Exception e){
            spelllocation.getWorld().createExplosion(spelllocation,8.0F);
        }

    }
    public void auraRun(){
    }

}
