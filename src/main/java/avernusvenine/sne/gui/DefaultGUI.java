package avernusvenine.sne.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DefaultGUI {

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


    // Getters and setters

    public Inventory getInventory(){
        return inventory;
    }

    public String getID(){
        return id;
    }

}
