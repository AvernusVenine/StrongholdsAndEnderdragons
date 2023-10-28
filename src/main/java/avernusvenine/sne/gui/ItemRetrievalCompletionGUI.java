package avernusvenine.sne.gui;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemRetrievalCompletionGUI extends DefaultGUI{

    protected List<ItemStack> questItems;
    protected String ownerUUID;

    private boolean completed = false;

    public ItemRetrievalCompletionGUI(Player player, ItemRetrievalQuest quest){
        id = "item_turn_in";
        title = "Item Turn In";
        ownerUUID = player.getUniqueId().toString();
        questItems = quest.getQuestItems();
        inventory = Bukkit.createInventory(null, 18, title);
        initializeItems();
    }

    public void initializeItems(){
        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>(), "blank");
        int[] blankSlots = new int[]{};

        switch(questItems.size()){
            case 1:
                blankSlots = new int[]{1,2,3,5,6,7,9,10,11,12,14,15,16,17};
                inventory.setItem(4, createGUIItem(questItems.get(0), "quest"));
                break;
        }

        for(int i : blankSlots)
            inventory.setItem(i, blankItem);

        ItemStack submitItem = createGUIItem(Material.LIME_STAINED_GLASS_PANE,
                "SUBMIT", new ArrayList<>(), "submit");
        inventory.setItem(8, submitItem);

        ItemStack closeItem = createGUIItem(Material.RED_STAINED_GLASS_PANE,
                "CLOSE", new ArrayList<>(), "close");
        inventory.setItem(0, closeItem);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if(!event.getView().getOriginalTitle().equals(title) || !player.getUniqueId().toString().equals(ownerUUID))
            return;

        ItemStack item = event.getCurrentItem();

        if(item == null || item.getType().isAir())
            return;

        NBTItem nbtItem = new NBTItem(item);

        switch (nbtItem.getString(nbtID)){
            default:
                return;
            case "close":
                switch(questItems.size()){
                    default:
                        break;
                    case 1:
                        ItemStack itemReturn = event.getInventory().getItem(4);
                        if(itemReturn != null)
                            player.getInventory().addItem(itemReturn);
                        break;
                }

                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);

                event.setCancelled(true);
                player.closeInventory();
                break;
            case "submit":
                boolean valid = true;

                for(ItemStack questItem : questItems){
                    if(!event.getInventory().contains(questItem))
                        valid = false;
                }

                if(valid){
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    PlayerDictionary.get(player.getUniqueId().toString()).onQuestCompletion();
                    completed = true;
                    player.closeInventory();
                }
                event.setCancelled(true);
                break;
            case "blank", "quest":
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        Player player = (Player) event.getPlayer();

        if(!completed){
            PlayerDictionary.get(player.getUniqueId().toString()).closeQuestCompletion();
            for(ItemStack item : event.getInventory().getContents()){

                if(item == null || item.getType() == Material.AIR)
                    break;

                NBTItem nbtItem = new NBTItem(item);

                if(!Objects.equals(nbtItem.getString(nbtID), "blank") && !Objects.equals(nbtItem.getString(nbtID), "quest") &&
                        !Objects.equals(nbtItem.getString(nbtID), "close") && !Objects.equals(nbtItem.getString(nbtID), "submit"))
                    player.getInventory().addItem(nbtItem.getItem());
            }
        }

        HandlerList.unregisterAll(this);
    }

}
