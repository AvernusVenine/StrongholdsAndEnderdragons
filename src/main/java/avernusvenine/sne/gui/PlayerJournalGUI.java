package avernusvenine.sne.gui;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerJournalGUI extends DefaultGUI{

    public PlayerJournalGUI(){
        id = "player_journal";
        title = convertWidthToMinecraftCode(-12) + "\uE238";
        inventory = Bukkit.createInventory(null, 54, ChatColor.WHITE + title);
        initializeItems();
    }

    public void initializeItems(){
        System.out.println(inventory.getSize());

        inventory.setItem(8, createGUIItem(Material.PAPER,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "BACK", new ArrayList<>(), "back", 1));

        int[] socialSlots = new int[]{5,6,7,14,15,16,17};
        ItemStack socialItem = createGUIItem(Material.PAPER, ChatColor.GRAY + "" + ChatColor.BOLD + "SOCIAL",
                new ArrayList<>(), "social", 1);

        for(int i : socialSlots)
            inventory.setItem(i, socialItem);

        int[] questSlots = new int[]{23,24,25,26,32,33,34,35};
        ItemStack questItem = createGUIItem(Material.PAPER, ChatColor.GRAY + "" + ChatColor.BOLD + "QUESTS",
                new ArrayList<>(), "quest", 1);

        for(int i : questSlots)
            inventory.setItem(i, questItem);

        int[] milestoneSlots = new int[]{41,42,43,44,50,51,52,53};
        ItemStack milestoneItem = createGUIItem(Material.PAPER, ChatColor.GRAY + "" + ChatColor.BOLD + "MILESTONES",
                new ArrayList<>(), "milestone", 1);

        for(int i : milestoneSlots)
            inventory.setItem(i, milestoneItem);

        int[] wikiSlots = new int[]{45,46,47,48};
        ItemStack wikiItem = createGUIItem(Material.PAPER, ChatColor.GRAY + "" + ChatColor.BOLD + "WIKI",
                new ArrayList<>(), "wiki", 1);

        for(int i : wikiSlots)
            inventory.setItem(i, wikiItem);

    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

        if(!event.getView().getOriginalTitle().equals(title))
            return;

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if(item == null || item.getType().isAir())
            return;

        event.setCancelled(true);

        NBTItem nbtItem = new NBTItem(item);

        switch(nbtItem.getString(nbtID)){
            default:
                return;
            case "back":
                player.closeInventory();
                return;
            case "social":
                player.sendMessage("Feature coming soon!");
                break;
            case "quest":
                player.sendMessage("Feature coming soon!");
                break;
            case "milestone":
                player.sendMessage("Feature coming soon!");
                break;
            case "wiki":
                player.sendMessage("Feature coming soon!");
                break;
        }
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
