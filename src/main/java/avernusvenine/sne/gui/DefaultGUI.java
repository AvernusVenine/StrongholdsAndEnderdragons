package avernusvenine.sne.gui;

import avernusvenine.sne.PlayerDictionary;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class DefaultGUI implements Listener {

    /*
     * ####CUSTOM MODEL CHEAT SHEET####
     * 1 -> Blank Paper

     * */

    protected final static String nbtID = "GuiID";

    protected Player owner;

    protected Inventory inventory;
    protected String id;
    protected String title;

    public DefaultGUI() {
    }

    public abstract void initializeItems();

    public static ItemStack createGUIItem(final Material material, final String name, final List<String> lore){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGUIItem(final Material material, final String name, final List<String> lore, String id){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static ItemStack createGUIItem(final Material material, final String name, final List<String> lore, String id,
                                          int model){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(model);
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static ItemStack createGUIItem(final Material material, final TextComponent displayName, final List<TextComponent> lore){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGUIItem(final Material material, final TextComponent displayName, final List<TextComponent> lore,
                                          String id, int model){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.lore(lore);
        meta.setCustomModelData(model);
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static ItemStack createGUIItem(final Material material, final TextComponent displayName, final List<TextComponent> lore, String id){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.lore(lore);
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static ItemStack createGUIItem(final ItemStack item, final String id){
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static String convertWidthToMinecraftCode(int width){

        int code = 0xD0000 + width;
        int highSurrogate = 0xD800 + ((code - 0x10000) >> 10);
        int lowSurrogate = 0xDC00 + ((code - 0x10000) & 0x3FF);

        return new String(new int[]{highSurrogate, lowSurrogate}, 0, 2);
    }

    // Getters and setters

    public Inventory getInventory(){
        return inventory;
    }

    public String getID(){
        return id;
    }


    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){
        if(!event.getView().getOriginalTitle().equals(title) || !owner.equals(event.getPlayer()))
            return;

        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

        if(!event.getView().getOriginalTitle().equals(title))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryEvent(final InventoryInteractEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        event.setCancelled(true);
    }

}
