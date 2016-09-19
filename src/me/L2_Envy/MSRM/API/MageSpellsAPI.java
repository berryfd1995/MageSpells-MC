package me.L2_Envy.MSRM.API;

import me.L2_Envy.MSRM.API.Managers.SpellCreationManager;
import me.L2_Envy.MSRM.API.Managers.WandCreationManager;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.ParticleObject;
import me.L2_Envy.MSRM.PluginManager.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 7/23/2016.
 */
public class MageSpellsAPI {
    public static PluginManager pluginManager;
    public static SpellCreationManager spellCreationManager;
    public static WandCreationManager wandCreationManager;
    public MageSpellsAPI(){
        spellCreationManager = new SpellCreationManager();
        wandCreationManager = new WandCreationManager();
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }
    /**
     * @param spellEffect
     */
    public static void shootSpell(SpellEffect spellEffect){
        pluginManager.main.mageSpellsManager.activeSpellManager.shootSpell(spellEffect);
    }

    /**
     * @param to
     * @param from
     * @return
     */
    public static Vector createNewVector(Location to, Location from){
        Vector vector = null;
        vector = to.toVector().subtract(from.toVector()).multiply(2);
        vector.normalize();
        return vector;
    }

    /**
     * @param spellEffect
     */
    public static void addSpellEffect(SpellEffect spellEffect){
        pluginManager.main.mageSpellsManager.spellEffectManager.addSpellEffect(spellEffect);
    }

    /**
     * @param activeSpellObject
     * @return
     */
    public static ActiveSpellObject cloneActiveSpellObject(ActiveSpellObject activeSpellObject){
        ActiveSpellObject activeSpellObject1 = new ActiveSpellObject(activeSpellObject.getSpellObject(), activeSpellObject.getLocation().clone(),activeSpellObject.getCaster());
        return activeSpellObject1;
    }

    /**
     *
     * @param activeSpellObject
     * @param location
     */
    public static void playParticle(ActiveSpellObject activeSpellObject, Location location) {
        for(ParticleObject particle : activeSpellObject.getParticleObjects()) {
            particle.getParticle().send(
                    Bukkit.getOnlinePlayers(),
                    location,
                    particle.getOffSetX(),
                    particle.getOffSetY(),
                    particle.getOffSetZ(),
                    particle.getSpeed(),
                    particle.getAmount());
        }
    }

}
