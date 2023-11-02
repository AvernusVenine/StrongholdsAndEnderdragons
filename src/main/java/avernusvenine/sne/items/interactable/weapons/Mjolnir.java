package avernusvenine.sne.items.interactable.weapons;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.interactable.Interactable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;

import de.tr7zw.changeme.nbtapi.NBTItem;

import java.util.ArrayList;
import java.util.List;

public class Mjolnir extends Interactable {

    public Mjolnir() {
        super();

        id = "mjolnir";
        material = Material.NETHERITE_AXE;
        slot = InventorySlot.MAIN_HAND;

        basicCooldown = timeToTicks(3, 0, 0);
        specialCooldown = timeToTicks(20, 0, 0);

        String displayName = ChatColor.WHITE + "" + ChatColor.MAGIC + "AA" + ChatColor.RESET
                + ChatColor.WHITE + "" + ChatColor.BOLD + "MJOLNIR" + ChatColor.RESET
                + ChatColor.WHITE + "" + ChatColor.MAGIC + "AA";

        List<String> lore = new ArrayList<String>();

        lore.add(ChatColor.WHITE + "Whosoever holds this hammer...");
        lore.add("");
        lore.add("");
        lore.add(ChatColor.GRAY + "Thundering V");
        lore.add(ChatColor.GRAY + "Lightning Storm III");
        lore.add("");
        lore.add("");
        lore.add(ChatColor.WHITE + "Thundering:");
        lore.add(ChatColor.GRAY + "Strikes a creature with lightning");
        lore.add("");
        lore.add(ChatColor.WHITE + "Lightning Storm:");
        lore.add(ChatColor.GRAY + "Summon a lightning storm to strike nearby creatures");
        lore.add("");
        lore.add("");
        lore.add(artifact);

        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = generateItem(material, 1, displayName, lore, itemFlags, true, id);
    }

    public void rightClickAir(Player player){

        int count = 3;
        int range = 4;

        long delay = 0;
        long period = 40;

        if(onCooldown(new NBTItem(player.getInventory().getItemInMainHand()), UseType.SPECIAL, specialCooldown)){
            alertCooldown(player, specialCooldown, UseType.SPECIAL);
            return;
        }

        if(player.getInventory().getItemInOffHand().getType() != Material.AIR){
            return;
        }

        addCooldown(item, UseType.SPECIAL, player, slot);

        Runnable func = () -> {

            List<Entity> entities = player.getNearbyEntities(range, range, range);

            for(Entity entity : entities){
                if(entity.isDead() || StrongholdsAndEnderdragons.invalidEntities.contains(entity.getType()))
                    continue;

                entity.getWorld().strikeLightning(entity.getLocation());
            }
        };

        new TaskHandler(delay, period, count, func);
        player.getWorld().strikeLightning(player.getLocation());

    }

    public void leftClickAtEntity(Player player, Entity entity){

        if(onCooldown(new NBTItem(player.getInventory().getItemInMainHand()), UseType.BASIC, basicCooldown))
            return;

        addCooldown(item, UseType.BASIC, player, slot);

        if(StrongholdsAndEnderdragons.invalidEntities.contains(entity.getType()))
            return;

        player.getWorld().strikeLightning(entity.getLocation());
    }

    public void damagedWhileHeld(Player player, EntityDamageEvent event){
        if(event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING)
            event.setCancelled(true);
    }

}
