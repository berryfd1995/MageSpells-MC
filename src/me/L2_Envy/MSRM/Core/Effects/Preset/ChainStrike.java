package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/6/2016.
 */
public class ChainStrike implements SpellEffect{
    private String name = "chain";
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
        Location location1 = getClosestEntity();
        if(location1 != null){
            vector =  location1.toVector().subtract(spelllocation.toVector()).multiply(2);
            vector.normalize();
            spelllocation.add(vector);
        }else {
            spelllocation.add(vector);
        }
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
        Location location = getClosestEntity();
        if(location != null && (spelllocation.getBlock().getType() == Material.AIR || spelllocation.getBlock().getType() == Material.LONG_GRASS)) {
            ActiveSpellObject activeSpellObject = getActiveSpell();
            SpellEffect spellEffect = new ChainStrike();
            spelllocation = spelllocation.add(0,1,0);
            location = location.add(0,1,0);
            spellEffect.setInitialLocation(spelllocation);
            vector = location.toVector().subtract(spelllocation.toVector()).multiply(2);
            vector.normalize();
            activeSpellObject.setLocation(spelllocation);
            activeSpellObject.setInitialLoc(spelllocation);
            spelllocation.add(vector);
            spellEffect.setInitialVector(vector);
            spellEffect.setActiveSpell(activeSpellObject);
            return spellEffect;
        }else{
            return null;
        }
    }
    public boolean shouldEnd(){
        return false;
    }
    public Location getClosestEntity(){
        Location location =  null;
        for(Entity entity : spelllocation.getWorld().getNearbyEntities(spelllocation,activeSpellObject.getTraveldistance(),activeSpellObject.getTraveldistance(),activeSpellObject.getTraveldistance())){
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity =(LivingEntity) entity;
                if(!activeSpellObject.didSprayHit(livingEntity) && !activeSpellObject.didBoltHit(livingEntity)) {
                    if (entity instanceof Player) {
                        Player player = (Player) livingEntity;
                        if (!activeSpellObject.getCaster().equals(player)) {
                            if (location == null) {
                                location = entity.getLocation();
                            } else if (spelllocation.distance(entity.getLocation()) < spelllocation.distance(location)) {
                                location = entity.getLocation();
                            }
                        }
                    } else {
                        if (location == null) {
                            location = entity.getLocation();
                        } else if (spelllocation.distance(entity.getLocation()) < spelllocation.distance(location)) {
                            location = entity.getLocation();
                        }
                    }
                }
            }
        }
        return location;
    }
}
