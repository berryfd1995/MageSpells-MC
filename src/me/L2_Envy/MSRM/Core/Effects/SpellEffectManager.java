package me.L2_Envy.MSRM.Core.Effects;

import me.L2_Envy.MSRM.Core.Effects.Preset.*;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.MageSpellsManager;

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
}
