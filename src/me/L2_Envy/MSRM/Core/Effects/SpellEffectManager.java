package me.L2_Envy.MSRM.Core.Effects;

import me.L2_Envy.MSRM.Core.Effects.Preset.*;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/25/2016.
 */
public class SpellEffectManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<SpellEffect> spellEffects;

    public SpellEffectManager(){
       /* spellEffects = new ArrayList<>();
        spellEffects.add(new HomingSpellEffect());
        spellEffects.add(new NormalEffect());
        spellEffects.add(new Fire());
        spellEffects.add(new Lightning());
        spellEffects.add(new Explode());
        spellEffects.add(new GravityPush());
        spellEffects.add(new Teleport());
        spellEffects.add(new Vampire());
        spellEffects.add(new Meteor());
        spellEffects.add(new ChainStrike());
        spellEffects.add(new Petrify());
        spellEffects.add(new Armageddon());
        spellEffects.add(new Storm());
        spellEffects.add(new HailMary());
        spellEffects.add(new SpellDrop());
        spellEffects.add(new Launch());
        spellEffects.add(new Spiral());*/
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void addSpellEffect(SpellEffect spellEffect){
         //spellEffects.add(spellEffect);
     }
    public boolean hasSpellEffect(SpellEffect spellEffect){
        //return spellEffects.contains(spellEffect);
        return false;
    }
    public boolean hasSpellEffect(String spellEffect){
        if(getSpellEffect(spellEffect) != null){
            return true;
        }
        return false;
    }
    public SpellEffect getSpellEffect(String spellEffect){
        switch (spellEffect.toLowerCase()){
            case "armageddon":
                return new Armageddon();
            case "chain":
                return new ChainStrike();
            case "normal":
                return new NormalEffect();
            case "fire":
                return new Fire();
            case "meteor":
                return new Meteor();
            case "hailmary":
                return new HailMary();
            case "launch":
                return new Launch();
            case "spiral":
                return new Spiral();
            case "vampire":
                return  new Vampire();
            case "lightning":
                return new Lightning();
            case "teleport":
                return new Teleport();
            case "storm":
                return new Storm();
            case "push":
                return new GravityPush();
            case "petrify":
                return new Petrify();
            case "explode":
                return new Explode();
            case "arcspell":
                return new SpellDrop();
            case "meteor2":
                return new Meteor2();
            case "homing":
                return new HomingSpellEffect();
            case "shield":
                return new Shield();
            case "helix":
                return new Helix();
                default:
                    break;
        }
        return null;
    }
    public SpellEffect setupSpellEffect(SpellObject spellObject, Player player){
        SpellEffect spelleffect;
        if(mageSpellsManager.spellEffectManager.hasSpellEffect(spellObject.getSpellEffect())){
            spelleffect = mageSpellsManager.spellEffectManager.getSpellEffect(spellObject.getSpellEffect());
        }else{
            spelleffect = new NormalEffect();
        }
        ActiveSpellObject activeSpellObject = new ActiveSpellObject(spellObject, player.getLocation().add(0,1,0), player);
        spelleffect.setInitialLocation(player.getLocation().add(0,1,0));
        spelleffect.setInitialVector(activeSpellObject.getCaster().getEyeLocation().getDirection().multiply(spellObject.getSpellSpeed() * .01));
        spelleffect.setActiveSpell(activeSpellObject);
        spelleffect.initialSetup();
        return spelleffect;
    }
    public void createExplosion(Location l, float size){
        if(Main.getPluginManager().worldEditAPI.allowExplosionInRegion(l)){
            l.getWorld().createExplosion(l,size);
        }else {
            l.getWorld().createExplosion(l, 0);
        }
    }
}
