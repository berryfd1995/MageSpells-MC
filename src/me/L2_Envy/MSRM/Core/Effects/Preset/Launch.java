package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by berry on 2/6/2017.
 */
public class Launch implements SpellEffect {
    private String name = "launch";
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
        //Vector unitVector = livingEntity.getLocation().toVector().subtract(activeSpellObject.getLocation().toVector()).normalize();
        Vector unitVector = new Location(livingEntity.getWorld(),0,1,0).toVector().normalize();
        int i;
        try{
            String var = activeSpellObject.getVariables().get(0);
            i = Integer.parseInt(var);
            livingEntity.setVelocity(unitVector.multiply(i));
        }catch (Exception e){
            livingEntity.setVelocity(unitVector.multiply(2));
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
