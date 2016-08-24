package me.L2_Envy.MSRM.Core.Objects;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Daniel on 7/25/2016.
 */
public class PlayerObject {
    private String name;
    private UUID uuid;
    private int level = 1;
    private long experience;
    private File playerFile;
    private FileConfiguration playerconfig;
    private ArrayList<SpellObject> spellObjects;
    private ArrayList<WandObject> wandObjects;
    private Inventory inventory;
    private int currentmana;
    private boolean readytocast;
    private boolean charging;
    private boolean oncooldown;
    private int timeleftoncharge;
    private int maxchargetime;
    private int timeleftoncooldwon;
    private int maxcooldowntime;
    public PlayerObject(String name, UUID uuid, int level, Long experience, File playerFile, FileConfiguration playerconfig, ArrayList<SpellObject> spellObjects, ArrayList<WandObject> wandObjects, Inventory inventory) {
        this.name = name;
        this.uuid = uuid;
        this.level = level;
        this.experience = experience;
        this.playerFile = playerFile;
        this.playerconfig = playerconfig;
        this.spellObjects = spellObjects;
        this.wandObjects = wandObjects;
        this.inventory = inventory;
        currentmana = 100;
        readytocast = true;
        charging = false;
        timeleftoncharge = 0;
        maxchargetime = 0;
        oncooldown = false;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLevel() {
        return level;
    }
    public long getExperience(){
        return experience;
    }
    public File getPlayerFile() {
        return playerFile;
    }

    public FileConfiguration getPlayerconfig() {
        return playerconfig;
    }

    public ArrayList<SpellObject> getSpellObjects() {
        return spellObjects;
    }
    public ArrayList<WandObject> getWandObjects() { return  wandObjects; }

    public void teachSpell(SpellObject spellObject){
        if(!knowsSpell(spellObject)){
            spellObjects.add(spellObject);
        }
    }
    public void teachWand(WandObject wandObject){
        if(!knowsWand(wandObject)){
            wandObjects.add(wandObject);
        }
    }
    public boolean knowsWand(WandObject wandObject){
        for(WandObject wandObject1 : wandObjects){
            if(wandObject.equals(wandObject1)){
                return true;
            }
        }
        return false;
    }
    public boolean knowsSpell(SpellObject spellObject){
        for(SpellObject spellObject1: spellObjects){
            if(spellObject.equals(spellObject1)){
                return true;
            }
        }
        return false;
    }
    public void setMaxchargetime(int maxchargetime){ this.maxchargetime = maxchargetime; }
    public int getMaxchargetime(){ return  maxchargetime; }
    public void setTimeleftoncharge(int timeleftoncharge){ this.timeleftoncharge = timeleftoncharge; }
    public int getTimeleftoncharge(){ return  timeleftoncharge; }
    public void tickTimeLeftOnCharge(){
        timeleftoncharge--;
    }
    public int getCurrentmana(){
        return currentmana;
    }
    public void setCurrentmana(int amount){
        this.currentmana = amount;
    }
    public boolean isReadytocast(){
        return readytocast;
    }
    public void setReadytocast(boolean readytocast){
        this.readytocast = readytocast;
    }
    public boolean isOnCooldown(){
        return oncooldown;
    }
    public void setOncooldown(boolean oncooldown){
        this.oncooldown = oncooldown;
    }
    public boolean isCharging() {
        return charging;
    }
    public void setCharging(boolean charging){
        this.charging = charging;
    }

    public void addExperience(Long experience){
        this.experience =this.experience + experience;
    }
    public void setRank(int rank){
        this.level = rank;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public int getTimeleftoncooldwon() {
        return timeleftoncooldwon;
    }

    public void setTimeleftoncooldwon(int timeleftoncooldwon) {
        this.timeleftoncooldwon = timeleftoncooldwon;
    }

    public int getMaxcooldowntime() {
        return maxcooldowntime;
    }

    public void setMaxcooldowntime(int maxcooldowntime) {
        this.maxcooldowntime = maxcooldowntime;
    }
    public void tickTimeLeftOnCooldown(){
        this.timeleftoncooldwon--;
    }
}
