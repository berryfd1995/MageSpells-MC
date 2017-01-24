package me.L2_Envy.MSRM.Core;


import me.L2_Envy.MSRM.Core.Binding.BindingManager;
import me.L2_Envy.MSRM.Core.Casting.CastingManager;
import me.L2_Envy.MSRM.Core.Effects.DamageEffectManager;
import me.L2_Envy.MSRM.Core.Effects.ParticleEffectManager;
import me.L2_Envy.MSRM.Core.Effects.PotionEffectManager;
import me.L2_Envy.MSRM.Core.Effects.SpellEffectManager;
import me.L2_Envy.MSRM.Core.GUI.*;
import me.L2_Envy.MSRM.Core.Learning.SpellLearningManager;
import me.L2_Envy.MSRM.Core.LevelingSystem.LevelingManager;
import me.L2_Envy.MSRM.Core.Mana.ManaManager;
import me.L2_Envy.MSRM.Core.Players.MageManager;
import me.L2_Envy.MSRM.Core.SpellBook.SpellBookManager;
import me.L2_Envy.MSRM.Core.Spells.ActiveSpellManager;
import me.L2_Envy.MSRM.Core.Spells.SpellContactManager;
import me.L2_Envy.MSRM.Core.Spells.SpellManager;
import me.L2_Envy.MSRM.Core.Teams.TeamManager;
import me.L2_Envy.MSRM.Core.Wands.WandManager;
import me.L2_Envy.MSRM.Main;

/**
 * Created by Daniel on 7/23/2016.
 */
public class MageSpellsManager {
    public Main main;
    public BindingManager bindingManager;
    public CastingManager castingManager;
    public DamageEffectManager damageEffectManager;
    public ParticleEffectManager particleEffectManager;
    public PotionEffectManager potionEffectManager;
    public SpellEffectManager spellEffectManager;
    public PlayerInterface playerInterface;
    public SpellUI spellUI;
    public WandUI wandUI;
    public SpellInfoUI spellInfoUI;
    public WandInfoUI wandInfoUI;
    public WandBag wandBag;
    public MageStats mageStats;
    public BindingMenu bindingMenu;
    public SpellLearningManager spellLearningManager;
    public LevelingManager levelingManager;
    public ManaManager manaManager;
    public MageManager mageManager;
    public SpellBookManager spellBookManager;
    public ActiveSpellManager activeSpellManager;
    public SpellContactManager spellContactManager;
    public SpellManager spellManager;
    public TeamManager teamManager;
    public WandManager wandManager;
    private boolean nodesystemenabled;
    public MageSpellsManager(){
        bindingManager = new BindingManager();
        castingManager = new CastingManager();
        damageEffectManager = new DamageEffectManager();
        particleEffectManager = new ParticleEffectManager();
        potionEffectManager = new PotionEffectManager();
        spellEffectManager = new SpellEffectManager();
        playerInterface = new PlayerInterface();
        spellUI = new SpellUI();
        wandBag = new WandBag();
        mageStats = new MageStats();
        wandUI = new WandUI();
        spellInfoUI = new SpellInfoUI();
        wandInfoUI = new WandInfoUI();
        bindingMenu = new BindingMenu();
        castingManager = new CastingManager();
        spellLearningManager = new SpellLearningManager();
        levelingManager = new LevelingManager();
        manaManager = new ManaManager();
        mageManager = new MageManager();
        spellBookManager = new SpellBookManager();
        activeSpellManager = new ActiveSpellManager();
        spellContactManager = new SpellContactManager();
        spellManager = new SpellManager();
        teamManager = new TeamManager();
        wandManager = new WandManager();
    }
    public void linkAll(Main main){
        this.main = main;
        bindingManager.link(this);
        castingManager.link(this);
        damageEffectManager.link(this);
        particleEffectManager.link(this);
        potionEffectManager.link(this);
        spellEffectManager.link(this);
        playerInterface.link(this);
        spellUI.link(this);
        spellInfoUI.link(this);
        wandInfoUI.link(this);
        wandBag.link(this);
        mageStats.link(this);
        wandUI.link(this);
        bindingMenu.link(this);
        castingManager.link(this);
        spellLearningManager.link(this);
        levelingManager.link(this);
        manaManager.link(this);
        mageManager.link(this);
        spellBookManager.link(this);
        activeSpellManager.link(this);
        spellContactManager.link(this);
        spellManager.link(this);
        teamManager.link(this);
        wandManager.link(this);
    }
    public boolean InitilizePlugin(){

        return true;
    }
    public boolean isNodeSystemEnabled(){
        return nodesystemenabled;
    }
    public void setNodeSystemEnabled(boolean enabled){
        this.nodesystemenabled = enabled;
    }

}
