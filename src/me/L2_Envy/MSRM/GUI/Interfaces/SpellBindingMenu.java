package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.Utils;
import net.minecraft.server.v1_12_R1.EntityIllagerWizard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpellBindingMenu extends UserInterface{
    public enum Stage{
        WAND_SELECTION,
        MODE_SELECTION,
        SPELL_SELECTION,
    }
    private Stage stage;
    private SpellObject spellObject;
    private ItemStack wandItemStack;
    public SpellBindingMenu(Player player, Stage stage){
        super(player);
        this.stage = stage;
    }
    public void setSpellObject(SpellObject spellObject){
        this.spellObject = spellObject;
    }
    public void setWandObject(ItemStack wandItemStack){
        this.wandItemStack = wandItemStack;
    }
    @Override
    public Inventory loadInventory() {
        Inventory inventory = null;
        switch(stage){
            case WAND_SELECTION:
                inventory = Bukkit.createInventory(null,36, ChatColor.BLUE + "Binding Menu- Please choose a wand");
                ArrayList<ItemStack> wands = getWandsInInv(getPlayer());
                for(int i = 0; i < wands.size(); i++){
                    inventory.setItem(i,wands.get(i));
                }
                break;
            case MODE_SELECTION:
                inventory = Bukkit.createInventory(null,9, ChatColor.BLUE + "Binding Menu- Is this Primary or Secondary?");
                inventory.setItem(3, Utils.getItemStack("STICK","&6Primary"));
                inventory.setItem(5, Utils.getItemStack("STICK","&6Secondary"));
                break;
            case SPELL_SELECTION:
                inventory = Bukkit.createInventory(null,36, ChatColor.BLUE + "Binding Menu- Please choose a spell");
                ArrayList<ItemStack> spellbooks = getSpellBooksInInv(getPlayer());
                for(int i = 0; i < spellbooks.size(); i++){
                    inventory.setItem(i,spellbooks.get(i));
                }
                break;
        }
        this.setInventory(inventory);
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {
        switch(stage){
            case WAND_SELECTION:
               ItemStack itemStack = getInventory().getItem(slot);
                if(itemStack != null){
                    SpellBindingMenu spellBindingMenu = new SpellBindingMenu(getPlayer(),Stage.MODE_SELECTION);
                    spellBindingMenu.setSpellObject(spellObject);
                    spellBindingMenu.setWandObject(itemStack);
                    Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(),spellBindingMenu);
                }
                break;
            case MODE_SELECTION:
                switch(slot){
                    case 3:
                        Main.getMageSpellsManager().bindingManager.bind(getPlayer(),spellObject,wandItemStack,true);
                        Main.getMageSpellsManager().guiManager.closeUserInterface(getPlayer());
                        break;
                    case 5:
                        Main.getMageSpellsManager().bindingManager.bind(getPlayer(),spellObject,wandItemStack,false);
                        Main.getMageSpellsManager().guiManager.closeUserInterface(getPlayer());

                        break;
                }
                break;
            case SPELL_SELECTION:
                SpellObject spellObject = Main.getMageSpellsManager().spellBookManager.getSpellFromBook(getInventory().getItem(slot));
                if(spellObject != null) {
                    SpellBindingMenu spellBindingMenu = new SpellBindingMenu(getPlayer(),Stage.WAND_SELECTION);
                    spellBindingMenu.setSpellObject(spellObject);
                    Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(),spellBindingMenu);
                }
                break;
        }
    }

   private ArrayList<ItemStack> getSpellBooksInInv(Player player){
        PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(player.getUniqueId());
        ArrayList<ItemStack> spellbooks = new ArrayList<>();
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null) {
                if(itemStack.getType() != Material.AIR) {
                    for (SpellObject spellObject : Main.getMageSpellsManager().spellManager.getSpellObjects()) {
                        if (itemStack.equals(spellObject.getSpellbook())) {
                            if((playerObject.getLevel() >= spellObject.getRequiredLevelToBind() || !Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                                    && (!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))
                                    &&(!Main.getMageSpellsManager().isNodeSystemEnabled() || player.hasPermission("magespells.spell" + spellObject.getSpellNode()))) {
                                ItemStack itemStack1 = itemStack.clone();
                                itemStack1.setAmount(1);
                                if (!spellbooks.contains(itemStack1)) {
                                    spellbooks.add(itemStack1);
                                }
                            }
                        }
                    }
                }
            }
        }
        return spellbooks;
    }
    private ArrayList<ItemStack> getWandsInInv(Player player){
        PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(player.getUniqueId());
        ArrayList<ItemStack> wands = new ArrayList<>();
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null) {
                if (itemStack.getType() != Material.AIR) {
                    WandObject wandObject = Main.getMageSpellsManager().wandManager.getWandFromItem(itemStack);
                    if(wandObject != null){
                       /* if(((playerObject.getLevel() >= wandObject.getRequiredleveltobind() *//*&& spellObject.getRequiredLevelToBind()
                                <= wandObject.getRequiredleveltobind()*//* )||!Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                                &&(!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))
                                && (!Main.getMageSpellsManager().isNodeSystemEnabled() ||( player.hasPermission("magespells.wand." + wandObject.getWandnode())
                                && player.hasPermission("magespells.spell." + spellObject.getSpellNode()) && wandObject.isSpellCompatible(spellObject.getSpellNode())))){*/
                            ItemStack itemStack1 = itemStack.clone();
                            itemStack1.setAmount(1);
                            if (!wands.contains(itemStack1)) {
                                wands.add(itemStack1);
                            }
                        //}
                    }
                }
            }
        }
        return wands;
    }
}
