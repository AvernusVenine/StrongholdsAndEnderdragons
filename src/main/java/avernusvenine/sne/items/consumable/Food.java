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

public class Food extends SneItem implements Listener {

    protected float foodLevel;
    protected float saturation;

    public static ItemStack generateFoodItem(Material material, int amount, TextComponent displayName, List<TextComponent> lore,
                                 List<ItemFlag> itemFlags, String id, float foodLevel, float saturation){
        ItemStack item = generateItem(material, amount, displayName, lore, itemFlags, false, id);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setFloat(NBTFlags.foodLevel, foodLevel);
        nbtItem.setFloat(NBTFlags.saturation, saturation);

        return nbtItem.getItem();
    }

    public void onConsumption(Player player){

    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event){

        NBTItem nbtItem = new NBTItem(event.getItem());

        if(!id.equalsIgnoreCase(nbtItem.getString(NBTFlags.itemID)))
            return;

        Player player = event.getPlayer();
        event.setCancelled(true);

        Food food = (Food) ItemDictionary.get(id);
        food.onConsumption(player);

        player.setFoodLevel((int) Math.min(nbtItem.getFloat(NBTFlags.foodLevel) + foodLevel, 20));
        player.setSaturation(Math.min(nbtItem.getFloat(NBTFlags.saturation) + saturation, 20));
    }
}
