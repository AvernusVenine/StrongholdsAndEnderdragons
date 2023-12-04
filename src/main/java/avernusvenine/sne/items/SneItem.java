package avernusvenine.sne.items;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.StrongholdsAndEnderdragons;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class SneItem {

    // Item Rarity Colors
    protected static TextColor artifactColor = TextColor.color(122, 248, 255);
    protected static TextColor legendaryColor = TextColor.color(238, 168, 17);
    protected static TextColor epicColor = TextColor.color(190, 26, 196);
    protected static TextColor rareColor = TextColor.color(14, 72, 173);
    protected static TextColor uncommonColor = TextColor.color(82, 82, 82);
    protected static TextColor commonColor = TextColor.color(170, 170, 170);

    public final static String artifact = ChatColor.AQUA + "" + ChatColor.BOLD + "Artifact";
    public final static String legendary = ChatColor.GOLD + "" + ChatColor.BOLD + "Legendary";
    public final static String epic = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Epic";
    public final static String rare = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Rare";
    public final static String uncommon = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Uncommon";
    public final static String common = ChatColor.WHITE + "" + ChatColor.BOLD + "Common";

    protected ItemStack item;
    protected Material material;
    protected String id;
    protected Rarity rarity;
    protected boolean droppable = true;

    public enum Rarity{
        GARBAGE,
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY,
        ARTIFACT
    }

    public static ItemStack setCustomModel(ItemStack item, int model){
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(model);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateItem(){
        ItemStack tempItem = new ItemStack(Material.STICK, 1);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName("Default CustomItem (You shouldn't have gotten this!)");

        List<String> lore = new ArrayList<>();
        lore.add("Please report this as a bug along with how it happened!");

        meta.setLore(lore);

        tempItem.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(tempItem);
        nbtItem.setLong(NBTFlags.itemBasic, 0L);
        nbtItem.setLong(NBTFlags.itemSpecial, 0L);
        nbtItem.setString(NBTFlags.itemID, "default_custom_item");

        return nbtItem.getItem();
    }

    public static ItemStack generateItem(Material material, int amount, TextComponent displayName, List<TextComponent> lore,
                                         List<ItemFlag> itemFlags, boolean unbreakable, String id){
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.lore(lore);

        for (ItemFlag itemFlag : itemFlags)
            meta.addItemFlags(itemFlag);

        meta.setUnbreakable(unbreakable);

        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(NBTFlags.itemID, id);

        return nbtItem.getItem();
    }

    // Getters and Setters

    public Rarity getRarity(){
        return rarity;
    }

    public String getID(){
        return id;
    }

    public ItemStack getItem(){
        return item;
    }

    public boolean isDroppable(){
        return droppable;
    }
}
