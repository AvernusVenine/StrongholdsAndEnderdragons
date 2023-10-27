package avernusvenine.sne.gui;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
}
