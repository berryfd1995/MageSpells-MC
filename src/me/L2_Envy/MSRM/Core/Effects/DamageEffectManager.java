package me.L2_Envy.MSRM.Core.Effects;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.ActiveSpellObject;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 8/15/2016.
 */
public class DamageEffectManager {
    public MageSpellsManager mageSpellsManager;
    public DamageEffectManager(){

    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void doDamage(ActiveSpellObject activeSpellObject, LivingEntity livingEntity, boolean bolt, boolean spray, boolean aura){
        if(bolt) {
            if(activeSpellObject.getBoltdamage() > 0) {
                livingEntity.damage(calculateActualDamage(livingEntity, activeSpellObject.getBoltdamage(), activeSpellObject.getArmorpiercing()), activeSpellObject.getCaster());
            }
        }
        if(spray){
            if(activeSpellObject.getSpraydamage() > 0) {
                livingEntity.damage(calculateActualDamage(livingEntity, activeSpellObject.getAuradamage(), activeSpellObject.getArmorpiercing()), activeSpellObject.getCaster());
            }
        }
        if(aura){
            if(activeSpellObject.getAuradamage() > 0) {
                livingEntity.damage(calculateActualDamage(livingEntity, activeSpellObject.getSpraydamage(), activeSpellObject.getArmorpiercing()), activeSpellObject.getCaster());
            }
        }
        if(activeSpellObject.getPlayerhitcommand() != null && !activeSpellObject.getPlayerhitcommand().isEmpty() && activeSpellObject.getPlayerhitcommand().length() > 0){
            String str = activeSpellObject.getPlayerhitcommand();
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                if(str.contains("%player%hit")) {
                    str.replaceAll("%player%", player.getName());
                }
                if(str.contains("%caster%")){
                    str.replaceAll("%caster%", activeSpellObject.getCaster().getName());
                }
                player.setOp(true);
                player.performCommand(str);
                player.setOp(false);
            }
        }
        if(activeSpellObject.getCasterhitcommand() != null && !activeSpellObject.getCasterhitcommand().isEmpty() && activeSpellObject.getCasterhitcommand().length() > 0){
            String str = activeSpellObject.getCasterhitcommand();
            if(livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                if (str.contains("%player%hit")) {
                    str.replaceAll("%player%", player.getName());
                }
            }
                if(str.contains("%caster%")){
                    str.replaceAll("%caster%", activeSpellObject.getCaster().getName());
                }
            activeSpellObject.getCaster().setOp(true);
            activeSpellObject.getCaster().performCommand(str);
            activeSpellObject.getCaster().setOp(false);
        }
        if(mageSpellsManager.levelingManager.isLevelingEnabled()) {
            if (livingEntity.isDead()) {
                PlayerObject playerObject = mageSpellsManager.mageManager.getMage(activeSpellObject.getCaster().getUniqueId());
                mageSpellsManager.levelingManager.giveExperience(playerObject, livingEntity.getType());
            }
        }
    }
    public void damageArmor(Player player){
        for (ItemStack is : player.getInventory().getArmorContents()) {
            if (is != null) {
                if (is.getDurability() + 1 > is.getType().getMaxDurability()) {
                    is.setType(Material.AIR);
                } else {
                    is.setDurability((short) (is.getDurability() + 1));
                }
            }
        }
    }
    public double calculateActualDamage(LivingEntity livingEntity, double damage, int armorpen){
        if(livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            double reduction = getDamageReduced(player);
            damageArmor(player);
            double penreduction = reduction * (armorpen * 0.01);
            reduction = reduction - penreduction;
            return damage -(damage*reduction);
        }else{
            return damage;
        }
    }
    public double getDamageReduced(Player player) {
        //returns percentage
        org.bukkit.inventory.PlayerInventory inv = player.getInventory();
        ItemStack boots = inv.getBoots();
        ItemStack helmet = inv.getHelmet();
        ItemStack chest = inv.getChestplate();
        ItemStack pants = inv.getLeggings();
        double red = 0.0;
        if(helmet != null) {
            if (helmet.getType() == Material.LEATHER_HELMET) red = red + 0.04;
            else if (helmet.getType() == Material.GOLD_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.CHAINMAIL_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.IRON_HELMET) red = red + 0.08;
            else if (helmet.getType() == Material.DIAMOND_HELMET) red = red + 0.12;
        }
        //
        if(boots!= null) {
            if (boots.getType() == Material.LEATHER_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.GOLD_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.CHAINMAIL_BOOTS) red = red + 0.04;
            else if (boots.getType() == Material.IRON_BOOTS) red = red + 0.08;
            else if (boots.getType() == Material.DIAMOND_BOOTS) red = red + 0.12;
        }
        //
        if(pants != null) {
            if (pants.getType() == Material.LEATHER_LEGGINGS) red = red + 0.08;
            else if (pants.getType() == Material.GOLD_LEGGINGS) red = red + 0.12;
            else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) red = red + 0.16;
            else if (pants.getType() == Material.IRON_LEGGINGS) red = red + 0.20;
            else if (pants.getType() == Material.DIAMOND_LEGGINGS) red = red + 0.24;
        }
        //
        if(chest != null) {
            if (chest.getType() == Material.LEATHER_CHESTPLATE) red = red + 0.12;
            else if (chest.getType() == Material.GOLD_CHESTPLATE) red = red + 0.20;
            else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) red = red + 0.20;
            else if (chest.getType() == Material.IRON_CHESTPLATE) red = red + 0.24;
            else if (chest.getType() == Material.DIAMOND_CHESTPLATE) red = red + 0.32;
        }
        return red;
    }
}
