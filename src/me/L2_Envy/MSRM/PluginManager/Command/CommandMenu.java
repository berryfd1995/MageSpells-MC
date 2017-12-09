package me.L2_Envy.MSRM.PluginManager.Command;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.GUI.Interfaces.MainMenu;
import me.L2_Envy.MSRM.Main;
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
    @SuppressWarnings( "deprecation" )
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                try {
                    if (cmd.getName().equalsIgnoreCase("mage")) {
                        if (player.hasPermission("magespells.mage") || player.isOp()) {
                            if (pluginManager.main.mageSpellsManager.mageManager.isMage(player)) {
                                PlayerObject playerObject = pluginManager.main.mageSpellsManager.mageManager.getMage(player.getUniqueId());
                                if(playerObject != null) {
                                    if (args.length == 0) {
                                        helpMenu.displayHelpMenu(player, 1);
                                    } else {
                                        switch (args[0].toLowerCase()) {
                                            case "reload":
                                               /* player.sendMessage(ChatColor.GREEN + "Reloading configs..");
                                                pluginManager.configClass.loadOtherConfigs();
                                                pluginManager.customItemConfig.loadCustomItemConfigs();
                                                pluginManager.spellConfig.loadSpellConfigs();
                                                pluginManager.wandConfig.loadWandConfigs();
                                                pluginManager.teamConfig.loadTeamConfigs();
                                                player.sendMessage(ChatColor.GREEN + "Reloading player data..");
                                                if(player.hasPermission("magespells.admin")){
                                                    for(Player player1 : Bukkit.getOnlinePlayers()){
                                                        if(player.hasPermission("magespells.mage")) {
                                                            PlayerObject playerObject1 = pluginManager.playerConfig.loadPlayerData(player1.getUniqueId());
                                                            if (playerObject1 != null) {
                                                                pluginManager.main.mageSpellsManager.mageManager.addMage(playerObject1);
                                                                pluginManager.main.mageSpellsManager.manaManager.scheduleManaTask(playerObject1);
                                                            }
                                                        }
                                                    }
                                                }
                                                player.sendMessage(ChatColor.GREEN + "Magespells reloaded.");*/
                                                player.sendMessage(ChatColor.GREEN + "This feature has been temporarily disabled.");
                                                break;
                                            case "help":
                                                if (args.length == 2) {
                                                    helpMenu.displayHelpMenu(player, Integer.parseInt(args[1]));
                                                } else {
                                                    helpMenu.displayHelpMenu(player, 1);
                                                }
                                                break;
                                            case "menu":
                                                //pluginManager.main.mageSpellsManager.playerInterface.openPlayerInterface(player);
                                                Main.getMageSpellsManager().guiManager.openUserInterface(player, new MainMenu(player));
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
                                                if (args.length >= 2) {
                                                    if (player.hasPermission("magespells.admin")) {
                                                        if (args.length == 2) {
                                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(player, playerObject, args[1]);
                                                        } else if (args.length == 3) {
                                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(player, args[2], args[1]);
                                                        } else {
                                                            player.sendMessage(ChatColor.RED + "Invalid Parameters");
                                                        }
                                                    }
                                                } else {
                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(player, playerObject);
                                                }
                                                break;
                                            case "learnwand":
                                                if (args.length >= 2) {
                                                    if (player.hasPermission("magespells.admin")) {
                                                        if (args.length == 2) {
                                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(player, playerObject, args[1]);
                                                        } else if (args.length == 3) {
                                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(player, args[2], args[1]);
                                                        } else {
                                                            player.sendMessage(ChatColor.RED + "Invalid Parameters");
                                                        }
                                                    }
                                                } else {
                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(player, playerObject);
                                                }
                                                break;
                                            case "learnall":
                                                if (player.hasPermission("magespells.admin")) {
                                                    if (args.length == 1) {
                                                        for (WandObject wandObject : pluginManager.main.mageSpellsManager.wandManager.getWandObjects()) {
                                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(playerObject, wandObject);
                                                        }
                                                        for (SpellObject spellObject : pluginManager.main.mageSpellsManager.spellManager.getSpellObjects()) {
                                                            pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(playerObject, spellObject);
                                                        }
                                                        player.sendMessage(ChatColor.GREEN + "You have learned every spell and wand!");
                                                    } else if (args.length == 2) {
                                                        Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[1]);
                                                        if (player1 != null) {
                                                            if (pluginManager.main.mageSpellsManager.mageManager.isMage(player1)) {
                                                                PlayerObject playerObject1 = pluginManager.main.mageSpellsManager.mageManager.getMage(player1.getUniqueId());

                                                                for (WandObject wandObject : pluginManager.main.mageSpellsManager.wandManager.getWandObjects()) {
                                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnWand(playerObject1, wandObject);
                                                                }
                                                                for (SpellObject spellObject : pluginManager.main.mageSpellsManager.spellManager.getSpellObjects()) {
                                                                    pluginManager.main.mageSpellsManager.spellLearningManager.learnSpell(playerObject1, spellObject);
                                                                }
                                                            }
                                                        }
                                                        player1.sendMessage(ChatColor.GREEN + "You have learned every spell and wand!");
                                                    }
                                                }
                                            case "addexperience":
                                                if (args.length == 3) {
                                                    if (player.hasPermission("magespells.admin")) {
                                                        pluginManager.main.mageSpellsManager.levelingManager.giveExperience(player, args[1], Long.parseLong(args[2]));
                                                        player.sendMessage(ChatColor.GREEN + "You have given player " + args[1] + " " + Long.parseLong(args[2]) + " experience.");
                                                    }
                                                }
                                                break;
                                            case "spawnspellbook":
                                                if (args.length >= 2) {
                                                    if (player.hasPermission("magespells.admin")) {
                                                        SpellObject spellObject = pluginManager.main.mageSpellsManager.spellManager.getSpellFromName(args[1]);
                                                        if (args.length > 2) {
                                                            Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                            if (spellObject != null && player1 != null) {
                                                                player1.getInventory().addItem(spellObject.getSpellbook());
                                                                player.sendMessage(ChatColor.GREEN + "Successfully spawned in spellbook for player " + player1.getName());
                                                            }
                                                        } else {
                                                            if (spellObject != null) {
                                                                player.getInventory().addItem(spellObject.getSpellbook());
                                                                player.sendMessage(ChatColor.GREEN + "Successfully spawned in spellbook.");
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case "spawnwand":
                                                if (args.length >= 2) {
                                                    if (player.hasPermission("magespells.admin")) {
                                                        WandObject wandObject = pluginManager.main.mageSpellsManager.wandManager.getWandFromName(args[1]);
                                                        if (args.length > 2) {
                                                            Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                            if (wandObject != null && player1 != null) {
                                                                player1.getInventory().addItem(wandObject.getWandItemStack());
                                                                player.sendMessage(ChatColor.GREEN + "Successfully spawned in wand for player " + player1.getName());
                                                            }
                                                        } else {
                                                            if (wandObject != null) {
                                                                player.getInventory().addItem(wandObject.getWandItemStack());
                                                                player.sendMessage(ChatColor.GREEN + "Successfully spawned in wand.");
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case "setprimaryspell":
                                                if (player.hasPermission("magespells.admin")) {
                                                    if (args.length == 2) {
                                                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                                                        if (itemStack != null) {
                                                            if (pluginManager.main.mageSpellsManager.wandManager.isWand(itemStack)) {
                                                                SpellObject spellObject = pluginManager.main.mageSpellsManager.spellManager.getSpellFromName(args[1]);
                                                                if (spellObject != null) {
                                                                    player.getInventory().setItemInMainHand(pluginManager.main.mageSpellsManager.bindingManager.setPrimarySpell(itemStack, spellObject));
                                                                    player.updateInventory();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case "setsecondaryspell":
                                                if (player.hasPermission("magespells.admin")) {
                                                    if (player.hasPermission("magespells.admin")) {
                                                        if (args.length == 2) {
                                                            ItemStack itemStack = player.getInventory().getItemInMainHand();
                                                            if (itemStack != null) {
                                                                if (pluginManager.main.mageSpellsManager.wandManager.isWand(itemStack)) {
                                                                    SpellObject spellObject = pluginManager.main.mageSpellsManager.spellManager.getSpellFromName(args[1]);
                                                                    if (spellObject != null) {
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
                                                if (pluginManager.main.mageSpellsManager.teamManager.getUsercreatesteam()) {
                                                    if (args.length >= 2) {
                                                        switch (args[1].toLowerCase()) {
                                                            case "create":
                                                                if (args.length == 3) {
                                                                    if(pluginManager.main.mageSpellsManager.teamManager.createTeam(args[2], player)) {
                                                                        player.sendMessage(ChatColor.GREEN + "You have successfully created the team: " + args[2]);
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "You were unable to create this team!");
                                                                    }
                                                                }else{
                                                                    player.sendMessage(ChatColor.RED + "Unable to create this team!");
                                                                }
                                                                break;
                                                            case "leave":
                                                                if(pluginManager.main.mageSpellsManager.teamManager.leaveTeam(player)){
                                                                    player.sendMessage(ChatColor.GREEN + "You successfully left your team!");
                                                                }else{
                                                                    player.sendMessage(ChatColor.RED + "You were unable to perform this action!");
                                                                }
                                                                break;
                                                            case "accept":
                                                                if (args.length == 3) {
                                                                    if(pluginManager.main.mageSpellsManager.teamManager.acceptInvite(player, args[2])){
                                                                        player.sendMessage(ChatColor.GREEN + "You successfully joined a team!");
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "You were unable to join this team!");
                                                                    }
                                                                }
                                                                break;
                                                            case "decline":
                                                                if (args.length == 3) {
                                                                    if(pluginManager.main.mageSpellsManager.teamManager.declineInvite(player, args[2])){
                                                                        player.sendMessage(ChatColor.GREEN + "You successfully declined this invite!");
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "You were unable to perform this action!");
                                                                    }
                                                                }
                                                                break;
                                                            case "invite":
                                                                if (args.length == 3) {
                                                                    Player player1 = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                                    if (player1 != null) {
                                                                        if(pluginManager.main.mageSpellsManager.teamManager.invitePlayer(player, player1)){
                                                                            player.sendMessage(ChatColor.GREEN + "You successfully invited " + player1.getName());
                                                                        }else{
                                                                            player.sendMessage(ChatColor.RED + "You were unable to invite " + player1.getName());
                                                                        }
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "Could not find that player!");
                                                                    }
                                                                }
                                                                break;
                                                            case "promote":
                                                                if (args.length == 3) {
                                                                    Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                                    if (playerFromName != null) {
                                                                        if(pluginManager.main.mageSpellsManager.teamManager.promotePlayer(player, playerFromName)){
                                                                            player.sendMessage(ChatColor.GREEN + "You successfully promoted this player!");
                                                                        }else{
                                                                            player.sendMessage(ChatColor.RED +"You were unable to promote this player!");
                                                                        }
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "Player must be online to promote!");
                                                                    }
                                                                }
                                                                break;
                                                            case "demote":
                                                                if(args.length == 3){
                                                                    Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                                    if(playerFromName!= null){
                                                                        if(pluginManager.main.mageSpellsManager.teamManager.demotePlayer(player, playerFromName)){
                                                                            player.sendMessage(ChatColor.GREEN + "You successfully demoted this player!");
                                                                        }else{
                                                                            player.sendMessage(ChatColor.RED +"You were unable to demote this player!");
                                                                        }
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "Player must be online to demote!");
                                                                    }
                                                                }
                                                            case "disband":
                                                                if (args.length == 2) {
                                                                    if(pluginManager.main.mageSpellsManager.teamManager.disbandTeam(player)){
                                                                        player.sendMessage(ChatColor.GREEN + "You successfully disbanded your team!");
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "Unable to perform this action!");
                                                                    }
                                                                }
                                                                break;
                                                            case "kick":
                                                                if(args.length == 3){
                                                                    OfflinePlayer offlinePlayer = pluginManager.main.utils.getOfflinePlayerFromName(args[2]);
                                                                    if(offlinePlayer != null){
                                                                        if(pluginManager.main.mageSpellsManager.teamManager.kickPlayer(player,offlinePlayer)){
                                                                            player.sendMessage(ChatColor.GREEN + "You successfully kicked that player!");
                                                                        }else{
                                                                            player.sendMessage(ChatColor.RED + "You were unable to perform that action!");
                                                                        }
                                                                    }else{
                                                                        player.sendMessage(ChatColor.RED + "Could not find that player!");
                                                                    }
                                                                }
                                                            case "stats":
                                                                pluginManager.main.mageSpellsManager.teamManager.displayStats(player);
                                                                break;
                                                            default:
                                                                helpMenu.displayHelpMenu(player, 2);
                                                                break;
                                                        }
                                                    }
                                                }
                                                if (args.length >= 2) {
                                                    switch (args[1].toLowerCase()) {
                                                        case "add":
                                                            if (player.hasPermission("magespells.admin")) {
                                                                if (args.length == 4) {
                                                                    Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                                    if (playerFromName != null) {
                                                                        pluginManager.main.mageSpellsManager.teamManager.addPlayer(args[3], playerFromName);
                                                                    }
                                                                }
                                                            }
                                                            break;
                                                        case "remove":
                                                            if (player.hasPermission("magespells.admin")) {
                                                                if (args.length == 4) {
                                                                    Player playerFromName = pluginManager.main.utils.getOnlinePlayerFromName(args[2]);
                                                                    if (playerFromName != null) {
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
                            }else{
                                try {
                                    player.sendMessage(ChatColor.RED + "Somehow you are not a mage, but should be. Let me fix that for you!");
                                    pluginManager.playerConfig.loadPlayerData(player.getName(), player.getUniqueId());
                                    player.sendMessage(ChatColor.RED + "Okay, now try to run /mage again.");
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                    player.sendMessage(ChatColor.RED +"Contact your server administrator. There was an error with MageSpells.");
                                }
                            }
                        }
                    }else if(cmd.getName().equalsIgnoreCase("mtc")){
                        if (player.hasPermission("magespells.mage") || player.isOp()) {
                            if (pluginManager.main.mageSpellsManager.mageManager.isMage(player)) {
                                PlayerObject playerObject = pluginManager.main.mageSpellsManager.mageManager.getMage(player.getUniqueId());
                                if (playerObject != null) {
                                    if (args.length == 0) {
                                        helpMenu.displayHelpMenu(player, 1);
                                    } else {
                                        pluginManager.main.mageSpellsManager.teamManager.sendMessage(player,args[0]);
                                    }
                                }
                            }else{
                                try {
                                    player.sendMessage(ChatColor.RED + "Somehow you are not a mage, but should be. Let me fix that for you!");
                                    pluginManager.playerConfig.loadPlayerData(player.getName(), player.getUniqueId());
                                    player.sendMessage(ChatColor.RED + "Okay, now try to run /mage again.");
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                    player.sendMessage(ChatColor.RED +"Contact your server administrator. There was an error with MageSpells.");
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
