package avernusvenine.sne.npc;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.quests.Quest;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.citizensnpcs.api.CitizensAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.K;

import java.time.Duration;
import java.util.*;

public abstract class SneNPC {

    protected net.citizensnpcs.api.npc.NPC npc;

    protected DialogueHandler dialogueHandler;

    protected String id;
    protected String name;

    protected List<Quest> quests = new ArrayList<>();

    public void spawnNPC(Location location){
        npc.spawn(location);
    }

    //Getters and setters
    public String getID(){
        return id;
    }

    public DialogueHandler getDialogueHandler(){
        return dialogueHandler;
    }

    public String getUUID(){
        return npc.getUniqueId().toString();
    }

}
