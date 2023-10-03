package avernusvenine.sne.items.misc;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.PlayerJournalGUI;
import avernusvenine.sne.items.SneItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class PlayerJournal extends SneItem {

    public PlayerJournal(){
        super();

        id = "player_journal";
        material = Material.LEATHER_HORSE_ARMOR;
        slot = InventorySlot.MAIN_HAND;

        String displayName = ChatColor.WHITE + "" + ChatColor.BOLD + "Player Journal";

        List<String> lore = new ArrayList<String>();

        List<ItemFlag> itemFlags = new ArrayList<>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = SneItem.generateItem(material, 1, displayName, lore, itemFlags, true, id);
    }

    public void rightClickAir(Player player){
        PlayerJournalGUI gui = new PlayerJournalGUI();
        StrongholdsAndEnderdragons.plugin.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
        player.openInventory(gui.getInventory());
    }

}
