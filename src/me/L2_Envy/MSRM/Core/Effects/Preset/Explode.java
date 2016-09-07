package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 8/28/2016.
 */
public class Explode implements SpellEffect{
    private String name = "Explode";
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
        livingEntity.getLocation().getWorld().createExplosion(livingEntity.getLocation(),4.0F);
    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void setInitialVector(Vector vector){
        this.vector =vector;
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
        return null;
    }
}
