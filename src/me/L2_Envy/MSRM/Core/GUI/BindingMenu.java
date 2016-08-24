package me.L2_Envy.MSRM.Core.GUI;

import it.unimi.dsi.fastutil.Hash;
import me.L2_Envy.MSRM.Core.Binding.BindingManager;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.Core.Objects.WandObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 8/10/2016.
 */
public class BindingMenu {
    private ArrayList<String> playerinbindingmenu;
    private HashMap<String, ItemStack> playerinselectionmenu;
    private HashMap<String, SpellObject> playerinselectionmenubefore;
    private HashMap<String, SpellObject> playerinwandbindingmenu;
    private MageSpellsManager mageSpellsManager;
    public BindingMenu(){
        playerinbindingmenu = new ArrayList<>();
        playerinwandbindingmenu =  new HashMap<>();
        playerinselectionmenu = new HashMap<>();
        playerinselectionmenubefore = new HashMap<>();
    }
    public void link(MageSpellsManager mageSpellsManager){
        this.mageSpellsManager = mageSpellsManager;
    }
    public void openSpellBindingMenu(Player player){
        player.openInventory(loadInventory(player, true));
        playerinbindingmenu.add(player.getName());
        //Get Player Data
    }
    public void openWandBindingMenu(Player player, SpellObject spellObject){
        playerinwandbindingmenu.put(player.getName(), spellObject);
        player.openInventory(loadInventory(player, false));
        //Get Player Data
    }
    public void openSelectionMenu(Player player, ItemStack itemStack){
        playerinselectionmenubefore.put(player.getName(), playerinwandbindingmenu.get(player.getName()));
        playerinselectionmenu.put(player.getName(), itemStack);
        player.openInventory(loadSelection(player));
    }

    public void closeSpellBindingMenu(Player player){
        if(playerinbindingmenu.contains(player.getName())){
            playerinbindingmenu.remove(player.getName());
        }
    }
    public void closeWandBindingMenu(Player player){
        if(playerinwandbindingmenu.containsKey(player.getName())){
            playerinwandbindingmenu.remove(player.getName());
        }
    }
    public void closeSelectionmenu(Player player){
        if(playerinselectionmenu.containsKey(player.getName())){
            playerinselectionmenu.remove(player.getName());
        }
        if(playerinselectionmenubefore.containsKey(player.getName())){
            playerinselectionmenubefore.remove(player.getName());
        }
    }
    public SpellObject getSpellBookFromMenu(Player player){
        if(playerinwandbindingmenu.containsKey(player.getName())){
           return playerinwandbindingmenu.get(player.getName());
        }
        return null;
    }
    public SpellObject getSpellBookFromSelectionMenu(Player player){
        if(playerinselectionmenubefore.containsKey(player.getName())){
            return playerinselectionmenubefore.get(player.getName());
        }
        return null;
    }
    public ItemStack getItemStackFromMenu(Player player){
        if(playerinselectionmenu.containsKey(player.getName())){
            return playerinselectionmenu.get(player.getName());
        }
        return null;
    }
    public boolean inSpellBindingMenu(Player player){
        return playerinbindingmenu.contains(player.getName());
    }
    public boolean inWandBindingMenu(Player player){
        return playerinwandbindingmenu.containsKey(player.getName());
    }
    public boolean inSelectionMenu(Player player){
        return playerinselectionmenubefore.containsKey(player.getName()) && playerinselectionmenu.containsKey(player.getName());
    }
    private Inventory loadSelection(Player player){
        Inventory inventory = Bukkit.createInventory(null,9, ChatColor.BLUE + "Primary or Secondary Spell?");
        inventory.setItem(3, mageSpellsManager.main.utils.getItemStack("STICK","&6Primary"));
        inventory.setItem(5, mageSpellsManager.main.utils.getItemStack("STICK","&6Secondary"));
        return inventory;
    }
    private Inventory loadInventory(Player player, boolean spellmenu){
        Inventory inventory = Bukkit.createInventory(null,36, ChatColor.BLUE + "Binding Menu");
        if(mageSpellsManager.mageManager.isMage(player)){
            PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
            if(playerObject != null){
                if(spellmenu){
                    ArrayList<ItemStack> spellbooks = getSpellBooksInInv(player);
                    for(int i = 0; i < spellbooks.size(); i++){
                        inventory.setItem(i,spellbooks.get(i));
                    }
                }else{
                    ArrayList<ItemStack> wands = getWandsInInv(player);
                    for(int i = 0; i < wands.size(); i++){
                        inventory.setItem(i,wands.get(i));
                    }
                }
            }

        }
        return inventory;
    }
    private ArrayList<ItemStack> getSpellBooksInInv(Player player){
        PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
        ArrayList<ItemStack> spellbooks = new ArrayList<>();
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null) {
                if(itemStack.getType() != Material.AIR) {
                    for (SpellObject spellObject : mageSpellsManager.spellManager.getSpellObjects()) {
                        if (itemStack.equals(spellObject.getSpellbook())) {
                            if((playerObject.getLevel() >= spellObject.getRequiredLevelToBind() || !mageSpellsManager.levelingManager.isLevelingEnabled()) && (!mageSpellsManager.spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))) {
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
        PlayerObject playerObject = mageSpellsManager.mageManager.getMage(player.getUniqueId());
        SpellObject spellObject = getSpellBookFromMenu(player);
        ArrayList<ItemStack> wands = new ArrayList<>();
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null) {
                if (itemStack.getType() != Material.AIR) {
                    WandObject wandObject = mageSpellsManager.wandManager.getWandFromItem(itemStack);
                    if(wandObject != null){
                        if(((playerObject.getLevel() >= wandObject.getRequiredleveltobind() && spellObject.getRequiredLevelToBind() <= wandObject.getRequiredleveltobind()) || !mageSpellsManager.spellLearningManager.isLearningEnabled()) &&(!mageSpellsManager.levelingManager.isLevelingEnabled() || playerObject.knowsWand(wandObject))) {
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
    }
}
