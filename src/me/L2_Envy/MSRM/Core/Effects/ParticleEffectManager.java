package me.L2_Envy.MSRM.Core.Effects;

import com.sun.org.apache.xpath.internal.SourceTree;
import de.slikey.effectlib.util.ParticleEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.ParticleObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Daniel on 7/24/2016.
 */
public class ParticleEffectManager {
    public MageSpellsManager mageSpellsManager;
    public ParticleEffectManager(){

    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void playParticle(ActiveSpellObject activeSpellObject) {
       for(ParticleObject particle : activeSpellObject.getParticleObjects()) {
           particle.getParticle().display(particle.getOffSetX(),particle.getOffSetY(),particle.getOffSetZ(),particle.getSpeed(),particle.getAmount(),activeSpellObject.getLocation(),200);
           /* particle.getParticle().send(
                    Bukkit.getOnlinePlayers(),
                    activeSpellObject.getLocation(),
                    particle.getOffSetX(),
                    particle.getOffSetY(),
                    particle.getOffSetZ(),
                    particle.getSpeed(),
                    particle.getAmount());*/
        }
    }
    public void playParticle(ActiveSpellObject activeSpellObject, Location location) {
        for(ParticleObject particle : activeSpellObject.getParticleObjects()) {
            particle.getParticle().display(particle.getOffSetX(),particle.getOffSetY(),particle.getOffSetZ(),particle.getSpeed(),particle.getAmount(),location.add(0,0.5,0), 200);

            /*particle.getParticle().send(
                    Bukkit.getOnlinePlayers(),
                    location.add(0,0.5,0),
                    particle.getOffSetX(),
                    particle.getOffSetY(),
                    particle.getOffSetZ(),
                    particle.getSpeed(),
                    particle.getAmount());*/
        }
    }
}
