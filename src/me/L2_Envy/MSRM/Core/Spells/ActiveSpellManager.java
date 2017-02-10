package me.L2_Envy.MSRM.Core.Spells;

import de.slikey.effectlib.util.ParticleEffect;
import me.L2_Envy.MSRM.Core.Effects.Preset.Shield;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/28/2016.
 */
public class ActiveSpellManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<SpellEffect> activeSpellObjects;
    public ActiveSpellManager(){
        activeSpellObjects = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public boolean hasActiveSpellObject(ActiveSpellObject activeSpellObject){
        return activeSpellObjects.contains(activeSpellObject);
    }
    public void addActiveSpellObject(SpellEffect spellEffect){
        if(!activeSpellObjects.contains(spellEffect)){
            activeSpellObjects.add(spellEffect);
        }
    }
    public void removeSpellEffect(SpellEffect spellEffect){
        if(activeSpellObjects.contains(spellEffect)){
            activeSpellObjects.remove(spellEffect);
        }
    }
    public void shootSpell(SpellEffect spellEffect){
        addActiveSpellObject(spellEffect);
        spellEffect.getActiveSpell().setTimerTask(Bukkit.getScheduler().scheduleSyncRepeatingTask(mageSpellsManager.main, () ->{//has it hit a block?
                        if ((spellEffect.getActiveSpell().getLocation().getBlock().getType() == Material.AIR
                                ||spellEffect.getActiveSpell().getLocation().getBlock().getType() == Material.LONG_GRASS
                                ||spellEffect.getActiveSpell().getLocation().getBlock().getType() == Material.DOUBLE_PLANT) && !spellEffect.shouldEnd()) {
                            //Is it past max distance?
                            if (spellEffect.getActiveSpell().getInitialLoc().distance(spellEffect.getActiveSpell().getLocation()) < spellEffect.getActiveSpell().getTraveldistance()) {
                                //Spell Effect
                                spellEffect.Run();
                                //Update Spell Location
                                spellEffect.getActiveSpell().setLocation(spellEffect.plotSpellPoint());
                                //Play particle at spell location
                                mageSpellsManager.particleEffectManager.playParticle(spellEffect.getActiveSpell());
                                //Check if nearby spells
                                if (isNearbySpells(spellEffect, 2)) {
                                    removeSpell(spellEffect);
                                }
                                //sound effect
                                playSound(spellEffect.getActiveSpell());
                                //Do spell effects / damage
                                mageSpellsManager.spellContactManager.checkSpellContact(spellEffect);
                            } else {
                                mageSpellsManager.spellContactManager.initiateSpellEndSeq(spellEffect);
                            }
                        } else {
                            mageSpellsManager.spellContactManager.initiateSpellEndSeq(spellEffect);
                        }
        },0L,1L));
    }
    public boolean isNearbySpells(SpellEffect spell, int radius) {
        for (SpellEffect otherSpell : ((ArrayList<SpellEffect>) activeSpellObjects.clone())) {
            if (otherSpell.getActiveSpell().getCaster() != spell.getActiveSpell().getCaster()) {
            if(otherSpell.getActiveSpell().getCaster() != spell.getActiveSpell().getCaster()) {
                if (otherSpell.getActiveSpell().getLocation().distance(spell.getActiveSpell().getLocation()) < radius) {
                    otherSpell.getActiveSpell().getLocation().getWorld()
                            .createExplosion(spell.getActiveSpell().getLocation(), 2.0F);
                    removeSpell(spell);
                    removeSpell(otherSpell);
                    return true;
                }
            }
           }
        }
        for(SpellEffect otherSpell : mageSpellsManager.spellContactManager.getActiveAuraSpells()){
            if(otherSpell instanceof Shield) {
                if (otherSpell.getActiveSpell().getCaster() != spell.getActiveSpell().getCaster()) {
                    if (otherSpell.getActiveSpell().getLocation().distance(spell.getActiveSpell().getLocation()) - otherSpell.getActiveSpell().getAuraradius() > -1 &&
                            otherSpell.getActiveSpell().getLocation().distance(spell.getActiveSpell().getLocation()) - otherSpell.getActiveSpell().getAuraradius() < 1) {
                        ParticleEffect particleEffect = ParticleEffect.EXPLOSION_HUGE;
                        particleEffect.display(0, 0, 0, 0, 3, spell.getActiveSpell().getLocation(), 200);
                        spell.getActiveSpell().getLocation().getWorld().playSound(spell.getActiveSpell().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, .5F, 0F);
                        removeSpell(spell);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeNearbySpells(SpellEffect spell, int radius) {
        for (SpellEffect otherSpell : ((ArrayList<SpellEffect>) activeSpellObjects.clone())) {
            //if (otherSpell.getActiveSpell().getCaster() != spell.getActiveSpell().getCaster()) {
                if (otherSpell.getActiveSpell().getLocation().distance(spell.getActiveSpell().getLocation())-radius< 1 &&
                    otherSpell.getActiveSpell().getLocation().distance(spell.getActiveSpell().getLocation())-radius >-1){
                    Location loc = otherSpell.getActiveSpell().getLocation();
                    ParticleEffect particleEffect = ParticleEffect.EXPLOSION_LARGE;
                    particleEffect.display(0,0,0,0,1,loc,200);
                    removeSpell(otherSpell);
                    return true;
                }
           //}
        }
        return false;
    }
    public void removeSpell(SpellEffect spellEffect) {
        Bukkit.getScheduler().cancelTask(spellEffect.getActiveSpell().getTimerTask());
        spellEffect.spellEndingSeq();
        removeSpellEffect(spellEffect);
    }
    public void playSound(ActiveSpellObject activeSpellObject){
        activeSpellObject.getLocation().getWorld().playSound(activeSpellObject.getLocation(), activeSpellObject.getSound(), activeSpellObject.getSoundvolume(), activeSpellObject.getSoundpitch());
    }
}
