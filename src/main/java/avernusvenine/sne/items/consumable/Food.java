package avernusvenine.sne.items.consumable;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.items.SneItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Food extends SneItem {

    protected int foodLevel = 1;
    protected int saturation = 1;

    public static ItemStack generateFoodItem(Material material, int amount, TextComponent displayName, List<TextComponent> lore,
                                 List<ItemFlag> itemFlags, String id, int foodLevel, int saturation){
        ItemStack item = generateItem(material, amount, displayName, lore, itemFlags, false, id);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger(NBTFlags.foodLevel, foodLevel);
        nbtItem.setInteger(NBTFlags.saturation, saturation);

        return nbtItem.getItem();
    }

    public void onConsumption(Player player){

    }
}
