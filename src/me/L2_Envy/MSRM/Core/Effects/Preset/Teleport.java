package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 8/26/2016.
 */
public class Teleport implements SpellEffect{
    private String name = "teleport";
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
        Location loc = spelllocation.clone();
        Block block = loc.getBlock();
        if(block == null) {
            activeSpellObject.getCaster().teleport(spelllocation);
        }else{
            vector.setX(vector.getX()*-1);
            vector.setY(vector.getY()*-1);
            vector.setZ(vector.getZ()*-1);
            spelllocation.add(vector);
            activeSpellObject.getCaster().teleport(spelllocation);
        }
    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun(){
    }
}
