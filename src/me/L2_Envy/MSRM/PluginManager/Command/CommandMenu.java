package me.L2_Envy.MSRM.PluginManager.Command;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.PluginManager.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 7/23/2016.
 */
public class CommandMenu implements CommandExecutor{
    public HelpMenu helpMenu;
    public PluginManager pluginManager;
    public CommandMenu(){
        helpMenu = new HelpMenu();
    }
    public void link(PluginManager pluginManager){
        this.pluginManager = pluginManager;
        helpMenu.link(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                try {
                    if (cmd.getName().equalsIgnoreCase("mage")) {
                        if (player.hasPermission("magespells.mage")) {
                            if (pluginManager.main.mageSpellsManager.mageManager.isMage(player)) {
                                PlayerObject playerObject = pluginManager.main.mageSpellsManager.mageManager.getMage(player.getUniqueId());
                                switch (args[0].toLowerCase()) {
                                    case "help":
                                        if (args.length == 2) {
                                            helpMenu.displayHelpMenu(player, Integer.parseInt(args[1]));
                                        } else {
                                            helpMenu.displayHelpMenu(player, 1);
                                        }
                                        break;
                                    case "menu":
                                        pluginManager.main.mageSpellsManager.playerInterface.openPlayerInterface(player);
                                        break;
                                    case "stats":
                                        pluginManager.main.mageSpellsManager.mageStats.openMageStats(player);
                                        break;
                                    case "spellmenu":
                                        pluginManager.main.mageSpellsManager.spellUI.openSpellUI(player);
                                        break;
                                    case "wandmenu":
                                        pluginManager.main.mageSpellsManager.wandUI.openWandUI(player);
                                        break;
                                    case "bag":
                                        pluginManager.main.mageSpellsManager.wandBag.openWandBag(player);
                                        break;
                                    case "bind":
                                        pluginManager.main.mageSpellsManager.bindingMenu.openSpellBindingMenu(player);
                                        break;
                                    case "learnspell":
                                        if(args.length >= 2){
                                            if(player.hasPermission("magespells.admin")) {
                                                if (args.length == 2) {
                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(player, playerObject, args[1]);
                                                } else if (args.length == 3) {
                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(player, args[2], args[1]);
                                                }else{
                                                    //invalid
                                                }
                                            }
                                        }else {
                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(player,playerObject);
                                        }
                                        break;
                                    case "learnwand":
                                        if(args.length >= 2){
                                            if(player.hasPermission("magespells.admin")) {
                                                if (args.length == 2) {
                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(player, playerObject, args[1]);
                                                } else if (args.length == 3) {
                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(player, args[2], args[1]);
                                                }else{
                                                    //invalid
                                                }
                                            }
                                        }else {
                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(player,playerObject);
                                        }
                                        break;
                                    case "addexperience":
                                        if(args.length == 3){
                                            if(player.hasPermission("magespells.admin")) {
                                                pluginManager.main.mageSpellsManager.levelingManager.giveExperience(player, args[1], Long.parseLong(args[2]));
                                            }
                                        }
                                        break;
                                    case "spawnspellbook":
                                        if(args.length >= 2){
                                            if(player.hasPermission("magespells.admin")) {
                                                SpellObject spellObject = pluginManager.main.mageSpellsManager.spellManager.getSpellFromName(args[1]);
                                                if(args.length > 2) {
                                                    Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                    if (spellObject != null && player1 != null) {
                                                        player1.getInventory().addItem(spellObject.getSpellbook());
                                                    }
                                                }else{
                                                    if (spellObject != null) {
                                                        player.getInventory().addItem(spellObject.getSpellbook());
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case "spawnwand":
                                        if(args.length >= 2){
                                            if(player.hasPermission("magespells.admin")) {
                                                WandObject wandObject = pluginManager.main.mageSpellsManager.wandManager.getWandFromName(args[1]);
                                                if(args.length > 2) {
                                                    Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                    if (wandObject != null && player1 != null) {
                                                        player1.getInventory().addItem(wandObject.getWandItemStack());
                                                    }
                                                }else{
                                                    if (wandObject != null) {
                                                        player.getInventory().addItem(wandObject.getWandItemStack());
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case "setprimaryspell":
                                        if(player.hasPermission("magespells.admin")) {
                                            if(args.length == 2){
                                                ItemStack itemStack = player.getInventory().getItemInMainHand();
                                                if(itemStack != null){
                                                    if(pluginManager.main.mageSpellsManager.wandManager.isWand(itemStack)){
                                                        SpellObject spellObject = pluginManager.main.mageSpellsManager.spellManager.getSpellFromName(args[1]);
                                                        if(spellObject != null){
                                                            player.getInventory().setItemInMainHand(pluginManager.main.mageSpellsManager.bindingManager.setPrimarySpell(itemStack, spellObject));
                                                            player.updateInventory();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case "setsecondaryspell":
                                        if(player.hasPermission("magespells.admin")) {
                                            if(player.hasPermission("magespells.admin")) {
                                                if(args.length == 2){
                                                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                                                    if(itemStack != null){
                                                        if(pluginManager.main.mageSpellsManager.wandManager.isWand(itemStack)){
                                                            SpellObject spellObject = pluginManager.main.mageSpellsManager.spellManager.getSpellFromName(args[1]);
                                                            if(spellObject != null){
                                                                player.getInventory().setItemInMainHand(pluginManager.main.mageSpellsManager.bindingManager.setSecondarySpell(itemStack, spellObject));
                                                                player.updateInventory();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case "team":
                                        if(pluginManager.main.mageSpellsManager.teamManager.getUsercreatesteam()) {
                                            if (args.length >= 2) {
                                                switch (args[1].toLowerCase()) {
                                                    case "create":
                                                        if(args.length == 4) {
                                                            Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[3]);
                                                            if (playerFromName != null) {
                                                                pluginManager.main.mageSpellsManager.teamManager.createTeam(args[2], player, playerFromName);
                                                            }
                                                        }
                                                        break;
                                                    case "leave":
                                                            pluginManager.main.mageSpellsManager.teamManager.leaveTeam(player);
                                                        break;
                                                    case "accept":
                                                        if(args.length == 3) {
                                                            pluginManager.main.mageSpellsManager.teamManager.acceptInvite(player, args[2]);
                                                        }
                                                        break;
                                                    case "decline":
                                                        if(args.length == 3) {
                                                            pluginManager.main.mageSpellsManager.teamManager.declineInvite(player, args[2]);
                                                        }
                                                        break;
                                                    case "invite":
                                                        if(args.length == 3) {
                                                            Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                            if (player1 != null) {
                                                                pluginManager.main.mageSpellsManager.teamManager.invitePlayer(player, player1);
                                                            }
                                                        }
                                                        break;
                                                    case "makeofficer":
                                                        if(args.length == 3) {
                                                            Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                            if (playerFromName != null) {
                                                                pluginManager.main.mageSpellsManager.teamManager.promotePlayer(player, playerFromName);
                                                            }
                                                        }
                                                        break;
                                                    case "disband":
                                                        if(args.length == 2) {
                                                            pluginManager.main.mageSpellsManager.teamManager.disbandTeam(player);
                                                        }
                                                        break;
                                                }
                                            }
                                        }
                                        if (args.length >= 2) {
                                            switch (args[1].toLowerCase()) {
                                                case "add":
                                                    if(player.hasPermission("magespells.admin")){
                                                        if(args.length == 4) {
                                                            Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                            if(playerFromName != null) {
                                                                pluginManager.main.mageSpellsManager.teamManager.addPlayer(args[3], playerFromName);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case "remove":
                                                    if(player.hasPermission("magespells.admin")){
                                                        if(args.length == 4) {
                                                            Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                            if(playerFromName != null) {
                                                                pluginManager.main.mageSpellsManager.teamManager.removePlayer(args[3], playerFromName);
                                                            }
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    player.sendMessage(ChatColor.RED + "Could not recognize command.");
                }
            }else{
                try {
                    if (cmd.getName().equalsIgnoreCase("mage")) {
                                switch (args[0].toLowerCase()) {
                                    case "learnspell":
                                        if(args.length >= 3) {
                                            if (args.length == 3) {
                                                pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(sender, args[2], args[1]);
                                            } else {
                                                //invalid
                                            }
                                        }
                                        break;
                                    case "learnwand":
                                        if(args.length >= 2){
                                            if (args.length == 3) {
                                                pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(sender, args[2], args[1]);
                                            }else{
                                                //invalid
                                            }
                                        }
                                        break;
                                    case "addexperience":
                                        if(args.length == 3){
                                            pluginManager.main.mageSpellsManager.levelingManager.giveExperience(sender, args[1], Long.parseLong(args[2]));
                                        }
                                        break;
                                    case "team":
                                        if (args.length >= 2) {
                                            switch (args[1].toLowerCase()) {
                                                case "add":
                                                    if(args.length == 4) {
                                                        Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                        if(playerFromName != null) {
                                                            pluginManager.main.mageSpellsManager.teamManager.addPlayer(args[3], playerFromName);
                                                        }
                                                    }
                                                    break;
                                                case "remove":
                                                    if(args.length == 4) {
                                                        Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                        if(playerFromName != null) {
                                                            pluginManager.main.mageSpellsManager.teamManager.removePlayer(args[3], playerFromName);
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    default:
                                        break;
                                }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.RED + "Could not recognize command.");
                }
            }
            return true;
        }
}
