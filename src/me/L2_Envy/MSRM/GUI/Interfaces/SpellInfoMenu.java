package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.Core.Objects.SpellObject;
import me.L2_Envy.MSRM.GUI.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SpellInfoMenu extends UserInterface {
    private SpellObject spell;
    public SpellInfoMenu(Player player, SpellObject spell){
        super(player);
        this.spell = spell;
    }
    @Override
    public Inventory loadInventory() {
        return null;
    }

    @Override
    public void chooseIndex(int slot) {

    }
}
