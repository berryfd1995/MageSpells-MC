package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/7/2016.
 */
public class Petrify implements SpellEffect{
    private String name = "petrify";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public SpellEffect Run(){
        spelllocation.add(vector);
        return null;
    }
    public void onHit(LivingEntity livingEntity){
        if(!(livingEntity instanceof Player)){
            Location location = livingEntity.getLocation();
            location.getBlock().setType(Material.STONE);
            location.add(0,1,0).getBlock().setType(Material.STONE);
            livingEntity.setHealth(0);
        }else{

        }
    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void setInitialVector(Vector vector){
        this.vector = vector;
    }
    public Location plotSpellPoint(){
        return spelllocation;
    }
    public String getName(){
        return name;
    }
    public void initialSetup(){

    }
    public Location getSpellLocation(){
        return spelllocation;
    }
    public SpellEffect spellEndingSeq(){
        return null;
    }
    public boolean shouldEnd(){
        return false;
    }
    public SpellEffect auraRun(){
        return null;
    }
    public SpellEffect castNewSpell(){
        return null;
    }
}
