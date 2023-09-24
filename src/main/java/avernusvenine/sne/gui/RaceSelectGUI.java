package avernusvenine.sne.gui;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.races.DefaultRace;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RaceSelectGUI extends DefaultGUI implements Listener {

    public RaceSelectGUI(){
        id = "race_select";
        title = "Race Selection";
        inventory = Bukkit.createInventory(null, 27, title);
        initializeItems();
    }

    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<String>());
        int[] blankSlots = new int[]{0,8,9,10,11,15,16,17,18,19,20,21,23,24,25,26};

        for(int i : blankSlots)
            inventory.setItem(i, blankItem);

        inventory.setItem(22, createGUIItem(Material.RED_STAINED_GLASS_PANE,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "BACK", new ArrayList<String>(), "back"));

        inventory.setItem(1, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "DWARF", new ArrayList<String>(), "dwarf"));
        inventory.setItem(2, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "DRAGONKIN", new ArrayList<String>(), "dragon_kin"));
        inventory.setItem(3, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.AQUA + "" + ChatColor.BOLD + "ELF", new ArrayList<String>(), "elf"));
        inventory.setItem(4, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.GOLD + "" + ChatColor.BOLD + "FELIDAE", new ArrayList<String>(), "felidae"));
        inventory.setItem(5, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.YELLOW + "" + ChatColor.BOLD + "GNOME", new ArrayList<String>(), "gnome"));
        inventory.setItem(6, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "HALF ELF", new ArrayList<String>(), "half_elf"));
        inventory.setItem(7, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.GREEN + "" + ChatColor.BOLD + "HALF ORC", new ArrayList<String>(), "half_orc"));
        inventory.setItem(12, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.WHITE + "" + ChatColor.BOLD + "HUMAN", new ArrayList<String>(), "human"));
        inventory.setItem(13, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "ORC", new ArrayList<String>(), "orc"));
        inventory.setItem(14, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.RED + "" + ChatColor.BOLD + "TIEFLING", new ArrayList<String>(), "tiefling"));
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

        DefaultRace.RaceType type;

        switch(nbtItem.getString(nbtID)){
            default:
                return;
            case "back":
                player.openInventory(StrongholdsAndEnderdragons.guiDictionary.get("class_select").getInventory());
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
                return;
            case "dwarf":
                type = DefaultRace.RaceType.DWARF;
                break;
            case "dragon_kin":
                type = DefaultRace.RaceType.DRAGON_KIN;
                break;
            case "elf":
                type = DefaultRace.RaceType.ELF;
                break;
            case "felidae":
                type = DefaultRace.RaceType.FELIDAE;
                break;
            case "gnome":
                type = DefaultRace.RaceType.GNOME;
                break;
            case "half_elf":
                type = DefaultRace.RaceType.HALF_ELF;
                break;
            case "half_orc":
                type = DefaultRace.RaceType.HALF_ORC;
                break;
            case "human":
                type = DefaultRace.RaceType.HUMAN;
                break;
            case "orc":
                type = DefaultRace.RaceType.ORC;
                break;
            case "tiefling":
                type = DefaultRace.RaceType.TIEFLING;
                break;
        }

        StrongholdsAndEnderdragons.playerCharacters.get(player.getUniqueId().toString()).setRaceType(type);
        player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
        new NameSelectGUI(player);
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
