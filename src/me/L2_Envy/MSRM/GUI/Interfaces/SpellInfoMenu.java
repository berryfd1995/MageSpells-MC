package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import me.L2_Envy.MSRM.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class SpellInfoMenu extends UserInterface {
    private static final int[][] layout =
            {{1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,2,0,0,15,1},
                    {1,0,8,12,3,13,10,0,1},
                    {1,0,9,0,4,0,11,0,1},
                    {1,0,0,5,6,7,0,14,1},
                    {1,1,1,1,1,1,1,1,1}};
    private SpellObject spellObject;
    public SpellInfoMenu(Player player, SpellObject spell){
        super(player);
        this.spellObject = spell;
    }
    @Override
    public Inventory loadInventory() {
        Inventory inventory = Bukkit.createInventory(null,54, ChatColor.BLUE + "Spell: " + spellObject.getDisplayname());
        if(Main.getMageSpellsManager().mageManager.isMage(getPlayer())){
            PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId());
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
                            contents[s] = Main.getMageSpellsManager().main.utils.getItemStack("STAINED_GLASS_PANE-15");
                            break;
                        case 2:
                            contents[s] = Main.getMageSpellsManager().main.utils.getItemStack("NETHER_STAR", spellObject.getDisplayname(), spellObject.getLore());
                            break;
                        case 3:
                            if(canSee(playerObject,getPlayer(),spellObject)){
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
                                if(Main.getMageSpellsManager().levelingManager.isLevelingEnabled()){
                                    lore += "&6Required Level to Bind: " + spellObject.getRequiredLevelToBind() + "/";
                                    lore += "&6Required Level to Cast: " + spellObject.getRequiredLevelToCast() + "/";
                                    lore += "&6Required Level to Drop: " + spellObject.getRequiredLevelToDrop() + "/";
                                }
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 4:
                            if(canSee(playerObject,getPlayer(),spellObject)){
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
                            contents[s] = Utils.getItemStack("TNT", "&6Stats:", lore);
                            break;
                        case 5:
                            if(getPlayer().hasPermission("magespells.admin") || getPlayer().isOp()){
                                contents[s] = Utils.getItemStack("BOOK", "&6Spawn SpellBook:", ChatColor.GOLD +"Spawn a spell book in your inventory.");
                            }
                            break;
                        case 6:
                            if(getPlayer().hasPermission("magespells.admin") || getPlayer().isOp()){
                                contents[s] = Utils.getItemStack("BOOK", "&6Learn This Spell:", ChatColor.GOLD +"Teach yourself this spell.");
                            }
                            break;
                        case 7:
                            if(getPlayer().hasPermission("magespells.admin") || getPlayer().isOp()){
                                contents[s] = Utils.getItemStack("BOOK", "&6Learn All Spells:", ChatColor.GOLD +"Teaches this spell and all other spells.");
                            }
                            break;
                        case 8:
                            if(canSee(playerObject,getPlayer(),spellObject)){
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
                            contents[s] = Utils.getItemStack("MONSTER_EGG", "&6Mob Drop Chances:", lore);
                            break;
                        case 9:
                            if(canSee(playerObject,getPlayer(),spellObject)){
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
                            contents[s] = Utils.getItemStack("GOLD_NUGGET", "&6Required Items: ", lore);
                            break;
                        case 10:
                            if(canSee(playerObject,getPlayer(),spellObject)){
                                lore += "&6Mana Cost: " + spellObject.getManacost() + "/";
                                lore += "&6Cooldown Time: " + spellObject.getCooldown() + "s/";
                                lore += "&6Charge Time: " + spellObject.getChargetime() + "s/";
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("EXP_BOTTLE", "&6Casting Info:", lore);
                            break;
                        case 11:
                            if(canSee(playerObject,getPlayer(),spellObject)){
                                for(PotionEffect potionEffect : spellObject.getPotionEffects()){
                                    lore += "&6Type: "+potionEffect.getType().getName() + "/";
                                    lore += "   &6Duration: "+potionEffect.getDuration()/20 + "/";
                                    lore += "   &6Amplification: "+potionEffect.getAmplifier() + "/";
                                }
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("POTION", "&6Potion Effects:", lore);
                            break;
                        case 12:
                            if(canSee(playerObject,getPlayer(),spellObject)){
                                if(Main.getMageSpellsManager().isNodeSystemEnabled()) {
                                    for (WandObject wandObject : Main.getMageSpellsManager().wandManager.getWandObjects()) {
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
                            contents[s] = Utils.getItemStack("STICK", "&6Wand Compatibility:", lore);
                            break;
                        case 13:
                            if(canSee(playerObject,getPlayer(),spellObject)){
                                lore += ChatColor.GOLD +spellObject.getSpellEffect().toUpperCase();
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("BLAZE_POWDER", ChatColor.GOLD +"Special Effect:", lore);
                            break;
                        case 14:
                            contents[s] = Utils.getItemStack("REDSTONE_BLOCK", "&cBack");
                            break;

                        case 15:
                            if(canSee(playerObject, getPlayer(), spellObject)){
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
                            contents[s] = Utils.getItemStack("WORKBENCH", "&6Crafting Info:", lore);
                    }
                }
            }
            inventory.setContents(contents);
        }
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {
        if(getPlayer().hasPermission("magespells.admin")) {
            switch (slot) {
                case 39:
                    getPlayer().getInventory().addItem(spellObject.getSpellbook());
                    break;
                case 40:
                    Main.getMageSpellsManager().spellLearningManager.learnSpell(Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId()), spellObject);
                    getPlayer().sendMessage(ChatColor.GREEN + "You have learned the spell: " + spellObject.getDisplayname());
                    break;
                case 41:
                    for (SpellObject spellObject : Main.getMageSpellsManager().spellManager.getSpellObjects()) {
                        Main.getMageSpellsManager().spellLearningManager.learnSpell(Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId()), spellObject);
                    }
                    getPlayer().sendMessage(ChatColor.GREEN + "You have learned all spells!");
                    break;
            }
        }
        switch (slot){
            case 43:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(),new SpellListMenu(getPlayer(), 0));
                break;
        }
    }
    private boolean canSee(PlayerObject playerObject, Player player, SpellObject spellObject){
        return ((playerObject.getLevel() >= spellObject.getRequiredLevelToCast() || !Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                && (!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))
                && (!Main.getMageSpellsManager().isNodeSystemEnabled() || player.hasPermission("magespells.spell." + spellObject.getSpellNode())));
    }
}
