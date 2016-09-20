package me.L2_Envy.MSRM;

import me.L2_Envy.MSRM.Alchemy.AlchemyManager;
import me.L2_Envy.MSRM.Alchemy.Handlers.MenuListener;
import me.L2_Envy.MSRM.Core.Handlers.*;
import me.L2_Envy.MSRM.Core.MageSpellsManager;
import me.L2_Envy.MSRM.Core.Objects.PlayerObject;
import me.L2_Envy.MSRM.PluginManager.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by L2_Envy on 7/23/2016.
 */
public class Main extends JavaPlugin {
    public Logger logger = getLogger();
    public MageSpellsManager mageSpellsManager;
    public PluginManager pluginManager;
    public AlchemyManager alchemyManager;
    public Utils utils;
    public MenuListener menuListener;
    public CastingListener castingListener;
    public CraftingListener craftingListener;
    public EnchantingListener enchantingListener;
    public EntityListener entityListener;
    public InventoryListener inventoryListener;
    public void onEnable(){
        saveDefaultConfig();
        mageSpellsManager = new MageSpellsManager();
        pluginManager = new PluginManager();
        alchemyManager = new AlchemyManager();
        utils = new Utils(this);
        logger.info("Initilizing MageSpells_Remastered");
        pluginManager.linkAll(this);
        mageSpellsManager.linkAll(this);
        alchemyManager.linkAll(this);
        if(!pluginManager.InitilizePlugin()){
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            mageSpellsManager.InitilizePlugin();
            registerEvents(this, new MenuListener(alchemyManager), new CastingListener(mageSpellsManager), new CraftingListener(mageSpellsManager),
                    new EntityListener(mageSpellsManager), new EnchantingListener(mageSpellsManager), new InventoryListener(mageSpellsManager), new PlayerHandler(mageSpellsManager));
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.hasPermission("magespells.mage")) {
                    PlayerObject playerObject = pluginManager.playerConfig.loadPlayerData(player.getUniqueId());
                    if (playerObject != null) {
                        mageSpellsManager.mageManager.addMage(playerObject);
                        mageSpellsManager.manaManager.scheduleManaTask(playerObject);
                    }
                }
            }
            logger.info("MageSpells_Remastered Enabled!");
        }
    }
    public void onDisable(){
        logger.info("Saving data...");
        mageSpellsManager.mageManager.saveMages();
        mageSpellsManager.teamManager.saveTeams();
        logger.info("All data saved.");
    }
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    /**TODO: Create File structure
     * On startup Create folders-- export files 100%
     * Load all configs 100% -Messages
     * Work on GUI / HelpMenu / CommandMenu 100% -Help menu
     * Work on LevelManager 100%
     * Work on LearningManager 100%
     * Work on Wand Creation / Binding 100%
     * Work on SpellBookManager / Crafting 100%
     * Work on Spell Casting / Mana Manager 100%
     * Work on Active Spells / Spell Effects 100%
     * Work on On-Hit Effects / Teammates 100%
     * Work on Dependencies 100%
     * DEBUG
     * -Teams 100%
     * -SpellAttacking 100%
     * -Level Manager 100%
     * -Menu 100%
     * -SpellBook 100%
     * -Casting 100%
     * -Effects 100%
     * -Binding 100%
     * -File Saving 100%
     * -Mana Bar 100%
     * -Notes 100%
     * -Messages HOLD
     * -Fix Space in title.
     * -API HOLD
     * Work on API 0%
     * Work on Enchanting 0%
     * Work on Alchemy 0%
     * TODO: Add more options for custom item
     */
}
