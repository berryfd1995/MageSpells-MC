package me.L2_Envy.MSRM.Core.GUI;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;

/**
 * Created by berry on 1/24/2017.
 */
public class WandInfoUI {
    private MageSpellsManager mageSpellsManager;
    private HashMap<String, WandObject> playersinmenu;
    private static final int[][] layout =
                    {{1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,2,0,0,0,1},
                    {1,0,8,0,3,0,9,0,1},
                    {1,0,0,0,4,0,0,0,1},
                    {1,0,0,5,6,7,0,14,1},
                    {1,1,1,1,1,1,1,1,1}};
    public WandInfoUI(){
        playersinmenu = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void closeWandInfoUI(Player player){
        if(playersinmenu.containsKey(player.getName())){
            playersinmenu.remove(player.getName());
        }
    }
    public boolean inWandInfoUI(Player player){
        return playersinmenu.containsKey(player.getName());
    }
    public void openWandInfoUI(Player player, WandObject wandObject){
        player.openInventory(loadInventory(player, wandObject));
        playersinmenu.put(player.getName(), wandObject);
        //Get Player Data
    }
    public WandObject getWandObject(Player player){
        if(inWandInfoUI(player)){
            return playersinmenu.get(player.getName());
        }else{
            return null;
        }
    }
    private Inventory loadInventory(Player player, WandObject wandObject){
        Inventory inventory = Bukkit.createInventory(null,54, ChatColor.BLUE + "Wand: " + wandObject.getDisplayname());
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
                            contents[s] = mageSpellsManager.main.utils.getItemStack("BLAZE_ROD", wandObject.getDisplayname());
                            break;
                        case 3:
                            if(canSee(playerObject, player, wandObject)){
                                lore += "&6Required level to bind: "+ wandObject.getRequiredleveltobind()+"/";
                                lore += "&6Required level to use: "+ wandObject.getRequiredleveltouse()+"/";
                                lore += "&6Required level to craft: "+ wandObject.getRequiredleveltocraft()+"/";
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 4:
                            if(canSee(playerObject, player, wandObject)){
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
                            contents[s] = mageSpellsManager.main.utils.getItemStack("WORKBENCH", "&6Crafting Info:", lore);
                            break;
                        case 5:
                            if(player.hasPermission("magespells.admin") || player.isOp()){
                                contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Spawn Wand:", ChatColor.GOLD +"Spawn this wand in your inventory.");
                            }
                            break;
                        case 6:
                            if(player.hasPermission("magespells.admin") || player.isOp()){
                                contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Learn This Wand:", ChatColor.GOLD +"Teach yourself this wand.");
                            }
                            break;
                        case 7:
                            if(player.hasPermission("magespells.admin") || player.isOp()){
                                contents[s] = mageSpellsManager.main.utils.getItemStack("BOOK", "&6Learn All Wands:", ChatColor.GOLD +"Teaches this wand and all other wands.");
                            }
                            break;
                        case 8:
                            if(canSee(playerObject, player, wandObject)){
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
                            contents[s] = mageSpellsManager.main.utils.getItemStack("MONSTER_EGG", "&6Mob Drop Chances:", lore);
                            break;
                        case 9:
                            if(canSee(playerObject, player, wandObject)){
                                for(SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()){
                                    if(wandObject.isSpellCompatible(spellObject.getSpellNode())){
                                        lore += spellObject.getDisplayname() + "/";
                                    }
                                }
                            }else{
                                lore = "Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("NETHER_STAR", "&6Compatible Spells:", lore);
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
    private boolean canSee(PlayerObject playerObject, Player player, WandObject wandObject){
        return (playerObject.getLevel() >= wandObject.getRequiredleveltouse() || !mageSpellsManager.levelingManager.isLevelingEnabled())
                && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))
                && (!mageSpellsManager.isNodeSystemEnabled() || (player.hasPermission("magespells.wand." + wandObject.getWandnode())));
    }
}
