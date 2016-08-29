package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


/**
 * Created by Daniel on 7/27/2016.
 */
public class HomingSpellEffect implements SpellEffect{
    private String name = "Homing";
    private Vector vector;
    private Location spelllocation;
    public void Run(Location location){
        Location location1 = getClosestEntity();
        if(location1 != null){
            vector =  location1.toVector().subtract(spelllocation.toVector()).multiply(2);
            vector.normalize();
            spelllocation.add(vector);
        }else {
            spelllocation.add(vector);
        }
        //spelllocation.add(vector);
    }
    public void onHit(LivingEntity livingEntity){

    }
    public Location plotSpellPoint(){
        return spelllocation;
    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void setInitialVector(Vector vector){
        this.vector = vector;
    }
    public String getName(){
        return name;
    }

    public Location getClosestEntity(){
        Location location =  null;
        for(Entity entity : spelllocation.getWorld().getNearbyEntities(spelllocation,10,10,10)){
            if(entity instanceof LivingEntity){
                if(!(entity instanceof Player)) {
                    if (location == null) {
                        location = entity.getLocation();
                    } else if (spelllocation.distance(entity.getLocation()) < spelllocation.distance(location)) {
                        location = entity.getLocation();
                    }
                }
            }
        }
        return location;
    }
}
