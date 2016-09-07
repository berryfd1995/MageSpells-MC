package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/6/2016.
 */
public class Meteor implements SpellEffect{
    private String name = "meteor";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    private boolean forceend = false;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void Run(){
        spelllocation.add(vector);
    }
    public boolean shouldEnd(){
        return forceend;
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
        spelllocation = activeSpellObject.getCaster().getEyeLocation();
    }
    public SpellEffect spellEndingSeq(){
        ActiveSpellObject activeSpellObject = getActiveSpell();
        Location fromloc = spelllocation.clone().add(15,100,15);
        SpellEffect spellEffect = new Meteor2();
        spellEffect.setInitialLocation(fromloc);
        vector =  spelllocation.toVector().subtract(fromloc.toVector()).multiply(2);
        vector.normalize();
        activeSpellObject.clearSprayHit();
        activeSpellObject.clearBoltHit();
        activeSpellObject.setLocation(fromloc);
        activeSpellObject.setInitialLoc(fromloc);
        spellEffect.setInitialVector(vector);
        spellEffect.setActiveSpell(activeSpellObject);
        return spellEffect;
    }
}
