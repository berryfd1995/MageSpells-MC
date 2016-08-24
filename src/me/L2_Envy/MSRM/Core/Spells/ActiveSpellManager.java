package me.L2_Envy.MSRM.Core.Spells;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/28/2016.
 */
public class ActiveSpellManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<ActiveSpellObject> activeSpellObjects;
    public ActiveSpellManager(){
        activeSpellObjects = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public boolean hasActiveSpellObject(ActiveSpellObject activeSpellObject){
        return activeSpellObjects.contains(activeSpellObject);
    }
    public void addActiveSpellObject(ActiveSpellObject activeSpellObject){
        if(!activeSpellObjects.contains(activeSpellObject)){
            activeSpellObjects.add(activeSpellObject);
        }
    }
    public void removeActiveSpellObject(ActiveSpellObject activeSpellObject){
        if(!activeSpellObjects.contains(activeSpellObject)){
            activeSpellObjects.remove(activeSpellObject);
        }
    }
    public void shootSpell(ActiveSpellObject activeSpellObject){
        activeSpellObject.setTimerTask(Bukkit.getScheduler().scheduleSyncRepeatingTask(mageSpellsManager.main,() -> {
            //has it hit a block?
            if(activeSpellObject.getLocation().getBlock().getType() == Material.AIR){
                //Is it past max distance?
                if(activeSpellObject.getInitialLoc().distance(activeSpellObject.getLocation()) < activeSpellObject.getTraveldistance()){
                    //Spell Effect
                    activeSpellObject.getSpellEffect().Run(activeSpellObject.getLocation());
                    //Update Spell Location
                    activeSpellObject.setLocation(activeSpellObject.getSpellEffect().plotSpellPoint());
                    //Play particle at spell location
                    mageSpellsManager.particleEffectManager.playParticle(activeSpellObject);
                    //Check if nearby spells
                    if(isNearbySpells(activeSpellObject)){
                        removeSpell(activeSpellObject);
                    }
                    //sound effect
                    playSound(activeSpellObject);
                    //Do spell effects / damage
                    mageSpellsManager.spellContactManager.checkSpellContact(activeSpellObject);
                }else{
                    mageSpellsManager.spellContactManager.initiateSpellEndSeq(activeSpellObject);
                }
            }else{
                mageSpellsManager.spellContactManager.initiateSpellEndSeq(activeSpellObject);
            }
        },0L,1L));
    }
    public boolean isNearbySpells(ActiveSpellObject spell) {
        for (ActiveSpellObject otherSpell : ((ArrayList<ActiveSpellObject>) activeSpellObjects.clone())) {
            double radius = 2;
            if (otherSpell.getCaster() != spell.getCaster()) {
                if (otherSpell.getLocation().distance(spell.getLocation()) < radius) {
                    otherSpell.getLocation().getWorld()
                            .createExplosion(spell.getLocation(), 2.0F);
                    removeSpell(spell);
                    removeSpell(otherSpell);
                    return true;
                }
            }
        }
        return false;
    }
    public void removeSpell(ActiveSpellObject spell) {
        Bukkit.getScheduler().cancelTask(spell.getTimerTask());
        if (activeSpellObjects.contains(spell))
            activeSpellObjects.remove(spell);
    }
    public void playSound(ActiveSpellObject activeSpellObject){
        activeSpellObject.getLocation().getWorld().playSound(activeSpellObject.getLocation(), activeSpellObject.getSound(), activeSpellObject.getSoundvolume(), activeSpellObject.getSoundpitch());
    }
}
