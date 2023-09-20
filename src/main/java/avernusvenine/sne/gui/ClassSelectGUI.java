package avernusvenine.sne.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectGUI extends DefaultGUI implements Listener {

    public ClassSelectGUI(){
        id = "class_select";
        title = "Class Selection";
        inventory = Bukkit.createInventory(null, 27, title);
        initializeItems();
    }

    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, "", new ArrayList<String>());
        int[] blankSlots = new int[]{0,8,9,17,18,19,20,24,25,26};

        for(int i : blankSlots)
            inventory.setItem(i, blankItem);

        inventory.setItem(1, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "ARTIFICER", new ArrayList<String>()));
        inventory.setItem(2, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "BARBARIAN", new ArrayList<String>()));
        inventory.setItem(3, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "BLOOD HUNTER", new ArrayList<String>()));
        inventory.setItem(4, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "BARD", new ArrayList<String>()));
        inventory.setItem(5, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "CLERIC", new ArrayList<String>()));
        inventory.setItem(6, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "DRUID", new ArrayList<String>()));
        inventory.setItem(7, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "FIGHTER", new ArrayList<String>()));
        inventory.setItem(10, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "MONK", new ArrayList<String>()));
        inventory.setItem(11, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "PALADIN", new ArrayList<String>()));
        inventory.setItem(12, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "RANGER", new ArrayList<String>()));
        inventory.setItem(13, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "ROGUE", new ArrayList<String>()));
        inventory.setItem(14, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "SORCERER", new ArrayList<String>()));
        inventory.setItem(15, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "WARLOCK", new ArrayList<String>()));
        inventory.setItem(16, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BOLD + "WIZARD", new ArrayList<String>()));

    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

        if(!event.getInventory().equals(inventory))
            return;

        ItemStack item = event.getCurrentItem();

        if(item == null || item.getType().isAir())
            return;

        Player player = (Player) event.getWhoClicked();
        player.sendMessage(item.displayName());
    }

    @EventHandler
    public void onInventoryEvent(final InventoryInteractEvent event){
        if(!event.getInventory().equals(inventory))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event){

        if(event.getInventory().equals(inventory))
            event.setCancelled(true);
    }

}
