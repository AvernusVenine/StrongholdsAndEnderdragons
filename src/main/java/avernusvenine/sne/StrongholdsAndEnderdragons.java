package avernusvenine.sne;

import avernusvenine.sne.classes.*;
import avernusvenine.sne.events.ChatEventHandler;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.commands.GetRank;
import avernusvenine.sne.commands.GiveCustomItem;
import avernusvenine.sne.commands.OpenGUI;
import avernusvenine.sne.commands.SetRank;
import avernusvenine.sne.database.DatabaseHandler;
import avernusvenine.sne.events.ItemEventHandler;
import avernusvenine.sne.events.PlayerEventHandler;
import avernusvenine.sne.gui.ClassSelectGUI;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.gui.RaceSelectGUI;
import avernusvenine.sne.items.*;

import avernusvenine.sne.items.armor.PhoenixHelmet;
import avernusvenine.sne.items.misc.Midas;
import avernusvenine.sne.items.weapons.Mjolnir;
import avernusvenine.sne.races.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.sql.SQLException;
import java.util.*;

public final class StrongholdsAndEnderdragons extends JavaPlugin {

    // Dictionary of all custom items
    public static BiMap<String, CustomItem> customItemDictionary = HashBiMap.create();
    public static Map<DefaultClass.ClassType, DefaultClass> classDictionary = new HashMap<DefaultClass.ClassType, DefaultClass>();
    public static Map<DefaultRace.RaceType, DefaultRace> raceDictionary = new HashMap<DefaultRace.RaceType, DefaultRace>();

    //Entity lists for interaction events
    public static List<EntityType> invalidEntities = new ArrayList<EntityType>();
    public static List<EntityType> basicEntities = new ArrayList<EntityType>();

    //GUI dictionary for gui events
    public static Map<String, DefaultGUI> guiDictionary = new HashMap<String, DefaultGUI>();

    //Player dictionary for relating players to their currently selected characters
    public static Map<String, PlayerCharacter> playerCharacters = new HashMap<String, PlayerCharacter>();

    public static JavaPlugin plugin;
    public static DatabaseHandler databaseHandler;

    @Override
    public void onEnable() {

        plugin = this;

        loadItems();
        loadGUI();
        loadCommands();
        loadClasses();
        loadRaces();

        registerEvents();
        Bukkit.getServer().getConsoleSender().sendMessage("Successfully registered events!");
        registerEntities();

        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdirs();

            databaseHandler = new DatabaseHandler(getDataFolder().getAbsolutePath() + "/sne.db");
            Bukkit.getServer().getConsoleSender().sendMessage("Successfully loaded database!");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load database! Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }

    }

    public void loadRaces(){
        raceDictionary.put(DefaultRace.RaceType.DWARF, new Dwarf());
        raceDictionary.put(DefaultRace.RaceType.DRAGON_KIN, new Dragonkin());
        raceDictionary.put(DefaultRace.RaceType.ELF, new Elf());
        raceDictionary.put(DefaultRace.RaceType.FELIDAE, new Felidae());
        raceDictionary.put(DefaultRace.RaceType.GNOME, new Gnome());
        raceDictionary.put(DefaultRace.RaceType.HALF_ELF, new HalfElf());
        raceDictionary.put(DefaultRace.RaceType.HALF_ORC, new HalfOrc());
        raceDictionary.put(DefaultRace.RaceType.HUMAN, new Human());
        raceDictionary.put(DefaultRace.RaceType.ORC, new Orc());
        raceDictionary.put(DefaultRace.RaceType.TIEFLING, new Tiefling());
    }

    public void loadClasses(){
        classDictionary.put(DefaultClass.ClassType.ARTIFICER, new Artificer());
        classDictionary.put(DefaultClass.ClassType.BARBARIAN, new Barbarian());
        classDictionary.put(DefaultClass.ClassType.BARD, new Bard());
        classDictionary.put(DefaultClass.ClassType.CLERIC, new Cleric());
        classDictionary.put(DefaultClass.ClassType.DRUID, new Druid());
        classDictionary.put(DefaultClass.ClassType.FIGHTER, new Fighter());
        classDictionary.put(DefaultClass.ClassType.MONK, new Monk());
        classDictionary.put(DefaultClass.ClassType.PALADIN, new Paladin());
        classDictionary.put(DefaultClass.ClassType.RANGER, new Ranger());
        classDictionary.put(DefaultClass.ClassType.ROGUE, new Rogue());
        classDictionary.put(DefaultClass.ClassType.SHAMAN, new Shaman());
        classDictionary.put(DefaultClass.ClassType.SORCERER, new Sorcerer());
        classDictionary.put(DefaultClass.ClassType.WARLOCK, new Warlock());
        classDictionary.put(DefaultClass.ClassType.WIZARD, new Wizard());
    }

    public void loadGUI(){
        {
            ClassSelectGUI gui = new ClassSelectGUI();
            guiDictionary.put(gui.getID(), gui);
            getServer().getPluginManager().registerEvents(gui, plugin);
        }
        {
            RaceSelectGUI gui = new RaceSelectGUI();
            guiDictionary.put(gui.getID(), gui);
            getServer().getPluginManager().registerEvents(gui, plugin);
        }
    }

    public static void registerEntities(){
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

    public void loadCommands(){
        registerCommand("givecustomitem", new GiveCustomItem());
        registerCommand("getrank", new GetRank());
        registerCommand("setrank", new SetRank());
        registerCommand("opengui", new OpenGUI());
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
        getServer().getPluginManager().registerEvents(new ItemEventHandler(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(), plugin);
        getServer().getPluginManager().registerEvents(new ChatEventHandler(), plugin);
    }

    @Override
    public void onDisable() {
        try {
            databaseHandler.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
