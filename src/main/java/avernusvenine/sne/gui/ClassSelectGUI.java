package avernusvenine.sne.gui;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.classes.DefaultClass;
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

public class ClassSelectGUI extends DefaultGUI implements Listener {

    public ClassSelectGUI(){
        id = "class_select";
        title = "Class Selection";
        inventory = Bukkit.createInventory(null, 27, title);
        initializeItems();
    }

    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<String>());
        int[] blankSlots = new int[]{0,8,9,17,18,19,20,21,23,24,25,26};

        for(int i : blankSlots)
            inventory.setItem(i, blankItem);

        inventory.setItem(22, createGUIItem(Material.RED_STAINED_GLASS_PANE,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "BACK", new ArrayList<String>(), "back"));

        inventory.setItem(1, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "ARTIFICER", new ArrayList<String>(), "artificer"));
        inventory.setItem(2, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "BARBARIAN", new ArrayList<String>(), "barbarian"));
        inventory.setItem(3, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "BARD", new ArrayList<String>(), "bard"));
        inventory.setItem(4, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLERIC", new ArrayList<String>(), "cleric"));
        inventory.setItem(5, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.GREEN + "" + ChatColor.BOLD + "DRUID", new ArrayList<String>(), "druid"));
        inventory.setItem(6, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.GRAY + "" + ChatColor.BOLD + "FIGHTER", new ArrayList<String>(), "fighter"));
        inventory.setItem(7, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.AQUA + "" + ChatColor.BOLD + "MONK", new ArrayList<String>(), "monk"));
        inventory.setItem(10, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.GOLD + "" + ChatColor.BOLD + "PALADIN", new ArrayList<String>(), "paladin"));
        inventory.setItem(11, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "RANGER", new ArrayList<String>(), "ranger"));
        inventory.setItem(12, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "ROGUE", new ArrayList<String>(), "rogue"));
        inventory.setItem(13, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.YELLOW + "" + ChatColor.BOLD + "SHAMAN", new ArrayList<String>(), "shaman"));
        inventory.setItem(14, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "SORCERER", new ArrayList<String>(), "sorcerer"));
        inventory.setItem(15, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "WARLOCK", new ArrayList<String>(), "warlock"));
        inventory.setItem(16, createGUIItem(Material.WHITE_STAINED_GLASS_PANE,
                ChatColor.BLUE + "" + ChatColor.BOLD + "WIZARD", new ArrayList<String>(), "wizard"));

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

        DefaultClass.ClassType type = DefaultClass.ClassType.DEFAULT_CLASS;

        switch(nbtItem.getString(nbtID)){
            default:
                return;
            case "back":
                CharacterSelectGUI gui = new CharacterSelectGUI(player);
                StrongholdsAndEnderdragons.plugin.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);

                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
                player.openInventory(gui.getInventory());
                return;
            case "artificer":
                type = DefaultClass.ClassType.ARTIFICER;
                break;
            case "barbarian":
                type = DefaultClass.ClassType.BARBARIAN;
                break;
            case "shaman":
                type = DefaultClass.ClassType.SHAMAN;
                break;
            case "bard":
                type = DefaultClass.ClassType.BARD;
                break;
            case "cleric":
                type = DefaultClass.ClassType.CLERIC;
                break;
            case "druid":
                type = DefaultClass.ClassType.DRUID;
                break;
            case "fighter":
                type = DefaultClass.ClassType.FIGHTER;
                break;
            case "monk":
                type = DefaultClass.ClassType.MONK;
                break;
            case "paladin":
                type = DefaultClass.ClassType.PALADIN;
                break;
            case "ranger":
                type = DefaultClass.ClassType.RANGER;
                break;
            case "rogue":
                type = DefaultClass.ClassType.ROGUE;
                break;
            case "sorcerer":
                type = DefaultClass.ClassType.SORCERER;
                break;
            case "warlock":
                type = DefaultClass.ClassType.WARLOCK;
                break;
            case "wizard":
                type = DefaultClass.ClassType.WIZARD;
                break;
        }

        PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter().setClassType(type);

        player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);

        player.openInventory(StrongholdsAndEnderdragons.guiDictionary.get("race_select").getInventory());
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
