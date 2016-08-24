package me.L2_Envy.MSRM.Core.Interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 7/26/2016.
 */
public interface SpellEffect {
    /**
     * Called when spell first cast.
     * @param location
     */
    public void Run(Location location);

    public Location plotSpellPoint();
    public void onHit(LivingEntity livingEntity);
    public String getName();
    public void setInitialLocation(Location location);
    public void setInitialVector(Vector vector);
}
