package avernusvenine.sne;

import avernusvenine.sne.classes.*;
import avernusvenine.sne.commands.*;
import avernusvenine.sne.events.ChatEventHandler;
import avernusvenine.sne.events.ProjectileEventHandler;
import avernusvenine.sne.npc.traits.DialogueTrait;
import avernusvenine.sne.database.DatabaseHandler;
import avernusvenine.sne.events.ItemEventHandler;
import avernusvenine.sne.events.PlayerEventHandler;
import avernusvenine.sne.gui.DefaultGUI;

import avernusvenine.sne.professions.Fishing;
import avernusvenine.sne.races.*;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

public final class StrongholdsAndEnderdragons extends JavaPlugin {

    public static Map<DefaultClass.ClassType, DefaultClass> classDictionary = new HashMap<DefaultClass.ClassType, DefaultClass>();
    public static Map<Race.RaceType, Race> raceDictionary = new HashMap<Race.RaceType, Race>();

    //Entity lists for interaction events
    public static List<EntityType> invalidEntities = new ArrayList<EntityType>();
    public static List<EntityType> basicEntities = new ArrayList<EntityType>();

    //GUI dictionary for gui events
    public static Map<String, DefaultGUI> guiDictionary = new HashMap<String, DefaultGUI>();


    public static JavaPlugin plugin;
    public static DatabaseHandler databaseHandler;
    public static ProtocolManager manager;

    @Override
    public void onEnable() {

        plugin = this;
        manager = ProtocolLibrary.getProtocolManager();

        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdirs();

            databaseHandler = new DatabaseHandler(getDataFolder().getAbsolutePath() + "/sne.db");
            Bukkit.getServer().getConsoleSender().sendMessage("[SNE] Successfully loaded database!");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load database! Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }

        registerNPCTraits();
        Bukkit.getServer().getConsoleSender().sendMessage("[SNE] Successfully registered Citizen Traits!");

        ItemDictionary.loadItems();
        SpellDictionary.loadSpells();
        NPCDictionary.loadNPCs();

        loadCommands();
        loadClasses();
        loadRaces();

        registerEvents();
        Bukkit.getServer().getConsoleSender().sendMessage("[SNE] Successfully registered events!");

    }

    public void loadRaces(){
        raceDictionary.put(Race.RaceType.DWARF, new Dwarf());
        raceDictionary.put(Race.RaceType.DRAGON_KIN, new Dragonkin());
        raceDictionary.put(Race.RaceType.ELF, new Elf());
        raceDictionary.put(Race.RaceType.FELIDAE, new Felidae());
        raceDictionary.put(Race.RaceType.GNOME, new Gnome());
        raceDictionary.put(Race.RaceType.HALF_ELF, new HalfElf());
        raceDictionary.put(Race.RaceType.HALF_ORC, new HalfOrc());
        raceDictionary.put(Race.RaceType.HUMAN, new Human());
        raceDictionary.put(Race.RaceType.ORC, new Orc());
        raceDictionary.put(Race.RaceType.TIEFLING, new Tiefling());
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

    public void loadCommands(){
        registerCommand("givecustomitem", new GiveCustomItem());
        registerCommand("getrank", new GetRank());
        registerCommand("setrank", new SetRank());
        registerCommand("opengui", new OpenGUI());
        registerCommand("spawnnpc", new SpawnNPC());
        registerCommand("showtitle", new ShowTitle());
        registerCommand("despawnnpc", new DespawnNPC());
        registerCommand("unlockrecipe", new UnlockRecipe());
        registerCommand("showactionbar", new ShowActionbar());
        registerCommand("setresource", new SetResource());
        registerCommand("setspell", new SetSpell());
        registerCommand("npcdialoguechoice", new NPCDialogueChoice());
    }

    public void registerCommand(String name, CommandExecutor executor){
        PluginCommand command = getCommand(name);

        if(command != null){
            command.setExecutor(executor);
        }
    }

    public void registerEvents(){
        // Spigot Listeners
        getServer().getPluginManager().registerEvents(new ItemEventHandler(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(), plugin);
        getServer().getPluginManager().registerEvents(new ChatEventHandler(), plugin);
        getServer().getPluginManager().registerEvents(new Fishing(), plugin);
        getServer().getPluginManager().registerEvents(new ProjectileEventHandler(), plugin);

        // Packet Listeners
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if(PlayerDictionary.get(event.getPlayer()).getPlayerDialogueHandler().isChoosing())
                    event.setCancelled(true);
            }
        });
    }

    public void registerNPCTraits(){
        net.citizensnpcs.api.CitizensAPI.getTraitFactory()
                .registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(DialogueTrait.class));
    }

    @Override
    public void onDisable() {
        NPCDictionary.despawnAllNPCs();

        try {
            databaseHandler.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
