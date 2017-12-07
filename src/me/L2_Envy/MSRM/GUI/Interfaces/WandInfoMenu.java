package me.L2_Envy.MSRM.GUI.Interfaces;

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

public class WandInfoMenu extends UserInterface {
    private WandObject wandObject;
    private static final int[][] Layout =
            {{1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,2,0,0,0,1},
                    {1,0,8,0,3,0,9,0,1},
                    {1,0,0,0,4,0,0,0,1},
                    {1,0,0,5,6,7,0,14,1},
                    {1,1,1,1,1,1,1,1,1}};
    public WandInfoMenu(Player player, WandObject wand){
        super(player);
        this.wandObject = wand;
    }
    @Override
    public Inventory loadInventory() {
        Inventory inventory = Bukkit.createInventory(null,54, ChatColor.BLUE + "Wand: " + wandObject.getDisplayname());
        if(Main.getMageSpellsManager().mageManager.isMage(getPlayer())){
            PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId());
            ItemStack[] contents = new ItemStack[54];
            for(int i = 0; i <6; i++){
                for(int j = 0; j < 9; j++){
                    int s = ((i*8)+i) + j;
                    String lore = "";
                    switch (Layout[i][j]){
                        case 1:
                            contents[s] = Utils.getItemStack("STAINED_GLASS_PANE-15");
                            break;
                        case 2:
                            contents[s] = Utils.getItemStack("BLAZE_ROD", wandObject.getDisplayname());
                            break;
                        case 3:
                            if(canSee(playerObject, getPlayer(), wandObject)){
                                lore += "&6Required level to bind: "+ wandObject.getRequiredleveltobind()+"/";
                                lore += "&6Required level to use: "+ wandObject.getRequiredleveltouse()+"/";
                                lore += "&6Required level to craft: "+ wandObject.getRequiredleveltocraft()+"/";
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 4:
                            if(canSee(playerObject, getPlayer(), wandObject)){
                                lore = lore + ("&6[1][2][3]/");
                                lore = lore + ("&6[4][5][6]/");
                                lore = lore + ("&6[7][8][9]/");
                                int count = 1;
                                for (String sh : wandObject.getShapedRecipe().getShape()) {
                                    for (int k = 0; k < 3; k++) {
                                        for (Character c : wandObject.getShapedRecipe().getIngredientMap().keySet()) {
                                            if (c.equals(sh.charAt(k))) {
                                                ItemStack is = wandObject.getShapedRecipe().getIngredientMap().get(c);
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
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("WORKBENCH", "&6Crafting Info:", lore);
                            break;
                        case 5:
                            if(getPlayer().hasPermission("magespells.admin") || getPlayer().isOp()){
                                contents[s] = Utils.getItemStack("BOOK", "&6Spawn Wand:", ChatColor.GOLD +"Spawn this wand in your inventory.");
                            }
                            break;
                        case 6:
                            if(getPlayer().hasPermission("magespells.admin") || getPlayer().isOp()){
                                contents[s] = Utils.getItemStack("BOOK", "&6Learn This Wand:", ChatColor.GOLD +"Teach yourself this wand.");
                            }
                            break;
                        case 7:
                            if(getPlayer().hasPermission("magespells.admin") || getPlayer().isOp()){
                                contents[s] = Utils.getItemStack("BOOK", "&6Learn All Wands:", ChatColor.GOLD +"Teaches this wand and all other wands.");
                            }
                            break;
                        case 8:
                            if(canSee(playerObject, getPlayer(), wandObject)){
                                if(wandObject.isMobdropsenabled()){
                                    for(EntityType e : wandObject.getMobDrops().keySet()){
                                        lore += "&6" + e + ": " + wandObject.getMobDrops().get(e) + "%/";
                                    }
                                }else{
                                    lore += "&6This wand does not drop from any monsters!"+"/";
                                }

                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("MONSTER_EGG", "&6Mob Drop Chances:", lore);
                            break;
                        case 9:
                            if(canSee(playerObject, getPlayer(), wandObject)){
                                for(SpellObject spellObject : Main.getMageSpellsManager().spellManager.getSpellObjects()){
                                    if(wandObject.isSpellCompatible(spellObject.getSpellNode())){
                                        lore += spellObject.getDisplayname() + "/";
                                    }
                                }
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = Utils.getItemStack("NETHER_STAR", "&6Compatible Spells:", lore);
                            break;
                        case 14:
                            contents[s] = Utils.getItemStack("REDSTONE_BLOCK", "&cBack");
                            break;
                    }
                }
            }
            inventory.setContents(contents);
        }
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {

    }
    private boolean canSee(PlayerObject playerObject, Player player, WandObject wandObject){
        return (playerObject.getLevel() >= wandObject.getRequiredleveltouse() || !Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                && (!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))
                && (!Main.getMageSpellsManager().isNodeSystemEnabled() || (player.hasPermission("magespells.wand." + wandObject.getWandnode())));
    }
}
