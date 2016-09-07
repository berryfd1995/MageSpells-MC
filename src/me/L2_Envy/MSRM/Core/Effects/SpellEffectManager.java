package me.L2_Envy.MSRM.Core.Effects;

import me.L2_Envy.MSRM.Core.Effects.Preset.*;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Daniel on 7/25/2016.
 */
public class SpellEffectManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<SpellEffect> spellEffects;

    public SpellEffectManager(){
        spellEffects = new ArrayList<>();
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
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void addSpellEffect(SpellEffect spellEffect){
         spellEffects.add(spellEffect);
     }
    public boolean hasSpellEffect(SpellEffect spellEffect){
        return spellEffects.contains(spellEffect);
    }
    public boolean hasSpellEffect(String spellEffect){
        for(SpellEffect spellEffect1 : spellEffects){
            if(spellEffect1.getName().equalsIgnoreCase(spellEffect.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    public SpellEffect getSpellEffect(String spellEffect){
        for(SpellEffect spellEffect1 : spellEffects){
            if(spellEffect1.getName().equalsIgnoreCase(spellEffect.toLowerCase())){
                return spellEffect1;
            }
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
        spelleffect.setInitialVector(activeSpellObject.getCaster().getEyeLocation().getDirection().multiply(2));
        spelleffect.setActiveSpell(activeSpellObject);
        spelleffect.initialSetup();
        return spelleffect;
    }
}
