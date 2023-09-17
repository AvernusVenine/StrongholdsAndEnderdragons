package avernusvenine.sne.items;

import avernusvenine.sne.StrongholdsAndEnderdragons;

import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemEventHandler implements Listener {

    // When a player right/left clicks with a given item
    @EventHandler
    public void onInteractEvent(final PlayerInteractEvent event){

        ItemStack eventItem = event.getItem();

        if (eventItem != null){
            if(event.getAction() == Action.RIGHT_CLICK_AIR){

                CustomItem customItem = StrongholdsAndEnderdragons.customMetaDictionary.get(eventItem.getItemMeta());

                if(customItem != null)
                    customItem.rightClickAir(event.getPlayer());

            }
            else if(event.getAction() == Action.LEFT_CLICK_AIR){

            }
        }
    }

    // When a player right clicks at an entity
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event){

    }

    // When a player damages another entity
    @EventHandler
    public void onPlayerDamageEntity(final EntityDamageByEntityEvent event){

        // When the player damages an entity
        if(event.getDamager() instanceof Player){
            ItemStack heldItem = ((Player) event.getDamager()).getInventory().getItemInMainHand();

            CustomItem customItem = StrongholdsAndEnderdragons.customMetaDictionary.get(heldItem.getItemMeta());

            if(customItem != null)
                customItem.leftClickAtEntity((Player) event.getDamager(), event.getEntity());
        }

        // When the player takes damage from an entity
        if(event.getEntity() instanceof Player){

            Player player = (Player) event.getEntity();

            for(ItemStack item : player.getInventory().getArmorContents()){

                if(item == null)
                    continue;

                CustomItem customItem = StrongholdsAndEnderdragons.customMetaDictionary.get(item.getItemMeta());

                if(customItem == null)
                    continue;

                // Check if player is killed
                if(player.getHealth() - event.getDamage() < 1){
                    customItem.killedWhileWorn(player, event.getDamager(), event);
                }

            }
        }
    }

    @EventHandler
    public void onItemCooldown(final PlayerItemCooldownEvent event){

    }

}
