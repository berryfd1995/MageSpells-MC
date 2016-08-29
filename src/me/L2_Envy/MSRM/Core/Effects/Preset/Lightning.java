package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 8/28/2016.
 */
public class Lightning implements SpellEffect{
    private String name = "Lightning";
    private Vector vector;
    private Location spelllocation;
    public void Run(Location location){
        spelllocation.add(vector);
    }
    public void onHit(LivingEntity livingEntity){
        livingEntity.getLocation().getWorld().strikeLightning(livingEntity.getLocation());
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
