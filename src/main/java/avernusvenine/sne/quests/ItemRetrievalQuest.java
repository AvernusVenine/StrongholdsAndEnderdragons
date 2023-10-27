package avernusvenine.sne.quests;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemRetrievalQuest extends Quest{

    protected List<ItemStack> questItems;

    public ItemRetrievalQuest(String id, List<ItemStack> questItems, List<ItemStack> rewardItems){
        this.id = id;
        this.questItems = questItems;
        this.rewardItems = rewardItems;
        type = QuestType.ITEM_RETRIEVAL;
    }

    public List<ItemStack> getQuestItems(){
        return  questItems;
    }
}
