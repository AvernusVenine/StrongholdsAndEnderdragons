package avernusvenine.sne.events;

import avernusvenine.sne.Globals.ActionType;
import avernusvenine.sne.ItemDictionary;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.consumable.Food;
import avernusvenine.sne.items.interactable.Interactable;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
        if(event.getAction() == Action.RIGHT_CLICK_AIR && event.getPlayer().isSneaking())
            type = ActionType.PLAYER_SHIFT_RIGHT_CLICK_AIR;
        else if(event.getAction() == Action.LEFT_CLICK_AIR && event.getPlayer().isSneaking())
            type = ActionType.PLAYER_SHIFT_LEFT_CLICK_AIR;

        if(sneItem instanceof Interactable interactable) {
            if(interactable.canClassUse(PlayerDictionary.get(event.getPlayer()).getPlayerCharacter().getClassType()))
                interactable.onItemUse(event.getPlayer(), null, type, event);
            else
                event.getPlayer().sendMessage(Component.text("You don't know how to use this item!"));
        }
    }

    // When a player right clicks at an entity
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if(item.getType().isAir() || item.getAmount() == 0)
            return;

        SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(NBTFlags.itemID));

        Player player = event.getPlayer();

        if(sneItem instanceof Interactable interactable) {
            if(interactable.canClassUse(PlayerDictionary.get(player).getPlayerCharacter().getClassType())
            && interactable.canLevelUse(PlayerDictionary.get(player).getPlayerCharacter().getLevel())) {
                if (player.isSneaking()) {
                    interactable.onItemUse(player, null, ActionType.PLAYER_SHIFT_RIGHT_CLICK_ENTITY, event);
                } else {
                    interactable.onItemUse(player, null, ActionType.PLAYER_RIGHT_CLICK_ENTITY, event);
                }
            }
            else
                player.sendMessage(Component.text("You don't know how to use this item!"));
        }
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

            if(sneItem instanceof Interactable interactable) {
                if(interactable.canClassUse(PlayerDictionary.get(player).getPlayerCharacter().getClassType())
                        && interactable.canLevelUse(PlayerDictionary.get(player).getPlayerCharacter().getLevel()))
                    interactable.onItemUse(player, null, ActionType.PLAYER_DAMAGE_ENTITY, event);
                else
                    player.sendMessage(Component.text("You don't know how to use this item!"));
            }
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

        if(sneItem instanceof Food food){

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

    // Events for disallowing certain items to be dropped and stored
    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event){
        SneItem item = ItemDictionary.get(event.getItemDrop().getItemStack());

        if(item != null && item.isDroppable()){
            event.getItemDrop().setItemStack(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        ItemStack item = event.getCurrentItem();

        if(item == null || item.getType() == Material.AIR)
            return;

        SneItem sneItem = ItemDictionary.get(item);

        if(sneItem != null && sneItem.isDroppable()){
            if(event.getClickedInventory() != event.getWhoClicked().getInventory()){
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event){
        ItemStack item = event.getOldCursor();

        if(item.getType() == Material.AIR)
            return;

        SneItem sneItem = ItemDictionary.get(item);

        if(sneItem != null && sneItem.isDroppable()){
            if(event.getInventory() != event.getWhoClicked().getInventory()){
                event.setCancelled(true);
            }
        }

    }
}
