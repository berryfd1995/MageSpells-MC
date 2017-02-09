package me.L2_Envy.MSRM.Core.Spells;

import it.unimi.dsi.fastutil.Hash;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Daniel on 8/11/2016.
 */
public class SpellContactManager {
    private HashMap<ActiveSpellObject, Integer> activeAuraSpell;
    public MageSpellsManager mageSpellsManager;
    public SpellContactManager(){
        activeAuraSpell= new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }

    public void checkSpellContact(SpellEffect spellEffect){
        if(mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(spellEffect.getActiveSpell().getLocation())) {
            if (spellEffect.getActiveSpell().isSprayenabled()) {
                for (LivingEntity livingEntity : getNearbyEntites(spellEffect.getActiveSpell(), spellEffect.getActiveSpell().getSprayradius())) {
                    //particle
                    if (mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
                        spellEffect.onHit(livingEntity);
                        mageSpellsManager.particleEffectManager.playParticle(spellEffect.getActiveSpell(), livingEntity.getLocation().clone());
                        //potion affect
                        mageSpellsManager.potionEffectManager.playPotionEffect(spellEffect.getActiveSpell(), livingEntity);
                        //damage
                        mageSpellsManager.damageEffectManager.doDamage(spellEffect.getActiveSpell(), livingEntity, false, true, false);
                    }
                }
            }
            if (spellEffect.getActiveSpell().isBoltenabled()) {
                if (boltHit(spellEffect)) {
                    for (LivingEntity livingEntity : getNearbyEntitesBolt(spellEffect.getActiveSpell(), spellEffect.getActiveSpell().getBoltradius())) {
                        if(mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
                            spellEffect.onHit(livingEntity);
                            mageSpellsManager.particleEffectManager.playParticle(spellEffect.getActiveSpell(), livingEntity.getLocation().clone());
                            //potion affect
                            mageSpellsManager.potionEffectManager.playPotionEffect(spellEffect.getActiveSpell(), livingEntity);
                            //damage
                            mageSpellsManager.damageEffectManager.doDamage(spellEffect.getActiveSpell(), livingEntity, true, false, false);
                        }
                    }
                    initiateSpellEndSeq(spellEffect);
                }
            }
        }
    }

    public void initiateSpellEndSeq(SpellEffect spellEffect){
        if(spellEffect.getActiveSpell().isAuraenabled()){
            mageSpellsManager.activeSpellManager.removeSpell(spellEffect);
            activeAura(spellEffect);
        }else{
            mageSpellsManager.activeSpellManager.removeSpell(spellEffect);
        }
    }
    public void activeAura(SpellEffect spellEffect){
        spellEffect.getActiveSpell().clearSprayHit();
        spellEffect.getActiveSpell().setAuratimertask(Bukkit.getScheduler().scheduleSyncRepeatingTask(mageSpellsManager.main, () -> {
           if(spellEffect.getActiveSpell().getAuratimeleft() >0) {
               spellEffect.auraRun();
               mageSpellsManager.particleEffectManager.playParticle(spellEffect.getActiveSpell(), spellEffect.getActiveSpell().getLocation().clone());
               for (LivingEntity livingEntity : getNearbyEntites(spellEffect.getActiveSpell(), spellEffect.getActiveSpell().getAuraradius())) {
                   if (mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
                       spellEffect.onHit(livingEntity);
                       mageSpellsManager.particleEffectManager.playParticle(spellEffect.getActiveSpell(), livingEntity.getLocation().clone());
                       //potion
                       mageSpellsManager.potionEffectManager.playPotionEffect(spellEffect.getActiveSpell(), livingEntity);
                       //damage
                       mageSpellsManager.damageEffectManager.doDamage(spellEffect.getActiveSpell(), livingEntity, false, false, true);
                       //sound
                       livingEntity.getLocation().getWorld().playSound(livingEntity.getLocation(), spellEffect.getActiveSpell().getSound(), spellEffect.getActiveSpell().getSoundvolume(),spellEffect.getActiveSpell().getSoundpitch());
                   }
               }
               spellEffect.getActiveSpell().clearSprayHit();
               spellEffect.getActiveSpell().tickauratimer();
           }else{
               spellEffect.auraEndingSequence();
               removeAuraTask(spellEffect.getActiveSpell());
           }
       },0L,20L));
    }
    public void removeAuraTask(ActiveSpellObject activeSpellObject){
        Bukkit.getScheduler().cancelTask(activeSpellObject.getAuratimertask());
    }
    //Different from bolt radius. Bolt hit must be within 1.5 blocks to be activated. and end spell
    public boolean boltHit(SpellEffect spellEffect){
        double radius = 1.5;
        for (Entity entity : spellEffect.getActiveSpell().getLocation().getWorld().getEntities()) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity1 = (LivingEntity) entity;
                    if (spellEffect.getActiveSpell().getLocation().distance(entity.getLocation().clone().add(0, 0.5, 0)) < radius) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            boolean sameteam = mageSpellsManager.teamManager.onSameTeam(spellEffect.getActiveSpell().getCaster(), player);
                            if (spellEffect.getActiveSpell().getCaster().getName().equalsIgnoreCase(player.getName())) {
                                if (spellEffect.getActiveSpell().isAffectself()) {
                                    spellEffect.getActiveSpell().setBoltHit(player);
                                    if(spellEffect.getActiveSpell().didBoltHit(player)) {
                                        attackEntityBolt(spellEffect, player);
                                    }
                                    return true;
                                }
                            } else {
                                if (sameteam) {
                                    if (spellEffect.getActiveSpell().isAffectteammates()) {
                                        spellEffect.getActiveSpell().setBoltHit(player);
                                        if(spellEffect.getActiveSpell().didBoltHit(player)) {
                                            attackEntityBolt(spellEffect, player);
                                        }
                                        return true;
                                    }
                                } else {
                                    if (spellEffect.getActiveSpell().isAffectenemys()) {
                                        spellEffect.getActiveSpell().setBoltHit(player);
                                        if(spellEffect.getActiveSpell().didBoltHit(player)) {
                                            attackEntityBolt(spellEffect, player);
                                        }
                                        return true;
                                    }
                                }
                            }
                        } else {
                            if (spellEffect.getActiveSpell().isAffectmobs()) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                spellEffect.getActiveSpell().setBoltHit(livingEntity);
                                if(spellEffect.getActiveSpell().didBoltHit(livingEntity)) {
                                    attackEntityBolt(spellEffect, livingEntity);
                                }
                                return true;
                            }
                        }
                    }
                }
        }
        return false;
    }
    public void attackEntityBolt(SpellEffect spellEffect, LivingEntity livingEntity){
        if(mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
            spellEffect.onHit(livingEntity);
            mageSpellsManager.potionEffectManager.playPotionEffect(spellEffect.getActiveSpell(), livingEntity);
            //damage
            mageSpellsManager.damageEffectManager.doDamage(spellEffect.getActiveSpell(), livingEntity, true, false, false);
        }
    }
    //How will it know to end.
    //Do i check distance as well? Which distance.
    public ArrayList<LivingEntity> getNearbyEntites(ActiveSpellObject activeSpellObject, double radius){
        ArrayList<LivingEntity> livingEntities = new ArrayList<>();
        if(radius > 0) {
            for (Entity entity : activeSpellObject.getLocation().getWorld().getNearbyEntities(activeSpellObject.getLocation(), radius, radius, radius)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity1 = (LivingEntity) entity;
                    if(!activeSpellObject.didSprayHit(livingEntity1)) {
                        if (activeSpellObject.getLocation().distance(entity.getLocation().add(0, 0.5, 0)) < radius) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                boolean sameteam = mageSpellsManager.teamManager.onSameTeam(activeSpellObject.getCaster(), player);
                                if (activeSpellObject.getCaster().getName().equalsIgnoreCase(player.getName())) {
                                    if (activeSpellObject.isAffectself()) {
                                        livingEntities.add(player);
                                        activeSpellObject.setSprayHit(player);
                                    }
                                } else {
                                    if (sameteam) {
                                        if (activeSpellObject.isAffectteammates()) {
                                            if (!activeSpellObject.didSprayHit(player)) {
                                                livingEntities.add(player);
                                                activeSpellObject.setSprayHit(player);
                                            }
                                        }
                                    } else {
                                        if (activeSpellObject.isAffectenemys()) {
                                            if (!activeSpellObject.didSprayHit(player)) {
                                                livingEntities.add(player);
                                                activeSpellObject.setSprayHit(player);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (activeSpellObject.isAffectmobs()) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (!activeSpellObject.didSprayHit(livingEntity)) {
                                        livingEntities.add(livingEntity);
                                        activeSpellObject.setSprayHit(livingEntity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return livingEntities;
    }

    public ArrayList<LivingEntity> getNearbyEntitesBolt(ActiveSpellObject activeSpellObject, double radius){
        ArrayList<LivingEntity> livingEntities = new ArrayList<>();
        if(radius > 0) {
            for (Entity entity : activeSpellObject.getLocation().getWorld().getNearbyEntities(activeSpellObject.getLocation(), radius, radius, radius)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity1 = (LivingEntity) entity;
                    if(!activeSpellObject.didBoltHit(livingEntity1)) {
                        if (activeSpellObject.getLocation().distance(entity.getLocation().add(0, 0.5, 0)) < radius) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                boolean sameteam = mageSpellsManager.teamManager.onSameTeam(activeSpellObject.getCaster(), player);
                                if (activeSpellObject.getCaster().getName().equalsIgnoreCase(player.getName())) {
                                    if (activeSpellObject.isAffectself()) {
                                        livingEntities.add(player);
                                        activeSpellObject.setBoltHit(player);
                                    }
                                } else {
                                    if (sameteam) {
                                        if (activeSpellObject.isAffectteammates()) {
                                            if (!activeSpellObject.didBoltHit(player)) {
                                                livingEntities.add(player);
                                                activeSpellObject.setBoltHit(player);
                                            }
                                        }
                                    } else {
                                        if (activeSpellObject.isAffectenemys()) {
                                            if (!activeSpellObject.didBoltHit(player)) {
                                                livingEntities.add(player);
                                                activeSpellObject.setBoltHit(player);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (activeSpellObject.isAffectmobs()) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (!activeSpellObject.didBoltHit(livingEntity)) {
                                        livingEntities.add(livingEntity);
                                        activeSpellObject.setBoltHit(livingEntity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return livingEntities;
    }
}
