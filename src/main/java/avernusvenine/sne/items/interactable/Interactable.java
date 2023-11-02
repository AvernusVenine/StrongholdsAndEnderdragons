package avernusvenine.sne.items.interactable;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.items.SneItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Interactable extends SneItem {

    public static ItemStack generateInteractableItem(Material material, int amount, TextComponent displayName, List<TextComponent> lore,
                                                     List<ItemFlag> itemFlags, boolean unbreakable, String id){

        ItemStack item = generateItem(material, amount, displayName, lore, itemFlags, unbreakable, id);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setLong(NBTFlags.itemBasic, 0L);
        nbtItem.setLong(NBTFlags.itemSpecial, 0L);

        return nbtItem.getItem();
    }

    public static ItemStack generateInteractableItem(Material material, int amount, String displayName, List<String> lore,
                                                     Map<Enchantment, Integer> enchantment, List<ItemFlag> itemFlags,
                                                     boolean unbreakable, String id){
        ItemStack item = generateItem(material, amount, displayName, lore, enchantment, itemFlags, unbreakable, id);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setLong(NBTFlags.itemBasic, 0L);
        nbtItem.setLong(NBTFlags.itemSpecial, 0L);

        return nbtItem.getItem();
    }

}
