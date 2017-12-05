package me.L2_Envy.MSRM.Core.GUI;

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
                    {1,0,0,0,2,0,0,15,1},
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
            return playersinmenu.get(player.getName());
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
                                if(canSee(playerObject,player,spellObject)){
                                    if(spellObject.isAffectenemys()){
                                        lore += "&6Affects Enemys/";
                                    }
                                    if(spellObject.isAffectmobs()){
                                        lore += "&6Affects Mobs/";
                                    }
                                    if(spellObject.isAffectself()){
                                        lore += "&6Affects Self/";
                                    }
                                    if(spellObject.isAffectteammates()){
                                        lore += "&6Affects Teammates/";
                                    }
                                    if(mageSpellsManager.levelingManager.isLevelingEnabled()){
                                        lore += "&6Required Level to Bind: " + spellObject.getRequiredLevelToBind() + "/";
                                        lore += "&6Required Level to Cast: " + spellObject.getRequiredLevelToCast() + "/";
                                        lore += "&6Required Level to Drop: " + spellObject.getRequiredLevelToDrop() + "/";
                                    }
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                                break;
                            case 4:
                                if(canSee(playerObject,player,spellObject)){
                                    if(spellObject.isBoltenabled()){
                                        lore += "&6Bolt Enabled/   &6Damage Radius: " + spellObject.getBoltradius() + "/";
                                        lore += "   &6Bolt Damage: " + spellObject.getBoltdamage() + "/";
                                    }
                                    if(spellObject.isSprayenabled()){
                                        lore += "&6Spray Enabled/   &6Spray Radius: " + spellObject.getSprayradius() + "/";
                                        lore += "   &6Spray Damage: " + spellObject.getSpraydamage() + "/";
                                    }
                                    if(spellObject.isAuraenabled()){
                                        lore += "&6Aura Enabled/   &6Aura Radius: " + spellObject.getAuraradius() + "/";
                                        lore += "   &6Aura Damage: " + spellObject.getAuradamage() + "/&6Duration: " + spellObject.getAuratime() + "/";
                                    }
                                    lore += "&6Armor Penetration: " + spellObject.getArmorpiercing() + "%/";
                                    lore += "&6Travel Distance: "+spellObject.getTraveldistance()+"/";
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("TNT", "&6Stats:", lore);
                                break;
                            case 5:
                                if(player.hasPermission("magespells.admin") || player.isOp()){
                                    contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Spawn SpellBook:", ChatColor.GOLD +"Spawn a spell book in your inventory.");
                                }
                                break;
                            case 6:
                                if(player.hasPermission("magespells.admin") || player.isOp()){
                                    contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Learn This Spell:", ChatColor.GOLD +"Teach yourself this spell.");
                                }
                                break;
                            case 7:
                                if(player.hasPermission("magespells.admin") || player.isOp()){
                                    contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Learn All Spells:", ChatColor.GOLD +"Teaches this spell and all other spells.");
                                }
                                break;
                            case 8:
                                if(canSee(playerObject,player,spellObject)){
                                    if(spellObject.isMobdropsenabled()){
                                        for(EntityType e : spellObject.getMobDrops().keySet()){
                                            lore += "&6" + e + ": " + spellObject.getMobDrops().get(e) + "%/";
                                        }
                                    }else{
                                        lore += "&6This spell does not drop from any monsters!"+"/";
                                    }

                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("MONSTER_EGG", "&6Mob Drop Chances:", lore);
                                break;
                            case 9:
                                if(canSee(playerObject,player,spellObject)){
                                    if(spellObject.isItemcostenabled()){
                                        for(ItemStack itemStack : spellObject.getItemcost().keySet()){
                                            if(itemStack.hasItemMeta()){
                                                if(itemStack.getItemMeta().hasDisplayName()){
                                                    lore += "&6" + itemStack.getItemMeta().getDisplayName() + " x" + spellObject.getItemcost().get(itemStack) + "/";
                                                }else{
                                                    lore += "&6" + ItemNames.lookup(itemStack) + " x" + spellObject.getItemcost().get(itemStack) + "/";
                                                }
                                            }else{
                                                lore += "&6" + ItemNames.lookup(itemStack) + " x" + spellObject.getItemcost().get(itemStack) + "/";
                                            }
                                        }
                                    }else{
                                        lore += "&6This spell does not require anything/";
                                    }
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("GOLD_NUGGET", "&6Required Items: ", lore);
                                break;
                            case 10:
                                if(canSee(playerObject,player,spellObject)){
                                    lore += "&6Mana Cost: " + spellObject.getManacost() + "/";
                                    lore += "&6Cooldown Time: " + spellObject.getCooldown() + "s/";
                                    lore += "&6Charge Time: " + spellObject.getChargetime() + "s/";
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("EXP_BOTTLE", "&6Casting Info:", lore);
                                break;
                            case 11:
                                if(canSee(playerObject,player,spellObject)){
                                    for(PotionEffect potionEffect : spellObject.getPotionEffects()){
                                        lore += "&6Type: "+potionEffect.getType().getName() + "/";
                                        lore += "   &6Duration: "+potionEffect.getDuration()/20 + "/";
                                        lore += "   &6Amplification: "+potionEffect.getAmplifier() + "/";
                                    }
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("POTION", "&6Potion Effects:", lore);
                                break;
                            case 12:
                                if(canSee(playerObject,player,spellObject)){
                                    if(mageSpellsManager.isNodeSystemEnabled()) {
                                        for (WandObject wandObject : mageSpellsManager.wandManager.getWandObjects()) {
                                            if(wandObject.isSpellCompatible(spellObject.getSpellNode())){
                                                lore += wandObject.getDisplayname() + "/";
                                            }
                                        }
                                    }else{
                                        lore += "&6This spell works with any wand";
                                    }
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("STICK", "&6Wand Compatibility:", lore);
                                break;
                            case 13:
                                if(canSee(playerObject,player,spellObject)){
                                    lore += ChatColor.GOLD +spellObject.getSpellEffect().toUpperCase();
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("BLAZE_POWDER", ChatColor.GOLD +"Special Effect:", lore);
                                break;
                            case 14:
                                contents[s] = mageSpellsManager.main.utils.getItemStack("REDSTONE_BLOCK", "&cBack");
                                break;

                            case 15:
                                if(canSee(playerObject, player, spellObject)){
                                    if(spellObject.isCraftingenabled()) {
                                        lore = lore + ("&6[1][2][3]/");
                                        lore = lore + ("&6[4][5][6]/");
                                        lore = lore + ("&6[7][8][9]/");
                                        int count = 1;
                                        for (String sh : spellObject.getShapedRecipe().getShape()) {
                                            for (int k = 0; k < 3; k++) {
                                                for (Character c : spellObject.getShapedRecipe().getIngredientMap().keySet()) {
                                                    if (c.equals(sh.charAt(k))) {
                                                        ItemStack is = spellObject.getShapedRecipe().getIngredientMap().get(c);
                                                        if (is != null) {
                                                            if (is.hasItemMeta()) {
                                                                if (is.getItemMeta().hasDisplayName()) {
                                                                    lore = lore + ("&6" + count + ": &b" + is.getItemMeta().getDisplayName() + "/");
                                                                } else {
                                                                    lore = lore + ("&6" + count + ": &b" + ItemNames.lookup(is) + "/");
                                                                }
                                                            } else {
                                                                lore = lore + ("&6" + count + ": &b" + ItemNames.lookup(is) + "/");
                                                            }
                                                            count++;
                                                        } else {
                                                            lore = lore + ("&6" + count + ": &bNothing/");
                                                            count++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        lore = "This spell is uncraftable!";
                                    }
                                }else{
                                    lore = "Unknown";
                                }
                                contents[s] = mageSpellsManager.main.utils.getItemStack("WORKBENCH", "&6Crafting Info:", lore);
                        }
                    }
                }
                inventory.setContents(contents);
        }
        return inventory;
    }
    private boolean canSee(PlayerObject playerObject, Player player, SpellObject spellObject){
        return ((playerObject.getLevel() >= spellObject.getRequiredLevelToCast() || !mageSpellsManager.levelingManager.isLevelingEnabled())
                && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))
                && (!mageSpellsManager.isNodeSystemEnabled() || player.hasPermission("magespells.spell." + spellObject.getSpellNode())));
    }
}
