package avernusvenine.sne.events;

import avernusvenine.sne.ItemDictionary;

import avernusvenine.sne.items.SneItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEventHandler implements Listener {

    // When a player right/left clicks with a given item
    @EventHandler
    public void onInteractEvent(final PlayerInteractEvent event){

        ItemStack item = event.getItem();

        if (item != null){
            if(event.getAction() == Action.RIGHT_CLICK_AIR){

                SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(SneItem.nbtID));

                if(sneItem != null)
                    sneItem.rightClickAir(event.getPlayer());

            }
            else if(event.getAction() == Action.LEFT_CLICK_AIR){

            }
        }
    }

    // When a player right clicks at an entity
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event){

    }

    // When a player damages another entity or is damaged by another entity
    @EventHandler
    public void onPlayerDamageEntity(final EntityDamageByEntityEvent event){

        // When the player damages an entity
        if(event.getDamager() instanceof Player){
            ItemStack item = ((Player) event.getDamager()).getInventory().getItemInMainHand();

            if(item == null || item.getType().isAir() || item.getAmount() == 0)
                return;

            SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(SneItem.nbtID));

            if(sneItem != null)
                sneItem.leftClickAtEntity((Player) event.getDamager(), event.getEntity());
        }
    }

    // When a player is damaged
    @EventHandler
    public void onPlayerDamage (final EntityDamageEvent event){

        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        {
            SneItem sneItem = ItemDictionary.get(new NBTItem(player.getInventory().getItemInMainHand()).getString(SneItem.nbtID));

            if(sneItem != null)
                sneItem.damagedWhileHeld(player, event);
        }

        for(ItemStack item : player.getInventory().getArmorContents()){

            if(item == null)
                continue;

            SneItem sneItem = ItemDictionary.get(new NBTItem(item).getString(SneItem.nbtID));

            if(sneItem == null)
                continue;

            // Check if player is killed
            if(player.getHealth() - event.getDamage() < 1){
                sneItem.killedWhileWorn(player, event);
            }

        }

    }
}
