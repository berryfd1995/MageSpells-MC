package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
        Vector vec = vector.clone();
        Block block = loc.getBlock();
        vec.setX(vec.getX()*-1);
        vec.setY(vec.getY()*-1);
        vec.setZ(vec.getZ()*-1);
        while(block.getType() != Material.AIR || loc.getWorld().getBlockAt(loc.clone().add(0,1,0)).getType() != Material.AIR){
            loc.add(vec);
            block = loc.getBlock();
        }
        activeSpellObject.getCaster().teleport(loc.getBlock().getLocation().add(.5,0,.5));
    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun(){
    }
    public void auraEndingSequence(){

    }
}
