package me.L2_Envy.MSRM;

import me.L2_Envy.MSRM.Core.Objects.CustomItemObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

/**
 * Created by Daniel on 7/23/2016.
 */
public class Utils {
    private Main main;
    public ArrayList<CustomItemObject> customitems;
    public Utils(Main main){
        this.main = main;
        customitems = new ArrayList<>();
    }
    public void addCustomItemObject(CustomItemObject customItemObject){
        customitems.add(customItemObject);
    }
    public String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public Player getOnlinePlayerFromName(String name){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getName().equalsIgnoreCase(name)){
                return player;
            }
        }
        return null;
    }
    public boolean itemStackIsCustomItem(ItemStack itemStack){
        for(CustomItemObject customItemObject : customitems){
            return itemStack.equals(customItemObject.getCustomitem());
        }
        return false;
    }
    public String getPlayerNameFromUUID(UUID uuid){
        for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
            if(offlinePlayer.getUniqueId().equals(uuid)){
                return offlinePlayer.getName();
            }
        }
        return null;
    }
    public OfflinePlayer getOfflinePlayerFromUUID(UUID uuid){
        for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
            if(offlinePlayer.getUniqueId().equals(uuid)){
                return offlinePlayer;
            }
        }
        return null;
    }
    public OfflinePlayer getOfflinePlayerFromName(String name){
        for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
            if(offlinePlayer.getName().equalsIgnoreCase(name)){
                return offlinePlayer;
            }
        }
        return null;
    }
    public ItemStack getItemStack(String item){
        //Check Custom Items first!!!
        ItemStack itemStack = null;
        for(CustomItemObject customItemObject : customitems) {
            if(item.equalsIgnoreCase(customItemObject.getItemname().toLowerCase())) {
                itemStack = customItemObject.getCustomitem();
            }
        }
        if(itemStack == null){
            item = item.toUpperCase();
            short dmg = 0;
            Material material;
            if (item.contains(":")) {
                dmg = Short.parseShort(item.split(":")[1]);
                material = Material.getMaterial(item.split(":")[0]);
            } else if (item.contains("-")) {
                dmg = Short.parseShort(item.split("-")[1]);
                material = Material.getMaterial(item.split("-")[0]);
            } else {
                material = Material.getMaterial(item);
            }

            itemStack = new ItemStack(material, 1, dmg);
        }
        return itemStack;
    }
    public ItemStack getItemStack(String item, int amount){
        ItemStack itemStack =  getItemStack(item);
        itemStack.setAmount(amount);
        return itemStack;
    }
    public ItemStack getItemStack(String item, String displayname){
        ItemStack itemStack =  getItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(colorize(displayname));
        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getItemStack(String item, String displayname, String lore){
        ItemStack itemStack =  getItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(colorize(displayname));
        if(lore.contains("/")){
            String[] lorelines = lore.split("/");
            for(int i = 0; i < lorelines.length; i++){
                lorelines[i] = colorize(lorelines[i]);
            }
            itemMeta.setLore(Arrays.asList(lorelines));
        }else {
            itemMeta.setLore(Arrays.asList(lore));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getItemStack(String item, String displayname, String lore, int amount){
        ItemStack itemStack =  getItemStack(item,displayname,lore);
        itemStack.setAmount(amount);
        return itemStack;
    }
    public ShapedRecipe loadRecipie(String[] materials, ItemStack result) {
        String row1 = "";
        String row2 = "";
        String row3 = "";
        ShapedRecipe recipe = new ShapedRecipe(result);
        HashMap<ItemStack, Character> symMap = new HashMap<>();
        char[] symbols = {'!', '@', '#', '$', '%', '^', '&', '*', '('};

        for (String mat : materials) {
            ItemStack item = getItemStack(mat);
            char symToUse = '!';

            if (symMap.containsKey(item)) {
                // Use same char symbol if it is already there
                symToUse = symMap.get(item);
            } else {
                // If not find a new one to use
                for (char c : symbols) {
                    if (!symMap.values().contains(c)) {
                        if (item.getType() == Material.AIR) {
                            symToUse = ' ';
                        } else {
                            symToUse = c;
                        }
                        break;
                    }
                }
                symMap.put(item, symToUse);
            }
            // Add the chars to the strings
            if (row1.length() < 3) {
                row1 = row1 + symToUse;
            } else if (row2.length() < 3) {
                row2 = row2 + symToUse;
            } else if (row3.length() < 3) {
                row3 = row3 + symToUse;
            }
        }

        for (ItemStack i : symMap.keySet()) {
            if (i.getType() == Material.AIR) {
                row1 = row1.replaceAll(String.valueOf(symMap.get(i)), " ");
                row2 = row2.replaceAll(String.valueOf(symMap.get(i)), " ");
                row3 = row3.replaceAll(String.valueOf(symMap.get(i)), " ");
            }
        }
        recipe.shape(row1, row2, row3);

        // Set all ingredients
        for (ItemStack i : symMap.keySet()) {
            if (i.getType() != Material.AIR) {
                recipe.setIngredient(symMap.get(i), i.getType());
            }
        }
        main.getServer().addRecipe(recipe);
        return recipe;
    }

}
