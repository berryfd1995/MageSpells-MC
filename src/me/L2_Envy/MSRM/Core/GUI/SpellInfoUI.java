package me.L2_Envy.MSRM.Core.GUI;

import it.unimi.dsi.fastutil.Hash;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 1/23/2017.
 */
public class SpellInfoUI {
    private MageSpellsManager mageSpellsManager;
    private HashMap<String, SpellObject> playersinmenu;
    private static final int[][] layout =
                    {{1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,2,0,0,0,1},
                    {1,0,8,12,3,13,10,0,1},
                    {1,0,9,0,4,0,11,0,1},
                    {1,0,0,5,6,7,0,14,1},
                    {1,1,1,1,1,1,1,1,1}};
    public SpellInfoUI(){
        playersinmenu = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void closeSpellInfoUI(Player player){
        if(playersinmenu.containsKey(player.getName())){
            playersinmenu.remove(player.getName());
        }
    }
    public boolean inSpellInfoUI(Player player){
        return playersinmenu.containsKey(player.getName());
    }
    public void openSpellInfoUI(Player player, SpellObject spellObject){
        player.openInventory(loadInventory(player, spellObject));
        playersinmenu.put(player.getName(), spellObject);
        //Get Player Data
    }
    public SpellObject getSpellObject(Player player){
        if(inSpellInfoUI(player)){
            return playersinmenu.get(player);
        }else{
            return null;
        }
    }
    private Inventory loadInventory(Player player, SpellObject spellObject){
        Inventory inventory = Bukkit.createInventory(null,54, ChatColor.BLUE + "Spell: " + spellObject.getDisplayname());
        if(mageSpellsManager.mageManager.isMage(player)){
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            //Setup inventory
            /*
                -compatible wands
                -spell info like damage, armour piercing
                -effects
                -
             */
                ItemStack[] contents = new ItemStack[54];
                for(int i = 0; i <6; i++){
                    for(int j = 0; j < 9; j++){
                        int s = ((i*8)+i) + j;
                        String lore = "";
                        switch (layout[i][j]){
                            case 1:
                                contents[s] = mageSpellsManager.main.utils.getItemStack("STAINED_GLASS_PANE-15");
                                break;
                            case 2:
                                contents[s] = mageSpellsManager.main.utils.getItemStack("NETHER_STAR", spellObject.getDisplayname(), spellObject.getLore());
                                break;
                            case 3:
                                if(playerObject.knowsSpell(spellObject)){
                                    if(spellObject.isAffectenemys()){
                                        lore += "Affects Enemys/";
                                    }
                                    if(spellObject.isAffectmobs()){
                                        lore += "Affects Mobs/";
                                    }
                                    if(spellObject.isAffectself()){
                                        lore += "Affects Self/";
                                    }
                                    if(spellObject.isAffectteammates()){
                                        lore += "Affects Teammates/";
                                    }
                                    if(mageSpellsManager.levelingManager.isLevelingEnabled()){
                                        lore += "Required Level to Bind: " + spellObject.getRequiredLevelToBind() + "/";
                                        lore += "Required Level to Cast: " + spellObject.getRequiredLevelToCast() + "/";
                                        lore += "Required Level to Drop: " + spellObject.getRequiredLevelToDrop() + "/";
                                    }
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                                break;
                            case 4:
                                if(playerObject.knowsSpell(spellObject)){
                                    if(spellObject.isBoltenabled()){
                                        lore += "Bolt Enabled/Damage Radius: " + spellObject.getBoltradius() + "/";
                                        lore += "Bolt Damage: " + spellObject.getBoltdamage() + "/";
                                    }
                                    if(spellObject.isSprayenabled()){
                                        lore += "Spray Enabled/Spray Radius: " + spellObject.getSprayradius() + "/";
                                        lore += "Spray Damage: " + spellObject.getSpraydamage() + "/";
                                    }
                                    if(spellObject.isAuraenabled()){
                                        lore += "Aura Enabled/Aura Radius: " + spellObject.getAuraradius() + "/";
                                        lore += "Aura Damage: " + spellObject.getAuradamage() + "/Duration: " + spellObject.getAuratime() + "/";
                                    }
                                    lore += "Armor Penetration: " + spellObject.getArmorpiercing() + "%/";
                                    lore += "Travel Distance: "+spellObject.getTraveldistance()+"/";
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("TNT", "&6Stats:", lore);
                                break;
                            case 5:
                                if(player.hasPermission("magespells.admin") || player.isOp()){
                                    contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Spawn SpellBook:", "Spawn a spell book in your inventory.");
                                }
                                break;
                            case 6:
                                if(player.hasPermission("magespells.admin") || player.isOp()){
                                    contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Learn This Spell:", "Teach yourself this spell.");
                                }
                                break;
                            case 7:
                                if(player.hasPermission("magespells.admin") || player.isOp()){
                                    contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Learn All Spells:", "Teaches this spell and all other spells.");
                                }
                                break;
                            case 8:
                                if(playerObject.knowsSpell(spellObject)){
                                    if(spellObject.isMobdropsenabled()){
                                        for(EntityType e : spellObject.getMobDrops().keySet()){
                                            lore += e + ": " + spellObject.getMobDrops().get(e) + "%/";
                                        }
                                    }else{
                                        lore += "This spell does not drop from any monsters!"+"/";
                                    }

                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("MONSTER_EGG", "&6Mob Drop Chances:", lore);
                                break;
                            case 9:
                                if(playerObject.knowsSpell(spellObject)){
                                    if(spellObject.isItemcostenabled()){
                                        for(ItemStack itemStack : spellObject.getItemcost().keySet()){
                                            if(itemStack.hasItemMeta()){
                                                if(itemStack.getItemMeta().hasDisplayName()){
                                                    lore += itemStack.getItemMeta().getDisplayName() + " x" + spellObject.getItemcost().get(itemStack) + "/";
                                                }else{
                                                    lore += ItemNames.lookup(itemStack) + " x" + spellObject.getItemcost().get(itemStack) + "/";
                                                }
                                            }else{
                                                lore += ItemNames.lookup(itemStack) + " x" + spellObject.getItemcost().get(itemStack) + "/";
                                            }
                                        }
                                    }else{
                                        lore += "This spell does not require anything/";
                                    }
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("GOLD_NUGGET", "&6Required Items: ", lore);
                                break;
                            case 10:
                                if(playerObject.knowsSpell(spellObject)){
                                    lore += "Mana Cost: " + spellObject.getManacost() + "/";
                                    lore += "Cooldown Time: " + spellObject.getCooldown() + "s/";
                                    lore += "Charge Time: " + spellObject.getChargetime() + "s/";
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("EXP_BOTTLE", "&6Casting Info:", lore);
                                break;
                            case 11:
                                if(playerObject.knowsSpell(spellObject)){
                                    for(PotionEffect potionEffect : spellObject.getPotionEffects()){
                                        lore += "Type: "+potionEffect.getType() + "/";
                                        lore += "   Duration: "+potionEffect.getDuration() + "/";
                                        lore += "   Amplification: "+potionEffect.getAmplifier() + "/";
                                    }
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("POTION", "&6Potion Effects:", lore);
                                break;
                            case 12:
                                if(playerObject.knowsSpell(spellObject)){
                                    if(mageSpellsManager.isNodeSystemEnabled()) {
                                        for (WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()) {
                                            if(wandObject.isSpellCompatible(spellObject.getSpellNode())){
                                                lore += wandObject.getDisplayname() + "/";
                                            }
                                        }
                                    }else{
                                        lore += "This spell works with any wand";
                                    }
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("STICK", "&6Wand Compatibility:", lore);
                                break;
                            case 13:
                                if(playerObject.knowsSpell(spellObject)){
                                    lore += spellObject.getSpellEffect();
                                }else{
                                    lore = "&6Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("BLAZE_POWDER", "&6Special Effect:", lore);
                                break;
                            case 14:
                                contents[s] = mageSpellsManager.main.utils.getItemStack("REDSTONE_BLOCK", "&cBack");
                                break;
                        }
                    }
                }
                inventory.setContents(contents);
        }
        return inventory;
    }
    public Inventory loadInventory(Player player, WandObject wandObject){
        if(mageSpellsManager.mageManager.isMage(player)){
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            //Setup inventory
            /*
                -compatible wands
                -spell info like damage, armour piercing
                -effects
                -
             */
            if(playerObject.knowsWand(wandObject)){

            }else{

            }
            //if admin
            if(player.hasPermission("")){

            }
        }
        return null;
    }
}
