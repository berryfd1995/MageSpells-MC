package me.L2_Envy.MSRM.Core.Learning;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 7/24/2016.
 */
public class SpellLearningManager {
    public MageSpellsManager mageSpellsManager;
    private boolean enablelearning;

    public SpellLearningManager() {

    }

    public void link(MageSpellsManager mageSpellsManager) {
        this.mageSpellsManager = mageSpellsManager;
    }

    public boolean isLearningEnabled() {
        return enablelearning;
    }

    public void setEnablelearning(boolean enabled) {
        enablelearning = enabled;
    }

    public void learnSpell(Player sender,PlayerObject senderObj) {
        if (mageSpellsManager.spellBookManager.isSpellBook(sender.getInventory().getItemInMainHand())) {
            senderObj.teachSpell(mageSpellsManager.spellBookManager.getSpellFromBook(sender.getInventory().getItemInMainHand()));
        } else if (mageSpellsManager.spellBookManager.isSpellBook(sender.getInventory().getItemInOffHand())){
            senderObj.teachSpell(mageSpellsManager.spellBookManager.getSpellFromBook(sender.getInventory().getItemInOffHand()));
        }
    }

    public void learnSpell(Player sender, PlayerObject senderObj, String spellname) {
        SpellObject spellObject = mageSpellsManager.spellManager.getSpellFromName(spellname);
        if (spellObject != null) {
            senderObj.teachSpell(spellObject);
        } else {
            sender.sendMessage("Could not find spell: " + spellname);
        }
    }

    public void learnSpell(CommandSender sender, String spellname, String reciever){
        OfflinePlayer offlinePlayer = mageSpellsManager.main.utils.getOfflinePlayerFromName(reciever);
        if (offlinePlayer != null) {
            if (offlinePlayer.isOnline()) {
                Player player1 = (Player) offlinePlayer;
                PlayerObject playerObject1 =mageSpellsManager.mageManager.getMage(player1.getUniqueId());
                if (playerObject1 != null) {
                    SpellObject spellObject = mageSpellsManager.spellManager.getSpellFromName(spellname);
                    if (spellObject != null) {
                        playerObject1.teachSpell(spellObject);
                    } else {
                        sender.sendMessage("Could not find spell: " + spellname);
                    }
                } else {
                    sender.sendMessage("Could not find player: " + reciever);
                }
            } else {
                PlayerObject playerObject1 = mageSpellsManager.mageManager.loadOfflineMage(reciever);
                if (playerObject1 != null) {
                    SpellObject spellObject = mageSpellsManager.spellManager.getSpellFromName(spellname);
                    if (spellObject != null) {
                        playerObject1.teachSpell(spellObject);
                        mageSpellsManager.main.pluginManager.playerConfig.savePlayerData(playerObject1);
                    } else {
                        sender.sendMessage("Could not find spell: " + spellname);
                    }
                } else {
                    sender.sendMessage("Could not find player: " + reciever);
                }
            }
        } else {
            sender.sendMessage("Could not find player: " + reciever);
        }
    }
   //
   public void learnWand(Player sender,PlayerObject senderObj) {
       if (mageSpellsManager.wandManager.isWand(sender.getInventory().getItemInMainHand())) {
           senderObj.teachWand(mageSpellsManager.wandManager.getWandFromItem(sender.getInventory().getItemInMainHand()));
       } else if (mageSpellsManager.wandManager.isWand(sender.getInventory().getItemInOffHand())){
           senderObj.teachWand(mageSpellsManager.wandManager.getWandFromItem(sender.getInventory().getItemInOffHand()));
       }
   }

    public void learnWand(CommandSender sender, PlayerObject senderObj, String wandname) {
        WandObject wandObject = mageSpellsManager.wandManager.getWandFromName(wandname);
        if (wandObject != null) {
            senderObj.teachWand(wandObject);
        } else {
            sender.sendMessage("Could not find spell: " + wandname);
        }
    }

    public void learnWand(CommandSender sender,String wandname, String reciever){
        OfflinePlayer offlinePlayer = mageSpellsManager.main.utils.getOfflinePlayerFromName(reciever);
        if (offlinePlayer != null) {
            if (offlinePlayer.isOnline()) {
                Player player1 = (Player) offlinePlayer;
                PlayerObject playerObject1 =mageSpellsManager.mageManager.getMage(player1.getUniqueId());
                if (playerObject1 != null) {
                    WandObject wandObject = mageSpellsManager.wandManager.getWandFromName(wandname);
                    if (wandObject != null) {
                        playerObject1.teachWand(wandObject);
                    } else {
                        sender.sendMessage("Could not find spell: " + wandname);
                    }
                } else {
                    sender.sendMessage("Could not find player: " + reciever);
                }
            } else {
                PlayerObject playerObject1 = mageSpellsManager.mageManager.loadOfflineMage(reciever);
                if (playerObject1 != null) {
                    WandObject wandObject = mageSpellsManager.wandManager.getWandFromName(wandname);
                    if (wandObject != null) {
                        playerObject1.teachWand(wandObject);
                        mageSpellsManager.main.pluginManager.playerConfig.savePlayerData(playerObject1);
                    } else {
                        sender.sendMessage("Could not find spell: " + wandname);
                    }
                } else {
                    sender.sendMessage("Could not find player: " + reciever);
                }
            }
        } else {
            sender.sendMessage("Could not find player: " + reciever);
        }
    }
}
