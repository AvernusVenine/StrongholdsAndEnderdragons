package avernusvenine.sne.quests;

import avernusvenine.sne.gui.DefaultGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest {

    public enum QuestType{
        ITEM_RETRIEVAL
    }

    protected String id;
    protected String title;

    protected List<ItemStack> rewardItems;
    protected List<String> questPrerequisites;

    protected QuestType type;

    public Quest(){
        id = "default_quest";
        rewardItems = new ArrayList<>();
        questPrerequisites = new ArrayList<>();
    }

    public Quest(String id, List<ItemStack> rewardItems){
        this.id = id;
        this.rewardItems = rewardItems;
    }

    public Quest(String id, List<ItemStack> rewardItems, List<String> questPrerequisites){
        this.id = id;
        this.rewardItems = rewardItems;
        this.questPrerequisites = questPrerequisites;
    }

    // Getters and Setters

    public String getID(){
        return id;
    }

    public QuestType getType(){
        return type;
    }

    public List<ItemStack> getRewardItems(){
        return rewardItems;
    }

    public List<String> getQuestPrerequisites(){
        return questPrerequisites;
    }
}
