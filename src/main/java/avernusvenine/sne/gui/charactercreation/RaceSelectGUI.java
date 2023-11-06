package avernusvenine.sne.gui.charactercreation;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.gui.charactercreation.ClassSelectGUI;
import avernusvenine.sne.gui.charactercreation.NameSelectGUI;
import avernusvenine.sne.races.Race;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RaceSelectGUI extends DefaultGUI implements Listener {

    public RaceSelectGUI(Player player){
        id = "race_select";
        title = "Race Selection";
        inventory = Bukkit.createInventory(null, 27, title);
        owner = player;
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


    @Override
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

        Race.RaceType type;

        switch(nbtItem.getString(nbtID)){
            default:
                return;
            case "back":
                Globals.openGUI(player, new ClassSelectGUI(player));
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
                return;
            case "dwarf":
                type = Race.RaceType.DWARF;
                break;
            case "dragon_kin":
                type = Race.RaceType.DRAGON_KIN;
                break;
            case "elf":
                type = Race.RaceType.ELF;
                break;
            case "felidae":
                type = Race.RaceType.FELIDAE;
                break;
            case "gnome":
                type = Race.RaceType.GNOME;
                break;
            case "half_elf":
                type = Race.RaceType.HALF_ELF;
                break;
            case "half_orc":
                type = Race.RaceType.HALF_ORC;
                break;
            case "human":
                type = Race.RaceType.HUMAN;
                break;
            case "orc":
                type = Race.RaceType.ORC;
                break;
            case "tiefling":
                type = Race.RaceType.TIEFLING;
                break;
        }

        PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter().setRaceType(type);
        player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
        new NameSelectGUI(player);
    }
}
