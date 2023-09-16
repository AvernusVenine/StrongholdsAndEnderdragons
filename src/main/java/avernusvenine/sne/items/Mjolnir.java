package avernusvenine.sne.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class Mjolnir extends CustomItem {

    public static void init() {
        id = "mjolnir";

        String displayName = ChatColor.GRAY + "" + ChatColor.MAGIC + "AA" + ChatColor.RESET
                + ChatColor.GRAY + "" + ChatColor.BOLD + "Mjolnir" + ChatColor.RESET
                + ChatColor.GRAY + "" + ChatColor.MAGIC + "AA";

        List<String> lore = new ArrayList<String>();
        lore.add("The wrath of Thor");

        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = CustomItem.generateItem(Material.IRON_AXE, 1, displayName, lore, itemFlags);
    }

    public static void useItem(PlayerInteractEvent event){
        event.getPlayer();
    }


}
