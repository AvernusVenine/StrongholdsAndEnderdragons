package avernusvenine.sne.items.interactable;

import avernusvenine.sne.Globals.ActionType;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.enchantments.SneEnchantment;
import avernusvenine.sne.items.SneItem;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Interactable extends SneItem {

    protected List<SneEnchantment> enchantments = new ArrayList<>();

    protected int basicCooldown;
    protected int specialCooldown;
    protected InventorySlot slot;

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

    public void onItemUse(Player player, Entity entity, ActionType type, Event event){
        for(SneEnchantment enchantment : enchantments){
            enchantment.use(player, entity, type, event);
        }
    }
}
