package me.L2_Envy.MSRM.Core.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/26/2016.
 */
public interface SpellEffect {
    public String getName();
    public ActiveSpellObject getActiveSpell();
    public void setActiveSpell(ActiveSpellObject activeSpell);
    public void initialSetup();
    public void setInitialLocation(Location location);
    public void setInitialVector(Vector vector);
    public Location plotSpellPoint();
    public void Run();
    public void auraRun();
    public void auraEndingSequence();
    public void onHit(LivingEntity livingEntity);
    public boolean shouldEnd();
    public void spellEndingSeq();
}
