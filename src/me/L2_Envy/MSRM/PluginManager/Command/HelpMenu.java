package me.L2_Envy.MSRM.PluginManager.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Daniel on 7/23/2016.
 */
public class HelpMenu {
    public CommandMenu commandMenu;
    public HelpMenu(){
    }
    public void link(CommandMenu commandMenu){
        this.commandMenu = commandMenu;
    }
    public void displayHelpMenu(Player player, int page){
        switch(page){
            case 1:
                player.sendMessage(ChatColor.GREEN+ "=========" + ChatColor.BLUE + "HELP MENU [1/6]"+ChatColor.GREEN + "========");
                player.sendMessage(ChatColor.BLUE + "/mage help # -- " + ChatColor.GREEN + "Used to display the help menu.");
                player.sendMessage(ChatColor.BLUE + "/mage menu --" + ChatColor.GREEN + "Opens up the magespells user interface.");
                player.sendMessage(ChatColor.BLUE + "/mage wandmenu -- " + ChatColor.GREEN + "Directly opens up the wand interface.");
                player.sendMessage(ChatColor.BLUE + "/mage spellmenu --" + ChatColor.GREEN + "Directly opens up the spell interface.");
                player.sendMessage(ChatColor.BLUE + "/mage bag --" + ChatColor.GREEN + "Directly opens up the wand bag");
                player.sendMessage(ChatColor.BLUE + "/mage stats --" + ChatColor.GREEN + "Directly opens up the mage stats interface.");
                player.sendMessage(ChatColor.BLUE + "Extra Info on other pages.");
                break;
            case 2:
                player.sendMessage(ChatColor.GREEN+ "=========" + ChatColor.BLUE + "HELP MENU [2/6]"+ChatColor.GREEN + "========");
                player.sendMessage(ChatColor.BLUE + "/mage bind --" + ChatColor.GREEN + "Directly opens up the binding interface.");
                if(commandMenu.pluginManager.main.mageSpellsManager.teamManager.getUsercreatesteam()|| player.hasPermission("magespells.admin")) {
                    player.sendMessage(ChatColor.BLUE + "/mage team disband --" + ChatColor.GREEN + "Disband your current team.");
                    player.sendMessage(ChatColor.BLUE + "/mage team create <teamname>--" + ChatColor.GREEN + "Create a team.");
                    player.sendMessage(ChatColor.BLUE + "/mage team leave --" + ChatColor.GREEN + "Leave the team you are currently in.");
                    player.sendMessage(ChatColor.BLUE + "/mage team invite <player> --" + ChatColor.GREEN + "Invite a player to your team.");
                    player.sendMessage(ChatColor.BLUE + "/mage team accept <teamname> --" + ChatColor.GREEN + "Accept a team invite.");
                    player.sendMessage(ChatColor.BLUE + "/mage team stats --" + ChatColor.GREEN + "Display current team information");
                }
                player.sendMessage(ChatColor.BLUE + "Extra Info on other pages.");
                break;
            case 3:
                player.sendMessage(ChatColor.GREEN+ "=========" + ChatColor.BLUE + "HELP MENU [3/6]"+ChatColor.GREEN + "========");
                if(commandMenu.pluginManager.main.mageSpellsManager.teamManager.getUsercreatesteam() || player.hasPermission("magespells.admin")) {
                    player.sendMessage(ChatColor.BLUE + "/mage team promote <player>--" + ChatColor.GREEN + "Promote a player to officer status");
                    player.sendMessage(ChatColor.BLUE + "/mage team demote <player>--" + ChatColor.GREEN + "Demote a player to normal memeber status");
                    player.sendMessage(ChatColor.BLUE + "/mage team kick <player> --" + ChatColor.GREEN + "Kick a player off your team!");
                    player.sendMessage(ChatColor.BLUE + "/tc <message> --" + ChatColor.GREEN + "Send a message to all of your teammates!");
                }
                if(player.hasPermission("magespells.admin")){
                    player.sendMessage(ChatColor.BLUE + "/mage team add <player> <team> --" + ChatColor.GREEN + "Add a player to a team.");
                    player.sendMessage(ChatColor.BLUE + "/mage team remove <player> <team> --" + ChatColor.GREEN + "Remove a player from a team.");
                }
                player.sendMessage(ChatColor.BLUE + "Extra Info on other pages.");
                break;
            case 4:
                player.sendMessage(ChatColor.GREEN + "=========" + ChatColor.BLUE + "HELP MENU [4/6]" + ChatColor.GREEN + "========");
                if(player.hasPermission("magespells.admin")) {
                    player.sendMessage(ChatColor.BLUE + "/mage reload --" + ChatColor.GREEN + "Reload configs and player data for magespells --"+ChatColor.RED+" Disabled Currently");
                    player.sendMessage(ChatColor.BLUE + "/mage spawnwand <wandname> <player>--" + ChatColor.GREEN + "Spawn in a wand from specified wand name.");
                    player.sendMessage(ChatColor.BLUE + "/mage setprimaryspell <spellname> --" + ChatColor.GREEN + "Set the primary spell of the current held wand.");
                    player.sendMessage(ChatColor.BLUE + "/mage setsecondaryspell <spellname> -- " + ChatColor.GREEN + "Set the secondary spell of the current held wand.");
                    player.sendMessage(ChatColor.BLUE + "/mage addexperience <player> <amount> --" + ChatColor.GREEN + "Add Experience to a current player.");
                    player.sendMessage(ChatColor.BLUE + "/mage spawnspellbook <spellname> <player> --" + ChatColor.GREEN + "Spawn a spell book from specified spell name.");
                    player.sendMessage(ChatColor.BLUE + "/mage learnwand <player> <wandname> --" + ChatColor.GREEN + "Teach a wand to a player. (Unlocks wand)");
                    player.sendMessage(ChatColor.BLUE + "/mage learnspell <player> <spellname> --" + ChatColor.GREEN + "Teach a spell to a player. (Unlocks Spell)");
                    player.sendMessage(ChatColor.BLUE + "/mage learnall <player> --" + ChatColor.GREEN + "Teach a player all spells and wands");
                }else{
                    player.sendMessage(ChatColor.BLUE + "You need to be an admin to see this page!");
                }
                break;
            case 5:
                player.sendMessage(ChatColor.GREEN+ "=========" + ChatColor.BLUE + "HELP MENU [5/6]"+ChatColor.GREEN + "========");
                player.sendMessage(ChatColor.BLUE + "Power Levels: " + ChatColor.GREEN + "Each wand and spell requires you to have a specific power level to craft, cast/use, bind with, and drop.");
                player.sendMessage(ChatColor.BLUE + "Binding: " + ChatColor.GREEN + "Whenever you want to add a spell to a wand, use the binding menu. Be sure they are compatible!");
                player.sendMessage(ChatColor.BLUE + "Menu: " + ChatColor.GREEN + "Use the menu for anything magespells related. Watch it update as you progress!");
                player.sendMessage(ChatColor.BLUE + "Bolt Spells: " + ChatColor.GREEN + "These spells shoot out in a straight direction and explode when hitting a target.");
                player.sendMessage(ChatColor.BLUE + "Spray Spells: " + ChatColor.GREEN + "These spells shoot out in a straight direction, and damage entities while flying. They do not stop on targets.");
                break;
            case 6:
                player.sendMessage(ChatColor.GREEN+ "=========" + ChatColor.BLUE + "HELP MENU [6/6]"+ChatColor.GREEN + "========");
                player.sendMessage(ChatColor.BLUE + "Arua Spells: " + ChatColor.GREEN + "Creates a zone around you or from spell projectile and applies the affect to anyone in the zone.");
                player.sendMessage(ChatColor.BLUE + "What is Mana: " + ChatColor.GREEN + "In order to perform a spell, you need the required amount of mana!");
                player.sendMessage(ChatColor.BLUE + "What are Item Costs: " + ChatColor.GREEN + "Some spells require you to have specific items in your inventory to cast!");
                player.sendMessage(ChatColor.BLUE + "What is a Wand: " + ChatColor.GREEN + "These are needed to cast any spells.");
                player.sendMessage(ChatColor.BLUE + "What is a charge time: " + ChatColor.GREEN + "The charge time must finish before the spell is cast.");
                break;
        }

    }
}
