package me.L2_Envy.MSRM.Core.Mana;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/**
 * Created by Daniel on 7/23/2016.
 */
public class ManaManager {
    public MageSpellsManager mageSpellsManager;
    private HashMap<PlayerObject, BukkitTask> manatask;
    private HashMap<PlayerObject, BukkitTask> chargetask;
    private HashMap<PlayerObject, BukkitTask> cooldowntask;
    private int maxmana;
    private int regenrate;
    private boolean displayexactvalue;
    private String inactivemana;
    private String activemana;
    private String lowmana;
    private String manabrackets;
    private String chargestatusbar;
    private String chargeleft;
    private String chargebracket;
    private String cooldownstausbar;
    private String cooldownleft;
    private String cooldownbracket;
    public ManaManager(){
        manatask = new HashMap<>();
        chargetask = new HashMap<>();
        cooldowntask = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void defineSettings(int maxmana, int regenrate, String inactivemana, String activemana,String lowmana, String manabrackets, String chargestatusbar, String chargeleft, String chargebracket,String cooldownstausbar, String cooldownleft, String cooldownbracket, boolean displayexactvalue){
        this.maxmana = maxmana;
        this.regenrate = regenrate;
        this.inactivemana = inactivemana;
        this.activemana = activemana;
        this.lowmana = lowmana;
        this.manabrackets = manabrackets;
        this.chargestatusbar = chargestatusbar;
        this.chargeleft = chargeleft;
        this.chargebracket = chargebracket;
        this.cooldownstausbar = cooldownstausbar;
        this.cooldownbracket = cooldownbracket;
        this.cooldownleft = cooldownleft;
        this.displayexactvalue = displayexactvalue;
    }
    public void regenMana(PlayerObject player){
        if(player.getCurrentmana() < maxmana) {
            addMana(player, regenrate);
            updateManaBar(player);
        }
    }
    public void scheduleManaTask(PlayerObject playerObject){
        playerObject.setCurrentmana(maxmana);
        manatask.put(playerObject, Bukkit.getScheduler().runTaskTimerAsynchronously(mageSpellsManager.main,() -> {
                regenMana(playerObject);
        },10L,20L));
    }
    public void scheduleChargeTask(String name,PlayerObject playerObject, SpellObject spellObject,int time){
        playerObject.setCharging(true);
        playerObject.setTimeleftoncharge(time);
        playerObject.setMaxchargetime(time);
        chargetask.put(playerObject, Bukkit.getScheduler().runTaskTimerAsynchronously(mageSpellsManager.main, () ->{
            if(playerObject.getTimeleftoncharge() == 0){
                playerObject.setTimeleftoncharge(0);
                playerObject.setMaxchargetime(0);
                playerObject.setCharging(false);
                mageSpellsManager.castingManager.doSpellCasting(name, spellObject);
                chargetask.get(playerObject).cancel();
            }else{
                playerObject.tickTimeLeftOnCharge();
                updateManaBar(playerObject);
            }
        },0L,20L));
    }
    public void scheduleCooldownTask(PlayerObject playerObject, int time){
        playerObject.setOncooldown(true);
        playerObject.setMaxcooldowntime(time);
        playerObject.setTimeleftoncooldwon(time);
        cooldowntask.put(playerObject,Bukkit.getScheduler().runTaskTimerAsynchronously(mageSpellsManager.main, () -> {
            if(playerObject.getTimeleftoncooldwon() == 0){
                playerObject.setTimeleftoncooldwon(0);
                playerObject.setMaxcooldowntime(0);
                playerObject.setOncooldown(false);
                cooldowntask.get(playerObject).cancel();
            }else{
                playerObject.tickTimeLeftOnCooldown();
                updateManaBar(playerObject);
            }
        },0L,20L));
    }
    //schedule cooldown task
    public void removeManaTask(PlayerObject playerObject){
        if(manatask.containsKey(playerObject)){
            manatask.get(playerObject).cancel();
        }
    }
    public void addMana(PlayerObject player, int amount){
        if(player.getCurrentmana() + amount >= maxmana){
            player.setCurrentmana(maxmana);
        }else {
            player.setCurrentmana(player.getCurrentmana() + amount);
        }
    }
    public void removeMana(PlayerObject playerObject, int amount){
        if(hasAtLeastMana(playerObject, amount)){
            playerObject.setCurrentmana(playerObject.getCurrentmana()-amount);
        }
    }
    public boolean hasAtLeastMana(PlayerObject playerObject, int amount){
        return playerObject.getCurrentmana() - amount >=0;
    }
    public void setMana(PlayerObject playerObject, int amount){
        playerObject.setCurrentmana(amount);
    }

    public void updateManaBar(PlayerObject playerObject){
        StringBuilder sb = new StringBuilder();
        String leftBracket = "﴾";
        String rightBracket = "﴿";
        double bars;
        int j =0;
        if(playerObject.isOnCooldown()){
            sb.append(cooldownbracket).append(leftBracket);
            if(playerObject.getMaxcooldowntime() < 5){
                j = playerObject.getMaxcooldowntime()+1;
            }else{
                j = 6;
            }
            bars = playerObject.getMaxcooldowntime() / (j-1);// each bar = num/5 Ex: max is 10/5 = 2. So every 2 points, make active bar.
            for (int i = 1; i < j; i++) {
                String chargebar = "▬";
                //for each bar is it active or no
                if (i*bars <= playerObject.getTimeleftoncooldwon()) {
                    sb.append(cooldownstausbar).append(chargebar);
                } else {
                    sb.append(cooldownleft).append(chargebar);
                }
            }
            sb.append(cooldownbracket).append(rightBracket);
        }
        if(displayexactvalue) {
            sb.append(activemana).append(" "+(int) (playerObject.getCurrentmana() + 0.5d) + " ");
        }
        sb.append(manabrackets).append(leftBracket);
        bars = maxmana / 25; //each bar = num/25
        for (int i = 1; i < 26; i++) {
            String manabar = "▬";
            if (i*bars <= playerObject.getCurrentmana()) {
                    sb.append(activemana).append(manabar);
            } else {
                if(playerObject.getCurrentmana() > maxmana *.25) {
                    sb.append(inactivemana).append(manabar);
                }else{
                    sb.append(lowmana).append(manabar);
                }
            }
        }
        sb.append(manabrackets).append(rightBracket);
        if(playerObject.isCharging()){
            sb.append(chargebracket).append(leftBracket);
            if(playerObject.getMaxcooldowntime() < 5){
                j = playerObject.getMaxchargetime()+1;
            }else{
                j = 6;
            }
            bars = playerObject.getMaxchargetime() / (j-1);// each bar = num/5 Ex: max is 10/5 = 2. So every 2 points, make active bar.
            for (int i = 1; i < j ; i++) {
                String chargebar = "▬";
                //for each bar is it active or no
                if (i*bars <= playerObject.getTimeleftoncharge()) {
                    sb.append(chargestatusbar).append(chargebar);
                } else {
                    sb.append(chargeleft).append(chargebar);
                }
            }
            sb.append(chargebracket).append(rightBracket);
        }

        new ActionbarTitleObject(mageSpellsManager.main.utils.colorize(sb.toString())).send(mageSpellsManager.main.utils.getOnlinePlayerFromName(playerObject.getName()));
    }

}
