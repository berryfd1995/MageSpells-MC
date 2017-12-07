package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpellBindingMenu extends UserInterface{
    public SpellBindingMenu(Player player, int stage){
        super(player);
    }
    @Override
    public Inventory loadInventory() {
        return null;
    }

    @Override
    public void chooseIndex(int slot) {

    }

   /* private ArrayList<ItemStack> getSpellBooksInInv(Player player){
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
        SpellObject spellObject = getSpellBookFromMenu(player);
        ArrayList<ItemStack> wands = new ArrayList<>();
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null) {
                if (itemStack.getType() != Material.AIR) {
                    WandObject wandObject = Main.getMageSpellsManager().wandManager.getWandFromItem(itemStack);
                    if(wandObject != null){
                        if(((playerObject.getLevel() >= wandObject.getRequiredleveltobind() *//*&& spellObject.getRequiredLevelToBind()
                                <= wandObject.getRequiredleveltobind()*//* )||!Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                                &&(!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsWand(wandObject))
                                && (!Main.getMageSpellsManager().isNodeSystemEnabled() ||( player.hasPermission("magespells.wand." + wandObject.getWandnode())
                                && player.hasPermission("magespells.spell." + spellObject.getSpellNode()) && wandObject.isSpellCompatible(spellObject.getSpellNode())))){
                            ItemStack itemStack1 = itemStack.clone();
                            itemStack1.setAmount(1);
                            if (!wands.contains(itemStack1)) {
                                wands.add(itemStack1);

                            }
                        }
                    }
                }
            }
        }
        return wands;
    }*/
}
