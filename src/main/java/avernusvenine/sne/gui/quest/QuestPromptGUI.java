package avernusvenine.sne.gui.quest;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.gui.AcceptDenyGUI;
import avernusvenine.sne.gui.DefaultGUI;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class QuestPromptGUI extends AcceptDenyGUI {

    public QuestPromptGUI(Player player){
        id = "quest_accept";
        title = "Accept Quest?";
        inventory = Bukkit.createInventory(null, 9, title);
        owner = player;
        initializeItems();
    }

    protected void onAccept(Player player){
        PlayerDictionary.get(player).onQuestAccept();
    }

    protected void onDeny(Player player){
        PlayerDictionary.get(player).onQuestDeny();
    }
}