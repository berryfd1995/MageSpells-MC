package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.TeamObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerMenu extends UserInterface {
    private static final int[] FORMAT = {0,0,0,0,1,0,0,0,0};
    public PlayerMenu(Player player){
        super(player);
    }
    @Override
    public Inventory loadInventory() {
        Inventory inventory = Bukkit.createInventory(null,9, ChatColor.BLUE + "Player Statistics");
        ItemStack[] contents = new ItemStack[FORMAT.length];
        if(Main.getMageSpellsManager().mageManager.isMage(getPlayer())) {
            PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId());
            TeamObject teamObject = Main.getMageSpellsManager().teamManager.getTeam(playerObject);
            String teamname;
            if(teamObject == null){
                teamname = "None";
            }else{
                teamname = teamObject.getTeamname();
            }
            for(int i = 0; i < FORMAT.length; i++) {
                switch (FORMAT[i]) {
                    case 0:
                        contents[i] = Utils.getItemStack("STAINED_GLASS_PANE-15","","");
                        break;
                    default:
                        contents[i] = Utils.getItemStack("BOOK","&bStats",
                                "&6Name: " + getPlayer().getName()
                                        + "/&6Team Name: " + teamname
                                        + "/&6Level: " + playerObject.getLevel()
                                        + "/&6Current xp: " + playerObject.getExperience()
                                        + "/&6Spells Learned: " + (playerObject.getSpellObjects().size())
                                        + " out of " + (Main.getMageSpellsManager().spellManager.getSpellObjects().size())
                                        + "/&6Wands Learned: " + playerObject.getWandObjects().size()
                                        + " out of " + Main.getMageSpellsManager().wandManager.getWandObjects().size());
                        break;
                }
            }
        }
        inventory.setContents(contents);
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {

    }
}
