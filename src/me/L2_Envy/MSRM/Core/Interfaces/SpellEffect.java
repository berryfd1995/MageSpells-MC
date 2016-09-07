package me.L2_Envy.MSRM.Core.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 7/26/2016.
 */
public interface SpellEffect {
    public void Run();
    public Location plotSpellPoint();
    public void onHit(LivingEntity livingEntity);
    public String getName();
    public void setInitialLocation(Location location);
    public void setInitialVector(Vector vector);
    public ActiveSpellObject getActiveSpell();
    public void setActiveSpell(ActiveSpellObject activeSpell);
    public void initialSetup();
    public boolean shouldEnd();
    public SpellEffect spellEndingSeq();
}
