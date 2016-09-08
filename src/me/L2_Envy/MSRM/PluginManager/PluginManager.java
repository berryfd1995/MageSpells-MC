package me.L2_Envy.MSRM.PluginManager;

import me.L2_Envy.MSRM.Main;
import me.L2_Envy.MSRM.API.MageSpellsAPI;
import me.L2_Envy.MSRM.PluginManager.APIHooks.ParticlePkg.ParticleAPI;
import me.L2_Envy.MSRM.PluginManager.APIHooks.TitleManagerPkg.TitleManagerAPI;
import me.L2_Envy.MSRM.PluginManager.APIHooks.VaultPkg.VaultAPI;
import me.L2_Envy.MSRM.PluginManager.APIHooks.WorldEditPkg.WorldEditAPI;
import me.L2_Envy.MSRM.PluginManager.Command.CommandMenu;
import me.L2_Envy.MSRM.PluginManager.Config.*;
import me.L2_Envy.MSRM.PluginManager.Refrences.Messages;

/**
 * Created by Daniel on 7/23/2016.
 */
public class PluginManager {
    public Main main;
    public CommandMenu commandMenu;
    public MageSpellsAPI mageSpellsAPI;
    public ParticleAPI particleAPI;
    public TitleManagerAPI titleManagerAPI;
    public WorldEditAPI worldEditAPI;
    public VaultAPI vaultAPI;
    public Messages messages;
    public ConfigClass configClass;
    public PlayerConfig playerConfig;
    public SpellConfig spellConfig;
    public TeamConfig teamConfig;
    public WandConfig wandConfig;
    public ExampleConfig exampleConfig;
    public Notes notes;
    public CustomItemConfig customItemConfig;
    public PluginManager(){
        commandMenu = new CommandMenu();
        mageSpellsAPI = new MageSpellsAPI();
        titleManagerAPI = new TitleManagerAPI();
        worldEditAPI = new WorldEditAPI();
        particleAPI = new ParticleAPI();
        vaultAPI = new VaultAPI();
        messages = new Messages();
        configClass = new ConfigClass();
        playerConfig = new PlayerConfig();
        spellConfig = new SpellConfig();
        teamConfig = new TeamConfig();
        wandConfig = new WandConfig();
        notes = new Notes();
        exampleConfig = new ExampleConfig();
        customItemConfig = new CustomItemConfig();
        //create Classes
    }
    public void linkAll(Main main){
        this.main = main;
        commandMenu.link(this);
        mageSpellsAPI.link(this);
        titleManagerAPI.link(this);
        worldEditAPI.link(this);
        particleAPI.link(this);
        vaultAPI.link(this);
        messages.link(this);
        configClass.link(main);
        playerConfig.link(main);
        spellConfig.link(main);
        teamConfig.link(main);
        wandConfig.link(main);
        notes.link(main);
        exampleConfig.link(main);
        customItemConfig.link(main);
        main.getCommand("mage").setExecutor(commandMenu);
    }
    public boolean InitilizePlugin(){
        //Hook Plugins in
        if(configClass.loadDefaultConfig()) {
            configClass.loadOtherConfigs();
            customItemConfig.loadCustomItemConfigs();
            playerConfig.checkFolder();
            spellConfig.loadSpellConfigs();
            wandConfig.loadWandConfigs();
            teamConfig.loadTeamConfigs();
            notes.exportNotes();
            exampleConfig.exportExamples();
            vaultAPI.setupEconomy();
            return true;
        }else{
            return false;
        }
    }
}
