package avernusvenine.sne.events;

import avernusvenine.sne.Globals.ActionType;
import avernusvenine.sne.ItemDictionary;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.consumable.Food;
import avernusvenine.sne.items.interactable.Interactable;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemEventHandler implements Listener {

    // When a player right/left clicks with a given item
    @EventHandler
    public void onInteractEvent(final PlayerInteractEvent event){
        ItemStack item = event.getItem();

        if(item == null || item.getType().isAir() || item.getAmount() == 0)
            return;

        SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(NBTFlags.itemID));

        ActionType type = ActionType.UNKNOWN;

        if(event.getAction() == Action.RIGHT_CLICK_AIR)
            type = ActionType.PLAYER_RIGHT_CLICK_AIR;
        else if(event.getAction() == Action.LEFT_CLICK_AIR)
            type = ActionType.PLAYER_LEFT_CLICK_AIR;

        if(sneItem instanceof Interactable interactable)
            interactable.onItemUse(event.getPlayer(), null, type, event);
    }

    // When a player right clicks at an entity
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if(item.getType().isAir() || item.getAmount() == 0)
            return;

        SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(NBTFlags.itemID));

        ActionType type = ActionType.UNKNOWN;

        if(sneItem instanceof Interactable interactable)
            interactable.onItemUse(event.getPlayer(), null, ActionType.PLAYER_RIGHT_CLICK_ENTITY, event);
    }

    // When a player damages another entity or is damaged by another entity
    @EventHandler
    public void onPlayerDamageEntity(final EntityDamageByEntityEvent event){

        // When the player damages an entity
        if(event.getDamager() instanceof Player player){
            ItemStack item = player.getInventory().getItemInMainHand();

            if(item.getType().isAir() || item.getAmount() == 0)
                return;

            SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(NBTFlags.itemID));

            if(sneItem instanceof Interactable interactable)
                interactable.onItemUse(player, event.getEntity(), ActionType.PLAYER_LEFT_CLICK_ENTITY, event);
        }

        if(event.getEntity() instanceof Player player){
            for(ItemStack item : player.getInventory().getArmorContents()){

                if(item == null || item.getType().isAir() || item.getAmount() == 0)
                    continue;

                SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(NBTFlags.itemID));

                if(sneItem instanceof Interactable interactable)
                    interactable.onItemUse(player, event.getDamager(), ActionType.PLAYER_DAMAGED_BY_ENTITY, event);
            }
        }
    }

    // When a player is damaged
    @EventHandler
    public void onPlayerDamage (final EntityDamageEvent event){

        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        for(ItemStack item : player.getInventory().getArmorContents()){

            if(item == null || item.getType().isAir() || item.getAmount() == 0)
                continue;

            SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(NBTFlags.itemID));

            if(sneItem instanceof Interactable interactable){
                if(player.getHealth() - event.getDamage() < 1)
                    interactable.onItemUse(player, null, ActionType.PLAYER_KILLED, event);
                else
                    interactable.onItemUse(player, null, ActionType.PLAYER_DAMAGED, event);
            }
        }
    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event){

        ItemStack item = event.getItem();
        NBTItem nbtItem = new NBTItem(item);

        SneItem sneItem = ItemDictionary.get(nbtItem.getString(NBTFlags.itemID));

        Player player = event.getPlayer();

        if(sneItem instanceof Food){
            Food food = (Food) sneItem;

            event.setCancelled(true);
            food.onConsumption(player);

            if(event.getHand() == EquipmentSlot.HAND)
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            else
                player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);

            player.setFoodLevel((int) Math.min(nbtItem.getFloat(NBTFlags.foodLevel) + player.getFoodLevel(), 20));
            player.setSaturation(Math.min(nbtItem.getFloat(NBTFlags.saturation) + player.getSaturation(), 20));
        }
    }
}
