package me.L2_Envy.MSRM.GUI;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GUIManager {
    private HashMap<Player, UserInterface> playerUserInterfaceHashMap;
    public GUIManager(){
        playerUserInterfaceHashMap = new HashMap<>();
    }
    public void openUserInterface(Player player, UserInterface userInterface){
        removeFromMap(player);
        player.openInventory(userInterface.loadInventory());
        playerUserInterfaceHashMap.put(player,userInterface);
    }
    public void closeUserInterface(Player player){
        if(inInterface(player)){
            playerUserInterfaceHashMap.get(player).close();
            playerUserInterfaceHashMap.remove(player);
        }
    }
    public boolean removeFromMap(Player player){
        if(inInterface(player)) {
            playerUserInterfaceHashMap.remove(player);
            return true;
        }
        return false;
    }
    public boolean inInterface(Player player){
        return playerUserInterfaceHashMap.containsKey(player);
    }
    public void selectSlot(Player player, int slot){
        playerUserInterfaceHashMap.get(player).chooseIndex(slot);
    }
    public void closeInterfaces(){
        for(Player player : playerUserInterfaceHashMap.keySet()){
            playerUserInterfaceHashMap.get(player).close();
        }
    }
    public boolean hasProtectionModeEnabled(Player player){
        if(inInterface(player)){
            return playerUserInterfaceHashMap.get(player).hasProtection();
        }
        //Not in my interface, so who knows if he does. Not me
        return false;
    }

}
