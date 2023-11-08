package avernusvenine.sne.gui.utility;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.professions.Cooking;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class CookingGUI extends DefaultGUI {

    private final int[] craftingSlots = new int[]{2,6,10,16,20,24};
    private final int[] blankSlots = new int[]{0,1,3,4,5,7,8,9,11,12,14,15,17,18,19,21,22,23,25,26};
    private final int outputSlot = 13;
    private final ItemStack outputItem = createGUIItem(Material.WHITE_STAINED_GLASS_PANE, Component.text(" "), new ArrayList<>(), "blank");
    private final BukkitTask task;

    public CookingGUI(Player player){
        owner = player;
        id = "cooking";
        title = "Cooking";
        inventory = Bukkit.createInventory(null, 27, title);
        initializeItems();

        task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, () -> {
            List<ItemStack> craftingItems = new ArrayList<>();

            for(int i : craftingSlots){
                ItemStack craftingItem = inventory.getItem(i);

                if(craftingItem != null && craftingItem.getType() != Material.AIR)
                    craftingItems.add(craftingItem);
            }

            boolean valid = Cooking.checkRecipes(craftingItems, inventory.getItem(outputSlot), owner);
            if(!valid)
                inventory.setItem(outputSlot, outputItem);
        },0, 1);
    }

    @Override
    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, Component.text(" "), new ArrayList<>(), "blank");

        for(int i : blankSlots)
            inventory.setItem(i, blankItem);

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
        task.cancel();
    }

    @Override
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){
        if(!event.getView().getOriginalTitle().equals(title) || !owner.equals(event.getWhoClicked()))
            return;

        ItemStack item = event.getCurrentItem();

        if(item == null || item.getType() == Material.AIR)
            return;

        NBTItem nbtItem = new NBTItem(item);

        if(nbtItem.getString(nbtID).equalsIgnoreCase("blank")){
            event.setCancelled(true);
            return;
        }

        if(event.getSlot() == outputSlot){
            event.getWhoClicked().getInventory().addItem(event.getInventory().getItem(outputSlot));
            for(int i : craftingSlots) {
                ItemStack regent = event.getInventory().getItem(i);
                if(regent == null)
                    continue;

                if(regent.getType() == Material.WATER_BUCKET || regent.getType() == Material.LAVA_BUCKET || regent.getType()
                        == Material.MILK_BUCKET) {
                    regent.setType(Material.BUCKET);
                    continue;
                }

                if(regent.getType() != Material.AIR)
                    regent.setAmount(regent.getAmount() - 1);
            }
            event.getInventory().setItem(outputSlot, outputItem);
            event.setCancelled(true);
        }
    }
}
