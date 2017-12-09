package me.L2_Envy.MSRM.GUI;

import com.mojang.authlib.yggdrasil.response.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class UserInterface {
    private Player player;
    private Inventory inventory;
    private boolean protection;
    public UserInterface(Player player){
        this.player = player;
        protection = true;
    }
    public UserInterface(Player player, boolean protection){
        this.player = player;
        this.protection = protection;
    }
    public Player getPlayer(){
        return player;
    }
    public void close(){
        if(player.getOpenInventory() != null){
            player.closeInventory();
        }
    }
    public boolean hasProtection(){
        return protection;
    }
    protected void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    protected Inventory getInventory(){
        return inventory;
    }
    public abstract Inventory loadInventory();
    public abstract void chooseIndex(int slot);
}
