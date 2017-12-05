package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.PluginManager.Refrences.ItemNames;
import me.L2_Envy.MSRM.Utils;
import net.minecraft.server.v1_12_R1.EntityIllagerWizard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpellListMenu extends UserInterface{
    private int[][] FORMAT = {{1,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,2}};
    private int page;
    private SpellObject[] spellObjects = new SpellObject[25];
    public SpellListMenu(Player player, int page) {
        super(player);
        //check page
        int maxpages = getMaxPages();
        if(page < 0){
            this.page = maxpages;
        }else if(page > maxpages){
           this.page = 0;
        }
        getSpellsOnPage();
    }
    @Override
    public Inventory loadInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.RED + "Spell Menu - Page: " + (page+1));
        PlayerObject playerObject = Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId());
        ItemStack[] contents = new ItemStack[27];
        for(int i = 0; i < FORMAT.length; i++){
            for(int j = 0; j < FORMAT[i].length; j++){
                int k = ((i*8) +i) +j;
                switch(FORMAT[i][j]){
                    case 0:
                        if(k-1 < spellObjects.length){
                            SpellObject spellObject = spellObjects[k-1];
                            if ((playerObject.getLevel() >= spellObject.getRequiredLevelToCast() || !Main.getMageSpellsManager().levelingManager.isLevelingEnabled())
                                    && (!Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() || playerObject.knowsSpell(spellObject))
                                    && (!Main.getMageSpellsManager().isNodeSystemEnabled() || getPlayer().hasPermission("magespells.spell." + spellObject.getSpellNode()))) {
                                String lore = spellObject.getLore() + "/&6BoltDamage: " + spellObject.getBoltdamage()
                                        + "/&6AuraDamage: " + spellObject.getAuradamage()
                                        + "/&6SprayDamage: " + spellObject.getSpraydamage()
                                        + "/&6Armor Piercing: " + spellObject.getArmorpiercing()
                                        + "/&6Mana Cost: " + spellObject.getManacost()
                                        + "/&6Money Cost: " + spellObject.getMoneycost()
                                        + "/&6Charge Time: " + spellObject.getChargetime()
                                        + "/&6Cooldown Time: " + spellObject.getCooldown()
                                        + "/&6Special Effect: " + spellObject.getSpellEffect()
                                        + "/&6Required Level: " + spellObject.getRequiredLevelToCast();
                                if (spellObject.isItemcostenabled()) {
                                    lore = lore + "/&6Item Cost: ";
                                    for (ItemStack itemStack : spellObject.getItemcost().keySet()) {
                                        if (Main.getMageSpellsManager().main.utils.itemStackIsCustomItem(itemStack)) {
                                            lore = lore + "/&6" + itemStack.getItemMeta().getDisplayName() + " x" + spellObject.getItemcost().get(itemStack);
                                        } else {
                                            lore = lore + "/&6" + ItemNames.lookup(itemStack) + " x" + spellObject.getItemcost().get(itemStack);
                                        }
                                    }
                                }
                                contents[k] = Utils.getItemStack("NETHER_STAR", spellObject.getDisplayname(), lore);

                                //Add Item Cost
                            } else {
                                //Add lore here
                                String lore = "";
                                if(!(playerObject.getLevel() >= spellObject.getRequiredLevelToCast()) && Main.getMageSpellsManager().levelingManager.isLevelingEnabled()){
                                    lore ="/&6Required Level: " + spellObject.getRequiredLevelToCast();
                                }
                                if(Main.getMageSpellsManager().spellLearningManager.isLearningEnabled() && !playerObject.knowsSpell(spellObject)){
                                    lore = "/&6You have not learned this spell yet!";
                                }
                                if(Main.getMageSpellsManager().isNodeSystemEnabled() && !getPlayer().hasPermission("magespells.spell." + spellObject.getSpellNode())){
                                    lore = "/&6You do not have permission to see this spell!";
                                }
                                contents[k] = Utils.getItemStack("STAINED_GLASS_PANE-15", spellObject.getDisplayname(),
                                        spellObject.getLore() + lore
                                                + "/&4This spell is currently unavaliable!");
                            }
                        }else{

                        }
                        break;
                    case 1:
                        contents[k] = Utils.getItemStack("EMERALD", "&cPrevious Page");
                        break;
                    case 2:
                        contents[k] = Utils.getItemStack("EMERALD", "&cNext Page");
                        break;
                }
            }
        }
        inventory.setContents(contents);
        return inventory;
    }

    @Override
    public void chooseIndex(int slot) {
        switch(slot){
            case 0:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new SpellListMenu(getPlayer(),page--));
                break;
            case 26:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new SpellListMenu(getPlayer(),page++));
                break;
            default:
                Main.getMageSpellsManager().guiManager.openUserInterface(getPlayer(), new SpellInfoMenu(getPlayer(),spellObjects[slot -1]));
                break;
        }
    }
    public void getSpellsOnPage(){
        int startIndex = page * 25;
        int endIndex = startIndex + 24;
        if(endIndex >= Main.mageSpellsManager.spellManager.getSpellObjects().size()){
            endIndex = Main.mageSpellsManager.spellManager.getSpellObjects().size()-1;
        }

        ArrayList<SpellObject> spellCollection = new ArrayList<>(Main.mageSpellsManager.spellManager.getSpellObjects().subList(startIndex,endIndex));
        spellObjects = spellCollection.toArray(new SpellObject[spellCollection.size()]);
    }
    public int getMaxPages(){
        ArrayList<SpellObject> spellCollection = Main.mageSpellsManager.spellManager.getSpellObjects();
        if(spellCollection.size() > 0){
            return (spellCollection.size() / 25) + (spellCollection.size() % 25 > 0 ? 1 : 0);
        }else{
            return 0;
        }
    }
}
