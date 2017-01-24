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
                    {1,0,8,12,3,13,10,0,1},
                    {1,0,9,0,4,0,11,0,1},
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
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 4:
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
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
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 9:
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 10:
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 11:
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 12:
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
                            break;
                        case 13:
                            if(playerObject.knowsWand(wandObject)){
                            }else{
                                lore = "&6Unknown";
                            }
                            contents[s] = mageSpellsManager.main.utils.getItemStack("ENCHANTMENT_TABLE", "&6Info:", lore);
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
}
