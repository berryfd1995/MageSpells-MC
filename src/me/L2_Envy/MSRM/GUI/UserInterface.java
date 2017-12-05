package me.L2_Envy.MSRM.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class UserInterface {
    private Player player;
    public UserInterface(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return player;
    }
    public void close(){
        if(player.getOpenInventory() != null){
            player.closeInventory();
        }
    }
    public abstract Inventory loadInventory();
    public abstract void chooseIndex(int slot);
}
