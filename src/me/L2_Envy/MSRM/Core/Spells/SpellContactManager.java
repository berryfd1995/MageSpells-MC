package me.L2_Envy.MSRM.Core.Spells;

import it.unimi.dsi.fastutil.Hash;
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

    public void checkSpellContact(ActiveSpellObject activeSpellObject){
        if(mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(activeSpellObject.getLocation())) {
            if (activeSpellObject.isSprayenabled()) {
                for (LivingEntity livingEntity : getNearbyEntites(activeSpellObject, activeSpellObject.getSprayradius())) {
                    //particle
                    if (mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
                        mageSpellsManager.particleEffectManager.playParticle(activeSpellObject, livingEntity.getLocation().clone());
                        //potion affect
                        mageSpellsManager.potionEffectManager.playPotionEffect(activeSpellObject, livingEntity);
                        //damage
                        mageSpellsManager.damageEffectManager.doDamage(activeSpellObject, livingEntity, false, true, false);
                    }
                }
            }
            if (activeSpellObject.isBoltenabled()) {
                if (boltHit(activeSpellObject)) {
                    for (LivingEntity livingEntity : getNearbyEntitesBolt(activeSpellObject, activeSpellObject.getBoltradius())) {
                        if(mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
                            mageSpellsManager.particleEffectManager.playParticle(activeSpellObject, livingEntity.getLocation().clone());
                            //potion affect
                            mageSpellsManager.potionEffectManager.playPotionEffect(activeSpellObject, livingEntity);
                            //damage
                            mageSpellsManager.damageEffectManager.doDamage(activeSpellObject, livingEntity, true, false, false);
                        }
                    }
                    initiateSpellEndSeq(activeSpellObject);
                }
            }
        }
    }

    public void initiateSpellEndSeq(ActiveSpellObject activeSpellObject){
        if(activeSpellObject.isAuraenabled()){
            mageSpellsManager.activeSpellManager.removeSpell(activeSpellObject);
            activeAura(activeSpellObject);
        }else{
            mageSpellsManager.activeSpellManager.removeSpell(activeSpellObject);
        }
    }
    public void activeAura(ActiveSpellObject activeSpellObject){
        activeSpellObject.clearSprayHit();
       activeSpellObject.setAuratimertask(Bukkit.getScheduler().scheduleSyncRepeatingTask(mageSpellsManager.main, () -> {
           if(activeSpellObject.getAuratimeleft() >0) {
               mageSpellsManager.particleEffectManager.playParticle(activeSpellObject, activeSpellObject.getLocation().clone());
               for (LivingEntity livingEntity : getNearbyEntites(activeSpellObject, activeSpellObject.getAuraradius())) {
                   if (mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
                       mageSpellsManager.particleEffectManager.playParticle(activeSpellObject, livingEntity.getLocation().clone());
                       //potion
                       mageSpellsManager.potionEffectManager.playPotionEffect(activeSpellObject, livingEntity);
                       //damage
                       mageSpellsManager.damageEffectManager.doDamage(activeSpellObject, livingEntity, false, false, true);
                       //sound
                       livingEntity.getLocation().getWorld().playSound(livingEntity.getLocation(), activeSpellObject.getSound(), activeSpellObject.getSoundvolume(), activeSpellObject.getSoundpitch());
                   }
               }
               activeSpellObject.clearSprayHit();
               activeSpellObject.tickauratimer();
           }else{
               removeAuraTask(activeSpellObject);
           }
       },0L,20L));
    }
    public void removeAuraTask(ActiveSpellObject activeSpellObject){
        Bukkit.getScheduler().cancelTask(activeSpellObject.getAuratimertask());
    }
    //Different from bolt radius. Bolt hit must be within 1.5 blocks to be activated. and end spell
    public boolean boltHit(ActiveSpellObject activeSpellObject){
        double radius = 1.5;
        for (Entity entity : activeSpellObject.getLocation().getWorld().getEntities()) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity1 = (LivingEntity) entity;
                    if (activeSpellObject.getLocation().distance(entity.getLocation().clone().add(0, 0.5, 0)) < radius) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            boolean sameteam = mageSpellsManager.teamManager.onSameTeam(activeSpellObject.getCaster(), player);
                            if (activeSpellObject.getCaster().getName().equalsIgnoreCase(player.getName())) {
                                if (activeSpellObject.isAffectself()) {
                                    activeSpellObject.setBoltHit(player);
                                    if(activeSpellObject.didBoltHit(player)) {
                                        attackEntityBolt(activeSpellObject, player);
                                    }
                                    return true;
                                }
                            } else {
                                if (sameteam) {
                                    if (activeSpellObject.isAffectteammates()) {
                                        activeSpellObject.setBoltHit(player);
                                        if(activeSpellObject.didBoltHit(player)) {
                                            attackEntityBolt(activeSpellObject, player);
                                        }
                                        return true;
                                    }
                                } else {
                                    if (activeSpellObject.isAffectenemys()) {
                                        activeSpellObject.setBoltHit(player);
                                        if(activeSpellObject.didBoltHit(player)) {
                                            attackEntityBolt(activeSpellObject, player);
                                        }
                                        return true;
                                    }
                                }
                            }
                        } else {
                            if (activeSpellObject.isAffectmobs()) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                activeSpellObject.setBoltHit(livingEntity);
                                if(activeSpellObject.didBoltHit(livingEntity)) {
                                    attackEntityBolt(activeSpellObject, livingEntity);
                                }
                                return true;
                            }
                        }
                    }
                }
        }
        return false;
    }
    public void attackEntityBolt(ActiveSpellObject activeSpellObject, LivingEntity livingEntity){
        if(mageSpellsManager.main.pluginManager.worldEditAPI.allowSpellInRegion(livingEntity.getLocation())) {
            mageSpellsManager.potionEffectManager.playPotionEffect(activeSpellObject, livingEntity);
            //damage
            mageSpellsManager.damageEffectManager.doDamage(activeSpellObject, livingEntity, true, false, false);
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
