package avernusvenine.sne.items;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static org.bukkit.Bukkit.*;


import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class CustomItem {

    public final static String nbtBasic = "BasicCooldown";
    public final static String nbtSpecial = "SpecialCooldown";
    public final static String nbtID = "CustomID";

    public final static String artifact = ChatColor.AQUA + "" + ChatColor.BOLD + "Artifact";
    public final static String legendary = ChatColor.GOLD + "" + ChatColor.BOLD + "Legendary";
    public final static String epic = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Epic";
    public final static String rare = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Rare";
    public final static String uncommon = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Uncommon";
    public final static String common = ChatColor.WHITE + "" + ChatColor.BOLD + "Common";

    protected ItemStack item;
    protected Material material;
    protected String id;

    protected int basicCooldown;
    protected int specialCooldown;
    protected InventorySlot slot;


    public enum UseType{
        BASIC,
        SPECIAL
    }

    public enum InventorySlot{
        MAIN_HAND,
        OFF_HAND,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS
    }

    public enum ItemType {
        SPELL,
        MUSICAL,
        CLOTH_ARMOR,
        LIGHT_ARMOR,
        MEDIUM_ARMOR,
        HEAVY_ARMOR,
        SHIELD,
        SIMPLE_WEAPON,
        MARTIAL_WEAPON,
        MAGICAL_WEAPON
    }


    public CustomItem() {
        id = "default_custom_item";
        material = Material.STICK;
        item = generateItem();

        specialCooldown = 0;
        basicCooldown = 0;
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
        nbtItem.setLong(nbtBasic, 0L);
        nbtItem.setLong(nbtSpecial, 0L);
        nbtItem.setString(nbtID, "default_custom_item");

        return nbtItem.getItem();
    }

    public static ItemStack generateItem(Material material, int amount, String displayName, List<String> lore,
                                         @NotNull List<ItemFlag> itemFlags, boolean unbreakable, String id){
        ItemStack tempItem = new ItemStack(material, amount);

        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        for (ItemFlag itemFlag : itemFlags) {
            meta.addItemFlags(itemFlag);
        }

        meta.setUnbreakable(unbreakable);

        tempItem.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(tempItem);
        nbtItem.setLong(nbtBasic, 0L);
        nbtItem.setLong(nbtSpecial, 0L);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static ItemStack generateItem(Material material, int amount, String displayName, List<String> lore,
                                         @NotNull Map<Enchantment, Integer> enchantment, @NotNull List<ItemFlag> itemFlags,
                                         boolean unbreakable, String id) {
        ItemStack tempItem = new ItemStack(material, amount);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        // Adds given Enchantments to the item
        for(Map.Entry<Enchantment, Integer> entry : enchantment.entrySet()){
            meta.addEnchant(entry.getKey(), entry.getValue(), false);
        }

        // Adds given ItemFlags to the item
        for (ItemFlag itemFlag : itemFlags) {
            meta.addItemFlags(itemFlag);
        }

        meta.setUnbreakable(unbreakable);

        tempItem.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(tempItem);
        nbtItem.setLong(nbtBasic, 0L);
        nbtItem.setLong(nbtSpecial, 0L);
        nbtItem.setString(nbtID, id);

        return nbtItem.getItem();
    }

    public static int timeToTicks(int s, int m, int h){
        return ((s*20) + (m*60*20) + (h*20*60*60));
    }
    public static long ticksToMillis(long t){ return (t*50L); }

    // Puts given item on cooldown
    public static void addCooldown(ItemStack item, UseType type, Player player, InventorySlot slot){

        NBTItem nbtItem = new NBTItem(item);

        if(type == UseType.BASIC)
            nbtItem.setLong(nbtBasic, System.currentTimeMillis());
        else if(type == UseType.SPECIAL)
            nbtItem.setLong(nbtSpecial, System.currentTimeMillis());

        switch(slot){
            case MAIN_HAND:
                player.getInventory().setItemInMainHand(nbtItem.getItem());
                break;
            case OFF_HAND:
                player.getInventory().setItemInOffHand(nbtItem.getItem());
                break;
            case HELMET:
                player.getInventory().setItem(EquipmentSlot.HEAD, nbtItem.getItem());
                break;
            case CHESTPLATE:
                player.getInventory().setItem(EquipmentSlot.CHEST, nbtItem.getItem());
                break;
            case LEGGINGS:
                player.getInventory().setItem(EquipmentSlot.LEGS, nbtItem.getItem());
                break;
            case BOOTS:
                player.getInventory().setItem(EquipmentSlot.FEET, nbtItem.getItem());
                break;
        }
    }

    public static boolean onCooldown(NBTItem nbtItem, UseType type, long cooldown){

        if(type == UseType.BASIC)
            return ((nbtItem.getLong(nbtBasic) + ticksToMillis(cooldown)) - System.currentTimeMillis() > 1);
        else if(type == UseType.SPECIAL)
            return ((nbtItem.getLong(nbtSpecial) + ticksToMillis(cooldown)) - System.currentTimeMillis() > 1);

        return false;
    }

    public static void alertCooldown(Player player, long cooldown, UseType type){
        String timeLeft = "";
        long time = 0L;

        NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());

        if(type == UseType.BASIC)
            time = nbtItem.getLong(nbtBasic);
        else if(type == UseType.SPECIAL)
            time = nbtItem.getLong(nbtSpecial);

        long diff = System.currentTimeMillis() - time;
        time =  ticksToMillis(cooldown) - diff;

        timeLeft += ((int) (time / (1000*60*60)) % 24) + "h ";
        timeLeft += ((int) (time / (1000*60)) % 60) + "m ";
        timeLeft += ((int) (time / 1000) % 60) + "s";


        player.sendTitle("", player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                + ChatColor.RESET + " is on cooldown for " + timeLeft, 5, 5, 5);
    }

    // Functions related to events

    public void rightClickAir(Player player){}
    public void leftClickAir(Player player){}
    public void leftClickAtEntity(Player player, Entity entity){}


    public void killedWhileWorn(Player player, EntityDamageEvent event){}

    public void damagedWhileHeld(Player player, EntityDamageEvent event){}


    // Getters and Setters


    public String getID(){
        return id;
    }

    public ItemStack getCustomItem(){
        return item;
    }

    protected static class TaskHandler {

        private int iterator;

        private BukkitTask task;

        public TaskHandler(long delay, long period, int count, Runnable func){

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    if(iterator > count)
                        task.cancel();
                    else {
                        func.run();
                        iterator++;
                    }
                }
            }, delay, period);

        }

    }

}
