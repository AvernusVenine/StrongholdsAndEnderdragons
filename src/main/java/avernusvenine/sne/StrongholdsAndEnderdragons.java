package avernusvenine.sne;

import avernusvenine.sne.commands.GiveCustomItem;
import avernusvenine.sne.commands.ResetCooldowns;
import avernusvenine.sne.items.*;

import avernusvenine.sne.items.armor.PhoenixHelmet;
import avernusvenine.sne.items.weapons.Mjolnir;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;

public final class StrongholdsAndEnderdragons extends JavaPlugin {

    // Dictionary of all custom items
    public static BiMap<String, CustomItem> customItemDictionary = HashBiMap.create();
    public static BiMap<ItemMeta, CustomItem> customMetaDictionary = HashBiMap.create();

    public static List<EntityType> invalidEntities = new ArrayList<EntityType>();

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {

        plugin = this;

        registerCommand("givecustomitem", new GiveCustomItem());
        registerCommand("resetcooldowns", new ResetCooldowns());

        loadItems();
        registerEvents();
        registerEntities();

    }

    public void registerEntities(){
        invalidEntities.add(EntityType.ARMOR_STAND);
        invalidEntities.add(EntityType.ARROW);
        invalidEntities.add(EntityType.AREA_EFFECT_CLOUD);
        invalidEntities.add(EntityType.BLOCK_DISPLAY);
        invalidEntities.add(EntityType.BOAT);
        invalidEntities.add(EntityType.CHEST_BOAT);
        invalidEntities.add(EntityType.DRAGON_FIREBALL);
        invalidEntities.add(EntityType.DROPPED_ITEM);
        invalidEntities.add(EntityType.EGG);
        invalidEntities.add(EntityType.ENDER_PEARL);
        invalidEntities.add(EntityType.ENDER_SIGNAL);
        invalidEntities.add(EntityType.ENDER_CRYSTAL);
        invalidEntities.add(EntityType.EXPERIENCE_ORB);
        invalidEntities.add(EntityType.FALLING_BLOCK);
        invalidEntities.add(EntityType.FIREWORK);
        invalidEntities.add(EntityType.FIREBALL);
        invalidEntities.add(EntityType.GLOW_ITEM_FRAME);
        invalidEntities.add(EntityType.INTERACTION);
        invalidEntities.add(EntityType.ITEM_DISPLAY);
        invalidEntities.add(EntityType.ITEM_FRAME);
        invalidEntities.add(EntityType.LLAMA_SPIT);
        invalidEntities.add(EntityType.MARKER);
        invalidEntities.add(EntityType.MINECART);
        invalidEntities.add(EntityType.MINECART_FURNACE);
        invalidEntities.add(EntityType.MINECART_CHEST);
        invalidEntities.add(EntityType.MINECART_COMMAND);
        invalidEntities.add(EntityType.MINECART_TNT);
        invalidEntities.add(EntityType.MINECART_HOPPER);
        invalidEntities.add(EntityType.MINECART_MOB_SPAWNER);
        invalidEntities.add(EntityType.PAINTING);
        invalidEntities.add(EntityType.PRIMED_TNT);
        invalidEntities.add(EntityType.SHULKER_BULLET);
        invalidEntities.add(EntityType.SMALL_FIREBALL);
        invalidEntities.add(EntityType.SPECTRAL_ARROW);
        invalidEntities.add(EntityType.SPLASH_POTION);
        invalidEntities.add(EntityType.TEXT_DISPLAY);
        invalidEntities.add(EntityType.THROWN_EXP_BOTTLE);
        invalidEntities.add(EntityType.UNKNOWN);
        invalidEntities.add(EntityType.WITHER_SKULL);
    }


    // Don't forget to put new custom items in here, so they can be spawned in
    public void loadItems(){
        {
            CustomItem item = new CustomItem();
            customItemDictionary.put(item.getID(), item);
            customMetaDictionary.put(item.getCustomItem().getItemMeta(), item);
        }
        {
            Mjolnir item = new Mjolnir();

            customItemDictionary.put(item.getID(), item);
            customMetaDictionary.put(item.getCustomItem().getItemMeta(), item);
        }
        {
            PhoenixHelmet item = new PhoenixHelmet();

            customItemDictionary.put(item.getID(), item);
            customMetaDictionary.put(item.getCustomItem().getItemMeta(), item);
        }
    }

    public void registerCommand(String name, CommandExecutor executor){
        PluginCommand command = getCommand(name);

        if(command != null){
            command.setExecutor(executor);
        }
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new ItemEventHandler(),plugin);
    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
