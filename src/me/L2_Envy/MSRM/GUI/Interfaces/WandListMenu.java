package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.GUI.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WandListMenu extends UserInterface {
    public WandListMenu(Player player){
        super(player);
    }
    @Override
    public Inventory loadInventory() {
        return null;
    }

    @Override
    public void chooseIndex(int slot) {

    }
}
