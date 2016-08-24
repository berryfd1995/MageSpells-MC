package me.L2_Envy.MSRM.Core.Spells;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Daniel on 7/23/2016.
 */
public class SpellManager {
    public MageSpellsManager mageSpellsManager;
    private ArrayList<SpellObject> spellObjects;
    public SpellManager(){
        spellObjects = new ArrayList<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public boolean hasSpellObject(SpellObject spellObject){
        return spellObjects.contains(spellObject);
    }
    public void addSpellObject(SpellObject spellObject){
        if(!spellObjects.contains(spellObject)){
            spellObjects.add(spellObject);
            sortSpells();
        }
    }
    public void removeSpellObject(SpellObject spellObject){
        if(!spellObjects.contains(spellObject)){
            spellObjects.remove(spellObject);
        }
    }
    public SpellObject getSpellFromName(String spellname){
        for(SpellObject spellObject : spellObjects){
            if(spellObject.getName().equalsIgnoreCase(spellname)){
                return spellObject;
            }
        }
        return null;
    }
    public SpellObject getSpellFromDisplayName(String display){
        for(SpellObject spellObject : spellObjects){
            if(display.equalsIgnoreCase(spellObject.getDisplayname())){
                return spellObject;
            }
        }
        return null;
    }
    public void sortSpells() {
        Collections.sort(spellObjects, new SpellObject.CompId());
        mageSpellsManager.spellUI.resortSpellPages(spellObjects);
    }
    public ArrayList<SpellObject> getSpellObjects(){
        return spellObjects;
    }

}
