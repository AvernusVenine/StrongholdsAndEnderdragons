package avernusvenine.sne.items;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static org.bukkit.Bukkit.*;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CustomItem {

    protected static ItemStack item;
    protected static String id;

    public static void init() {
        id = "default_custom_item";
        item = generateItem();
    }

    public static ItemStack generateItem(){
        ItemStack tempItem = new ItemStack(Material.STICK, 1);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName("Default CustomItem (You shouldn't have gotten this!)");

        List<String> lore = new ArrayList<>();
        lore.add("Please report this as a bug along with how it happened!");

        meta.setLore(lore);

        tempItem.setItemMeta(meta);
        return tempItem;
    }

    public static ItemStack generateItem(Material material, int amount, String displayName, List<String> lore,
                                         @NotNull List<ItemFlag> itemFlags){
        ItemStack tempItem = new ItemStack(material, amount);

        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        for(int i = 0; i < itemFlags.size(); i++){
            meta.addItemFlags(itemFlags.get(i));
        }

        tempItem.setItemMeta(meta);

        return tempItem;
    }

    public static ItemStack generateItem(Material material, int amount, String displayName, List<String> lore,
                                  @NotNull List<Enchantment> enchantment, @NotNull List<Integer> enchantmentStrength,
                                  @NotNull List<ItemFlag> itemFlags) {
        ItemStack tempItem = new ItemStack(material, amount);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        // Adds given Enchantments to the item
        for(int i = 0; i < enchantment.size(); i++){
            meta.addEnchant(enchantment.get(i), enchantmentStrength.get(i), false);
        }

        // Adds given ItemFlags to the item
        for(int i = 0; i < itemFlags.size(); i++){
            meta.addItemFlags(itemFlags.get(i));
        }

        tempItem.setItemMeta(meta);

        return tempItem;
    }

    public static void useItem(PlayerInteractEvent event){

    }


    // Getters and Setters


    public static String getID(){
        return id;
    }

    public static ItemStack getCustomItem(){
        return item;
    }


    // Event Handler

    protected static class CustomItemEvent implements Listener {

        @EventHandler
        public void onEvent(PlayerInteractEvent event){

            ItemStack eventItem = event.getItem();

            if (eventItem != null){
                if (StrongholdsAndEnderdragons.customItemDictionary.inverse().get(eventItem) != null){

                    if(eventItem.equals(Mjolnir.getCustomItem())) Mjolnir.useItem(event);

                }
            }
        }

    }

}
