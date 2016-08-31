package me.L2_Envy.MSRM.Core.Handlers;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import me.L2_Envy.MSRM.Core.GUI.PlayerInterface;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 8/8/2016.
 */
public class PlayerHandler implements Listener{
    public MageSpellsManager mageSpellsManager;
    public PlayerHandler(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    @EventHandler
    public void loadPlayerData(PlayerJoinEvent event){
        if(event.getPlayer().hasPermission("magespells.mage")) {
            mageSpellsManager.main.pluginManager.playerConfig.loadPlayerData(event.getPlayer().getName(), event.getPlayer().getUniqueId());
        }
    }
    @EventHandler
    public void savePlayerData(PlayerQuitEvent event){
        if(event.getPlayer().hasPermission("magespells.mage")) {
            if (mageSpellsManager.main.mageSpellsManager.mageManager.isMage(event.getPlayer())) {
                PlayerObject playerObject = mageSpellsManager.mageManager.getMage(event.getPlayer().getUniqueId());
                mageSpellsManager.manaManager.removeManaTask(playerObject);
                mageSpellsManager.main.pluginManager.playerConfig.savePlayerData(mageSpellsManager.main.mageSpellsManager.mageManager.getMage(event.getPlayer().getUniqueId()));
            }
        }
    }
    @EventHandler
    public void spellBookLearning(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if(player.getInventory().getItemInMainHand().equals(itemStack)) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(mageSpellsManager.spellLearningManager.isSpellBookLearningEnabled()){
                    SpellObject spellObject = mageSpellsManager.spellBookManager.getSpellFromBook(itemStack);
                    if(spellObject != null){
                        if(mageSpellsManager.mageManager.isMage(player)) {
                            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
                            if(mageSpellsManager.spellLearningManager.learnSpell(playerObject,spellObject)){
                                //take spellbook
                                boolean tookspellbook = false;
                                Inventory inventory = player.getInventory();
                                ItemStack[] itemStacks = inventory.getContents();
                                for(int i = 0; i < itemStacks.length; i++) {
                                    if (itemStacks[i] != null) {
                                        ItemStack itemStack1 = itemStacks[i];
                                        if (!tookspellbook) {
                                            if (mageSpellsManager.spellBookManager.isSameSpellBookIgnoreAmount(itemStack1, spellObject.getSpellbook())) {
                                                if (itemStack1.getAmount() == 1) {
                                                    itemStacks[i] = new ItemStack(Material.AIR);
                                                    tookspellbook = true;
                                                } else {
                                                    itemStack1.setAmount(itemStack1.getAmount() - 1);
                                                    tookspellbook = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                player.getInventory().setContents(itemStacks);
                                player.updateInventory();
                            }
                        }
                    }
                }else if(mageSpellsManager.wandManager.isWandLearningEnabled()){
                    WandObject wandObject = mageSpellsManager.wandManager.getWandFromItem(itemStack);
                    if(wandObject != null){
                        if(mageSpellsManager.mageManager.isMage(player)){
                            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
                            if(mageSpellsManager.spellLearningManager.learnWand(playerObject,wandObject)){
                                boolean tookwanditem = false;
                                Inventory inventory = player.getInventory();
                                ItemStack[] itemStacks = inventory.getContents();
                                for(int i = 0; i < itemStacks.length; i++){
                                    if(itemStacks[i] != null){
                                        ItemStack itemStack1 = itemStacks[i];
                                        if(!tookwanditem) {
                                            if (mageSpellsManager.wandManager.isSameWandIgnoreAmount(itemStack, itemStack1)) {
                                                if (itemStack.getAmount() == 1) {
                                                    itemStacks[i] = new ItemStack(Material.AIR);
                                                    tookwanditem = true;
                                                } else {
                                                    itemStack.setAmount(itemStack.getAmount() - 1);
                                                    tookwanditem = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                player.getInventory().setContents(itemStacks);
                                player.updateInventory();
                            }
                        }
                    }
                }
            }
        }
    }
}
