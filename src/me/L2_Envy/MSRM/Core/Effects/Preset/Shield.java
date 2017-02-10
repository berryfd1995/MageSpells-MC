package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.ParticleObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by Daniel on 2/9/2017.
 */
public class Shield implements SpellEffect {
    private String name = "shield";
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
        //plot points on sphere
        int radius=activeSpellObject.getAuraradius();
        if(!activeSpellObject.isBoltenabled() && !activeSpellObject.isSprayenabled()) {
            activeSpellObject.setLocation(activeSpellObject.getCaster().getLocation());
        }
        //remove other spells.
       //MageSpellsAPI.pluginManager.main.mageSpellsManager.activeSpellManager.removeNearbySpells(this,activeSpellObject.getAuraradius());
        Random random = new Random();
        for(int i = 0; i < 100*radius; i++) {
            double x = random.nextDouble() * 2.0D - 1.0D;
            double y = random.nextDouble() * 2.0D - 1.0D;
            double z = random.nextDouble() * 2.0D - 1.0D;
            Vector vec = new Vector(x, y, z).normalize().multiply(radius);
            for (ParticleObject particle : activeSpellObject.getParticleObjects()) {
                particle.getParticle().display(0, 0, 0, 0, 1, activeSpellObject.getLocation().clone().add(vec), 200);
            }
        }
    }
    public void auraEndingSequence(){

    }
}
