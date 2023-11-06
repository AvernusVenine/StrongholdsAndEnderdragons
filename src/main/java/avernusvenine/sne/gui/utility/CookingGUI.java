package avernusvenine.sne.gui.utility;

import avernusvenine.sne.gui.DefaultGUI;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CookingGUI extends DefaultGUI {

    private final int[] craftingSlots = new int[]{2,6,10,16,20,23};
    private final int[] blankSlots = new int[]{0,1,3,4,5,7,8,9,11,12,14,15,17,18,19,21,22,23,24,26,27};
    private final int outputSlot = 13;

    public CookingGUI(Player player){
        owner = player;
        id = "cooking";
        title = "Cooking";
        inventory = Bukkit.createInventory(null, 54, title);
        initializeItems();
    }

    @Override
    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS, Component.text(" "), new ArrayList<>(), "blank");

        for(int i : blankSlots){
            inventory.setItem(i, blankItem);
        }

        ItemStack outputItem = createGUIItem(Material.WHITE_STAINED_GLASS_PANE, Component.text(" "), new ArrayList<>(), "blank");
        inventory.setItem(outputSlot, outputItem);
    }

    @Override
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!event.getView().getOriginalTitle().equals(title) || !owner.equals(player))
            return;

        for(ItemStack item : event.getInventory().getContents()){

            if(item == null || item.getType() == Material.AIR)
                break;

            NBTItem nbtItem = new NBTItem(item);

            if(!nbtItem.getString(nbtID).equalsIgnoreCase("blank"))
                player.getInventory().addItem(item);
        }
        HandlerList.unregisterAll(this);
    }

    @Override
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        ItemStack item = event.getCurrentItem();

        if(item == null || item.getType() == Material.AIR)
            return;

        NBTItem nbtItem = new NBTItem(item);

        if(nbtItem.getString(nbtID).equalsIgnoreCase("blank"))
            return;

        List<ItemStack> currentItems = new ArrayList<>();

        for(int i : craftingSlots){
            currentItems.add(event.getInventory().getItem(i));
        }
    }
}
