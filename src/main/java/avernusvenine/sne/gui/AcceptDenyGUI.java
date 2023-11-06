package avernusvenine.sne.gui;

import avernusvenine.sne.PlayerDictionary;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class AcceptDenyGUI extends DefaultGUI{

    protected boolean playerChoice = false;

    protected abstract void onAccept(Player player);
    protected abstract void onDeny(Player player);


    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>());
        inventory.setItem(4, blankItem);

        ItemStack denyItem = createGUIItem(Material.RED_STAINED_GLASS_PANE, "DENY", new ArrayList<>(), "deny");
        for(int i = 0; i < 4; i++)
            inventory.setItem(i, denyItem);

        ItemStack acceptItem = createGUIItem(Material.LIME_STAINED_GLASS_PANE, "ACCEPT", new ArrayList<>(), "accept");
        for(int i = 5; i < 9; i++)
            inventory.setItem(i, acceptItem);
    }


    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){
        if(!event.getView().getOriginalTitle().equals(title) || !owner.equals(event.getPlayer()))
            return;

        if(playerChoice)
            onAccept((Player) event.getPlayer());
        else
            onDeny((Player) event.getPlayer());

        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

        if(!event.getView().getOriginalTitle().equals(title))
            return;

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        if(item == null || item.getType().isAir())
            return;

        NBTItem nbtItem = new NBTItem(item);

        if(nbtItem.getString(nbtID).equals("deny"))
            playerChoice = false;
        else if(nbtItem.getString(nbtID).equals("accept"))
            playerChoice = true;
        else
            return;

        player.closeInventory();
    }
}
