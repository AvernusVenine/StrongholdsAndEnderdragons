package avernusvenine.sne;

import avernusvenine.sne.commands.GiveCustomItem;
import avernusvenine.sne.commands.ResetCooldowns;
import avernusvenine.sne.items.*;

import avernusvenine.sne.items.armor.PhoenixHelmet;
import avernusvenine.sne.items.misc.Midas;
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

    public static List<EntityType> invalidEntities = new ArrayList<EntityType>();
    public static List<EntityType> basicEntities = new ArrayList<EntityType>();

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
        // Invalid entities
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
        invalidEntities.add(EntityType.EVOKER_FANGS);
        invalidEntities.add(EntityType.FALLING_BLOCK);
        invalidEntities.add(EntityType.FIREWORK);
        invalidEntities.add(EntityType.FIREBALL);
        invalidEntities.add(EntityType.GLOW_ITEM_FRAME);
        invalidEntities.add(EntityType.INTERACTION);
        invalidEntities.add(EntityType.ITEM_DISPLAY);
        invalidEntities.add(EntityType.ITEM_FRAME);
        invalidEntities.add(EntityType.LLAMA_SPIT);
        invalidEntities.add(EntityType.LEASH_HITCH);
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
        invalidEntities.add(EntityType.TRIDENT);
        invalidEntities.add(EntityType.UNKNOWN);
        invalidEntities.add(EntityType.WITHER_SKULL);

        // Basic tier entities
        basicEntities.add(EntityType.AXOLOTL);
        basicEntities.add(EntityType.BAT);
        basicEntities.add(EntityType.BEE);
        basicEntities.add(EntityType.BLAZE);
        basicEntities.add(EntityType.CAMEL);
        basicEntities.add(EntityType.CAVE_SPIDER);
        basicEntities.add(EntityType.CHICKEN);
        basicEntities.add(EntityType.COD);
        basicEntities.add(EntityType.COW);
        basicEntities.add(EntityType.CREEPER);
        basicEntities.add(EntityType.DROWNED);
        basicEntities.add(EntityType.ENDERMAN);
        basicEntities.add(EntityType.ENDERMITE);
        basicEntities.add(EntityType.EVOKER);
        basicEntities.add(EntityType.FOX);
        basicEntities.add(EntityType.FROG);
        basicEntities.add(EntityType.GHAST);
        basicEntities.add(EntityType.GLOW_SQUID);
        basicEntities.add(EntityType.GOAT);
        basicEntities.add(EntityType.GUARDIAN);
        basicEntities.add(EntityType.HOGLIN);
        basicEntities.add(EntityType.HUSK);
        basicEntities.add(EntityType.ILLUSIONER);
        basicEntities.add(EntityType.LLAMA);
        basicEntities.add(EntityType.MAGMA_CUBE);
        basicEntities.add(EntityType.MUSHROOM_COW);
        basicEntities.add(EntityType.OCELOT);
        basicEntities.add(EntityType.PANDA);
        basicEntities.add(EntityType.PHANTOM);
        basicEntities.add(EntityType.PIG);
        basicEntities.add(EntityType.POLAR_BEAR);
        basicEntities.add(EntityType.PILLAGER);
        basicEntities.add(EntityType.PUFFERFISH);
        basicEntities.add(EntityType.SALMON);
        basicEntities.add(EntityType.SHEEP);
        basicEntities.add(EntityType.SHULKER);
        basicEntities.add(EntityType.SILVERFISH);
        basicEntities.add(EntityType.SKELETON);
        basicEntities.add(EntityType.SLIME);
        basicEntities.add(EntityType.SPIDER);
        basicEntities.add(EntityType.SQUID);
        basicEntities.add(EntityType.STRAY);
        basicEntities.add(EntityType.STRIDER);
        basicEntities.add(EntityType.TROPICAL_FISH);
        basicEntities.add(EntityType.TURTLE);
        basicEntities.add(EntityType.VEX);
        basicEntities.add(EntityType.VINDICATOR);
        basicEntities.add(EntityType.WITCH);
        basicEntities.add(EntityType.ZOGLIN);
        basicEntities.add(EntityType.ZOMBIE);
        basicEntities.add(EntityType.ZOMBIE_VILLAGER);
        basicEntities.add(EntityType.ZOMBIFIED_PIGLIN);
    }


    // Don't forget to put new custom items in here, so they can be spawned in
    public void loadItems(){
        {
            CustomItem item = new CustomItem();
            customItemDictionary.put(item.getID(), item);
        }
        {
            Mjolnir item = new Mjolnir();
            customItemDictionary.put(item.getID(), item);
        }
        {
            PhoenixHelmet item = new PhoenixHelmet();
            customItemDictionary.put(item.getID(), item);
        }
        {
            Midas item = new Midas();
            customItemDictionary.put(item.getID(), item);
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
