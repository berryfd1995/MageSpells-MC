package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/14/2016.
 */
public class SpellDrop implements SpellEffect {
    private String name = "arcspell";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    private int i = 0;
    private int points = 50;
    double increment = (2 * Math.PI) / 10;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void Run(){
        double angle = i * increment;
        double x = spelllocation.getX() + (4 * Math.cos(angle));
        double z = spelllocation.getZ() + (4 * Math.sin(angle));
        i++;
        if(i == points){
            i = 0;
        }
        MageSpellsAPI.playParticle(getActiveSpell(), new Location(spelllocation.getWorld(),x,spelllocation.getY(),z));
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
        vector = vector.setY(vector.getY() -.08);
        spelllocation.add(vector);
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
}
