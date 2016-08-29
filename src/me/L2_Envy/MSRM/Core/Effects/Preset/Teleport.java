package me.L2_Envy.MSRM.Core.Effects.Preset;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 8/26/2016.
 */
public class Teleport {
    private String name = "Normal";
    private Vector vector;
    private Location spelllocation;
    public void Run(Location location){
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
}
