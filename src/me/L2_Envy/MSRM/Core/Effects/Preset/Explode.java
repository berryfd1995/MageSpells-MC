package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 8/28/2016.
 */
public class Explode implements SpellEffect{
    private String name = "Explode";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void Run(){
        spelllocation.add(vector);
    }
    public void onHit(LivingEntity livingEntity){
        try {
            String var = activeSpellObject.getVariables().get(0);
            float i = Float.parseFloat(var);
            MageSpellsAPI.pluginManager.main.mageSpellsManager.spellEffectManager.createExplosion(spelllocation, i);
        }catch(Exception e){
            MageSpellsAPI.pluginManager.main.mageSpellsManager.spellEffectManager.createExplosion(spelllocation, 4.0F);
        }

    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void setInitialVector(Vector vector){
        this.vector =vector;
    }
    public Location plotSpellPoint(){
        return spelllocation;
    }
    public String getName(){
        return name;
    }
    public void initialSetup(){

    }
    public void spellEndingSeq(){
        try {
            String var = activeSpellObject.getVariables().get(0);
            float i = Float.parseFloat(var);
            MageSpellsAPI.pluginManager.main.mageSpellsManager.spellEffectManager.createExplosion(spelllocation, i);
        }catch(Exception e){
            MageSpellsAPI.pluginManager.main.mageSpellsManager.spellEffectManager.createExplosion(spelllocation, 4.0F);

        }

    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun() {
    }
    public void auraEndingSequence(){

    }
}
