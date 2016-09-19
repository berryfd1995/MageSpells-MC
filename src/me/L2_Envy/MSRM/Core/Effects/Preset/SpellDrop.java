package me.L2_Envy.MSRM.Core.Effects.Preset;

import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.Core.Interfaces.SpellEffect;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Created by Daniel on 9/14/2016.
 */
public class SpellDrop implements SpellEffect {
    private String name = "arcspell";
    private Vector vector;
    private Location spelllocation;
    private ActiveSpellObject activeSpellObject;
    private int i = 0;
    private int points = 50;
    double increment = (2 * Math.PI) / 10;
    public ActiveSpellObject getActiveSpell(){
        return activeSpellObject;
    }
    public void setActiveSpell(ActiveSpellObject activeSpellObject){
        this.activeSpellObject = activeSpellObject;
    }
    public void Run(){
        double angle = i * increment;
        double x = spelllocation.getX() + (4 * Math.cos(angle));
        double z = spelllocation.getZ() + (4 * Math.sin(angle));
        i++;
        if(i == points){
            i = 0;
        }
        MageSpellsAPI.playParticle(getActiveSpell(), new Location(spelllocation.getWorld(),x,spelllocation.getY(),z));

    }
    public void onHit(LivingEntity livingEntity){
        /*int j,spots = 10;
        double pos = .5,size = 2;
        for(int s = 0; s < 8; s++) {
            for (j = 0; j < 360; j += 360 / spots) {
                double angle = (j * Math.PI / 180);
                double x = size * Math.cos(angle);
                double z = size * Math.sin(angle);
                MageSpellsAPI.playParticle(getActiveSpell(), livingEntity.getLocation().clone().add(x, pos, z));
            }
            pos = pos + .5;
            if(s > 4) {
                if (size <= 0) {
                    size = .2;
                }else{
                    size -= .5;
                }
            }
        }*/
    }
    public void setInitialLocation(Location location) {
        this.spelllocation = location;

    }
    public void setInitialVector(Vector vector){
        this.vector = vector;
    }
    public Location plotSpellPoint(){
        vector = vector.setY(vector.getY() -.08);
        spelllocation.add(vector);
        return spelllocation;
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
        //int j,spots = activeSpellObject.getAuraradius()*2;
        /*int j,spots = activeSpellObject.getAuraradius()*activeSpellObject.getAuraradius();
>>>>>>> 3aa85c4deca3f071a893b896ee3e2ec0f3930dac
        for (j = 0; j < 360; j += 360/spots) {
            double angle = (j * Math.PI / 180);
            double x = activeSpellObject.getAuraradius() * Math.cos(angle);
            double z = activeSpellObject.getAuraradius() * Math.sin(angle);
            MageSpellsAPI.playParticle(getActiveSpell(), spelllocation.clone().add(x,1,z));
        }*/
    }
}
