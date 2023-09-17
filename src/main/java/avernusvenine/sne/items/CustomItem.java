package avernusvenine.sne.items;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import org.bukkit.Bukkit;
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
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static org.bukkit.Bukkit.*;


import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class CustomItem {

    protected ItemStack item;
    protected Material material;
    protected String id;

    public CustomItem() {
        id = "default_custom_item";
        material = Material.STICK;
        item = generateItem();
    }

    public static ItemStack generateItem(){
        ItemStack tempItem = new ItemStack(Material.STICK, 1);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName("Default CustomItem (You shouldn't have gotten this!)");

        List<String> lore = new ArrayList<>();
        lore.add("Please report this as a bug along with how it happened!");

        meta.setLore(lore);

        tempItem.setItemMeta(meta);
        return tempItem;
    }

    public static ItemStack generateItem(Material material, int amount, String displayName, List<String> lore,
                                         @NotNull List<ItemFlag> itemFlags, boolean unbreakable){
        ItemStack tempItem = new ItemStack(material, amount);

        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        for (ItemFlag itemFlag : itemFlags) {
            meta.addItemFlags(itemFlag);
        }

        meta.setUnbreakable(unbreakable);

        tempItem.setItemMeta(meta);

        return tempItem;
    }

    public static ItemStack generateItem(Material material, int amount, String displayName, List<String> lore,
                                         @NotNull Map<Enchantment, Integer> enchantment, @NotNull List<ItemFlag> itemFlags,
                                         boolean unbreakable) {
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

        return tempItem;
    }

    public void rightClickAir(Player player){}
    public void leftClickAir(Player player){}
    public void leftClickAtEntity(Player player, Entity entity){}


    public void killedWhileWorn(Player player, Entity entity, EntityDamageByEntityEvent event){}


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
