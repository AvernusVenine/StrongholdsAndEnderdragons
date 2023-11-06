package avernusvenine.sne.gui.quest;

import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.quests.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestRewardGUI extends DefaultGUI {

    private final List<ItemStack> rewardItems;

    public QuestRewardGUI(Player player, Quest quest){
        id = "quest_reward";
        title = "Quest Reward";
        rewardItems = quest.getRewardItems();
        inventory = Bukkit.createInventory(null, 9, title);
        owner = player;
        initializeItems();
    }

    public void initializeItems(){
        for(ItemStack reward : rewardItems){
            inventory.addItem(reward);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){

        Player player = (Player) event.getPlayer();

        if(!event.getView().getOriginalTitle().equals(title) || !owner.equals(player))
            return;

        for(ItemStack item : event.getInventory().getContents()){
            if(item != null)
                player.getInventory().addItem(item);
        }

        HandlerList.unregisterAll(this);
    }

    @Override
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

    }

    @Override
    @EventHandler
    public void onInventoryEvent(final InventoryInteractEvent event){

    }

    @Override
    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event){

    }
}