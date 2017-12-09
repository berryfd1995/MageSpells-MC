package me.L2_Envy.MSRM.GUI.Interfaces;

import com.mojang.authlib.yggdrasil.response.User;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.L2_Envy.MSRM.GUI.Interfaces.SpellBindingMenu.Stage.SPELL_SELECTION;

public class MainMenu extends UserInterface {
    private static final int[] FORMAT = {0,1,2,3,4,5,6,7,8};
    public MainMenu(Player player){
        super(player);
    }
    @Override
    public Inventory loadInventory() {
        Inventory inventory = Bukkit.createInventory(null,FORMAT.length, ChatColor.BLUE + "Menu");
        ItemStack[] contents = new ItemStack[FORMAT.length];
        for(int i = 0; i < FORMAT.length; i++){
            switch (FORMAT[i]){
                case 2:
                    contents[i] = Utils.getItemStack("STICK", ChatColor.RED +"Wand Menu");
                    break;
                case 3:
                    contents[i] = Utils.getItemStack("BLAZE_POWDER", ChatColor.RED +"Spell Menu");
                    break;
                case 4:
                    if(Main.getMageSpellsManager().isWandBagEnabled()) {
                        contents[i] = Utils.getItemStack("CHEST", ChatColor.GOLD + "Wand Bag");
                    }else{
                        contents[i] = Utils.getItemStack("STAINED_GLASS_PANE-15","","");
                    }
                    break;
                case 5:
                    contents[i] = Utils.getItemStack("PAPER", ChatColor.BLUE +"Mage Stats");
                    break;
                case 6:
                    contents[i] = Utils.getItemStack("ENCHANTMENT_TABLE", ChatColor.BLUE +"Binding Menu");
                    break;
                default:
                    contents[i] = Utils.getItemStack("STAINED_GLASS_PANE-15","","");
                    break;
            }
        }
        inventory.setContents(contents);
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {
        switch (slot){
            case 2:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new WandListMenu(getPlayer(), 0));
                break;
            case 3:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new SpellListMenu(getPlayer(), 0));
                break;
            case 4:
                if(Main.getMageSpellsManager().isWandBagEnabled()) {
                    Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new WandBagMenu(getPlayer()));
                }
                break;
            case 5:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new PlayerMenu(getPlayer()));
                break;
            case 6:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new SpellBindingMenu(getPlayer(), SPELL_SELECTION));
                break;
            default:
                return;
        }
    }
}
