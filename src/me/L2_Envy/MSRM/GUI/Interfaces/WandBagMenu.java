package me.L2_Envy.MSRM.GUI.Interfaces;

import me.L2_Envy.MSRM.GUI.UserInterface;
import me.L2_Envy.MSRM.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WandBagMenu extends UserInterface {
    public WandBagMenu(Player player){
        super(player, false);
    }
    @Override
    public Inventory loadInventory() {
        return Main.getMageSpellsManager().mageManager.getMage(getPlayer().getUniqueId()).getInventory();
    }

    @Override
    public void chooseIndex(int slot) {

    }
}
