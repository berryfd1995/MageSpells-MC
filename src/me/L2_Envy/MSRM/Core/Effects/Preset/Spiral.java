package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import static java.lang.Math.PI;

/**
 * Created by berry on 2/8/2017.
 */
public class Spiral implements SpellEffect{
    private String name = "spiral";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void onHit(LivingEntity livingEntity){

    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public String getName(){
        return name;
    }
    public void initialSetup(){

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
    }
    double point = 0;
    public Location plotSpellPoint(){
        //point on circle
        Location spellpoint = spelllocation.clone();
        //increase point up 1.
        spellpoint.add(vector);
        //vector u  = vector spelllocationspellpoint and vector are parallel to plane
        Vector u = new Vector(spellpoint.getX()-spelllocation.getX(), spellpoint.getY() - spelllocation.getY(), spellpoint.getZ() - spelllocation.getZ());
        //if u DOT vecotr is 0, they are on same line.
        if(u.dot(vector) == 0) {
            //radius
            int radius = 4;
            //get normal vector to line. make it a unit vector
            Vector normal = vector.getCrossProduct(u).normalize();
            //get x y and z
            double x = spelllocation.getX() + radius * Math.cos(point)*u.getX() + radius * Math.sin(point)*normal.getX();
            double y = spelllocation.getY() + radius * Math.cos(point)*u.getY() + radius * Math.sin(point)*normal.getY();
            double z = spelllocation.getZ() + radius * Math.cos(point)*u.getZ() + radius * Math.sin(point)*normal.getZ();
            //increment angle
            point += (1/PI)/(radius/2);
            //plot
            spellpoint = new Location(spelllocation.getWorld(),x,y,z);
        }
        return spellpoint;
    }
}
