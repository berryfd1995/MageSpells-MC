package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/7/2016.
 */
public class HailMary implements SpellEffect{
    private String name = "hailmary";
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
       /* for(int i = 0; i <6; i++){
            for(int j = 0; j < 2; j++){
                Location fromloc = activeSpellObject.getCaster().getEyeLocation()
                        .add(getRightHeadDirection(activeSpellObject.getCaster()).multiply(i)).add(0,j,0);
                SpellEffect spellEffect = new NormalEffect();
                spellEffect.setActiveSpell(MageSpellsAPI.cloneActiveSpellObject(activeSpellObject));
                spellEffect.setInitialLocation(fromloc);
                spellEffect.setInitialVector(fromloc.getDirection());
                MageSpellsAPI.shootSpell(spellEffect);
            }
        }
        for(int i = 0; i <6; i++){
            for(int j = 0; j < 2; j++){
                Location fromloc = activeSpellObject.getCaster().getEyeLocation()
                        .add(getLeftHeadDirection(activeSpellObject.getCaster()).multiply(i)).add(0,j,0);
                SpellEffect spellEffect = new NormalEffect();
                spellEffect.setActiveSpell(MageSpellsAPI.cloneActiveSpellObject(activeSpellObject));
                spellEffect.setInitialLocation(fromloc);
                spellEffect.setInitialVector(fromloc.getDirection());
                MageSpellsAPI.shootSpell(spellEffect);
            }
        }*/
        for(int i = 0; i <6; i++){
                Location fromloc = activeSpellObject.getCaster().getEyeLocation()
                        .add(getRightHeadDirection(activeSpellObject.getCaster()).multiply(i));
                SpellEffect spellEffect = new NormalEffect();
                spellEffect.setActiveSpell(MageSpellsAPI.cloneActiveSpellObject(activeSpellObject));
                spellEffect.setInitialLocation(fromloc);
                spellEffect.setInitialVector(fromloc.getDirection());
                MageSpellsAPI.shootSpell(spellEffect);
        }
        for(int i = 0; i <6; i++){
                Location fromloc = activeSpellObject.getCaster().getEyeLocation()
                        .add(getLeftHeadDirection(activeSpellObject.getCaster()).multiply(i));
                SpellEffect spellEffect = new NormalEffect();
                spellEffect.setActiveSpell(MageSpellsAPI.cloneActiveSpellObject(activeSpellObject));
                spellEffect.setInitialLocation(fromloc);
                spellEffect.setInitialVector(fromloc.getDirection());
                MageSpellsAPI.shootSpell(spellEffect);
        }
        activeSpellObject.setAuraenabled(false);
        activeSpellObject.setBoltenabled(false);
        activeSpellObject.setSprayenabled(false);
    }
    public Location getSpellLocation(){
        return spelllocation;
    }
    public void spellEndingSeq(){
    }
    public boolean shouldEnd(){
        return true;
    }
    public void auraRun(){
    }

    public static Vector getRightHeadDirection(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        return new Vector(-direction.getZ(), 0.0, direction.getX()).normalize();
    }

    public static Vector getLeftHeadDirection(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        return new Vector(direction.getZ(), 0.0, -direction.getX()).normalize();
    }
}
