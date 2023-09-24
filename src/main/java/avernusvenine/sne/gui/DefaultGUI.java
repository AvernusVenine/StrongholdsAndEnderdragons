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

public class DefaultGUI implements Listener {

    protected static String nbtID = "GuiID";

    protected Inventory inventory;
    protected String id;
    protected String title;

    public DefaultGUI() {
        id = "default";
        title = "Default";
        inventory = Bukkit.createInventory(null, 27, title);
        initializeItems();
    }

    public void initializeItems(){

    }

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


    // Getters and setters

    public Inventory getInventory(){
        return inventory;
    }

    public String getID(){
        return id;
    }
}
