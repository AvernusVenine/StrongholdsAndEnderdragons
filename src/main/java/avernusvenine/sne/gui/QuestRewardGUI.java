package avernusvenine.sne.gui;

import avernusvenine.sne.quests.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestRewardGUI extends DefaultGUI {

    private String ownerUUID;
    private List<ItemStack> rewardItems;

    public QuestRewardGUI(Player player, Quest quest){
        id = "quest_reward";
        title = "Quest Reward";
        rewardItems = quest.getRewardItems();
        inventory = Bukkit.createInventory(null, rewardItems.size(), title);
        ownerUUID = player.getUniqueId().toString();
        initializeItems();
    }

    public void initializeItems(){
        for(ItemStack reward : rewardItems){
            inventory.addItem(reward);
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){

        Player player = (Player) event.getPlayer();

        if(!event.getView().getOriginalTitle().equals(title) || !player.getUniqueId().toString().equals(ownerUUID))
            return;

        for(ItemStack item : event.getInventory().getContents()){
            if(item != null)
                player.getInventory().addItem(item);
        }

        HandlerList.unregisterAll(this);
    }
}