package avernusvenine.sne.items.misc;

import avernusvenine.sne.items.CustomItem;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

import java.util.ArrayList;
import java.util.List;

public class Midas extends CustomItem{

    public Midas() {
        super();

        id = "midas";
        material = Material.LEATHER_HORSE_ARMOR;
        slot = InventorySlot.MAIN_HAND;

        basicCooldown = timeToTicks(0, 0, 1);

        String displayName = ChatColor.GOLD + "" + ChatColor.MAGIC + "A" + ChatColor.RESET
                + ChatColor.GOLD + ChatColor.BOLD + "Hand of King Midas" + ChatColor.RESET
                + ChatColor.GOLD + ChatColor.MAGIC + "A";

        List<String> lore = new ArrayList<String>();
        String loreOne = ChatColor.GOLD + "" + ChatColor.BOLD + "Hand of the cursed king Midas";
        String loreTwo = ChatColor.GRAY + "" + ChatColor.ITALIC + "Turn a mob into solid gold (Cooldown: 1h)";
        lore.add(loreOne);
        lore.add(loreTwo);

        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = CustomItem.generateItem(material, 1, displayName, lore, itemFlags, true, id);
    }

    public void leftClickAtEntity(Player player, Entity entity){

        if(onCooldown(new NBTItem(player.getInventory().getItemInMainHand()), UseType.BASIC, basicCooldown)) {
            alertCooldown(player, item, UseType.BASIC);
            return;
        }

        if(!(StrongholdsAndEnderdragons.basicEntities.contains(entity.getType())))
            return;

        Damageable damageable = (Damageable) entity;
        damageable.damage(999999);

        int amount = (int) (Math.random() * 8) + 1;
        ItemStack goldIngots = new ItemStack(Material.GOLD_INGOT, amount);
        amount = (int) (Math.random() * 17) + 1;
        ItemStack goldNuggets = new ItemStack(Material.GOLD_NUGGET, amount);

        player.getWorld().dropItem(entity.getLocation(), goldIngots);
        player.getWorld().dropItem(entity.getLocation(), goldNuggets);

        player.getWorld().playSound(player, Sound.ENTITY_ITEM_BREAK, 1, 0.5f);
    }

}
