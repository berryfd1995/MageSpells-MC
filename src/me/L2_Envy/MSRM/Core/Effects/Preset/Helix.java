package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import static java.lang.Math.PI;

/**
 * Created by Daniel on 2/9/2017.
 */
public class Helix implements SpellEffect {
    private String name = "helix";
    private Vector vector;
    private Vector normal;
    private Vector normal2;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    private double angle = 0;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void onHit(LivingEntity livingEntity){

    }
    public void setStartingAngle(double angle){
        this.angle = angle;
    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public String getName(){
        return name;
    }
    public void initialSetup(){
        if(angle == 0) {
           int spirals = 5;
            try {
                String var = activeSpellObject.getVariables().get(0);
                spirals = Integer.parseInt(var);
            } catch (NumberFormatException ex) {

            }
            double angleportion = ((360/(1+spirals))*PI)/180;
            for (int i = 1; i < spirals+1; i++) {
                Helix spellEffect = new Helix();
                spellEffect.setStartingAngle(angleportion*i);
                spellEffect.setActiveSpell(MageSpellsAPI.cloneActiveSpellObject(activeSpellObject));
                spellEffect.setInitialLocation(spelllocation.clone());
                spellEffect.setInitialVector(spelllocation.getDirection().clone().multiply(2));
                MageSpellsAPI.shootSpell(spellEffect);
            }
        }
    }
    public void spellEndingSeq(){
    }
    public boolean shouldEnd(){
        return false;
    }
    public void auraRun(){
    }

/*
x(θ)=c1+rcos(θ)a1+rsin(θ)b1
y(θ)=c2+rcos(θ)a2+rsin(θ)b2
z(θ)=c3+rcos(θ)a3+rsin(θ)b3
 */

    public void Run(){
        //moves spell along axis
        spelllocation.add(vector);
    }
    public void setInitialVector(Vector vector){
        this.vector = vector;
        Vector axis = vector.clone().normalize();
        Location center = spelllocation.clone();
        Vector u = new Vector(-center.getX(), -center.getY(), -center.getZ()).normalize();
        normal = u.getCrossProduct(axis).normalize();
        normal2 = normal.getCrossProduct(axis).normalize();
    }
    private double inc = .5;
    private double radius = inc;
    public Location plotSpellPoint(){
        /*try {
            if(activeSpellObject.getVariables().size()  >=2) {
                String var = activeSpellObject.getVariables().get(1);
                radius = Integer.parseInt(var);
            }
        } catch (NumberFormatException ex) {

        }*/
        double increment = (1/PI);
        double x = spelllocation.getX() + radius*Math.cos(angle)*normal.getX() + radius*Math.sin(angle)*normal2.getX();
        double y = spelllocation.getY() + radius*Math.cos(angle)*normal.getY() + radius*Math.sin(angle)*normal2.getY();
        double z = spelllocation.getZ() + radius*Math.cos(angle)*normal.getZ() + radius*Math.sin(angle)*normal2.getZ();
        angle += increment;//Increment angle
        radius+= inc;
        return new Location(spelllocation.getWorld(),x,y,z);
    }
    public void auraEndingSequence(){

    }
}
