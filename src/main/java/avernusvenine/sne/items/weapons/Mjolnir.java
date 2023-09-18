package avernusvenine.sne.items.weapons;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.*;

import java.util.ArrayList;
import java.util.List;

public class Mjolnir extends CustomItem {

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
        String loreOne = ChatColor.WHITE + "" + ChatColor.BOLD + "Whosoever holds this hammer...";
        String loreTwo = ChatColor.GRAY + "" + ChatColor.ITALIC + "Strike an opponent with lightning (Cooldown: 3s)";
        String loreThree = ChatColor.GRAY + "" + ChatColor.ITALIC + "Summon a lightning storm around you (Cooldown: 20s)";
        lore.add(loreOne);
        lore.add(loreTwo);
        lore.add(loreThree);

        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = CustomItem.generateItem(material, 1, displayName, lore, itemFlags, true, id);
    }

    public void rightClickAir(Player player){

        int count = 3;
        int range = 4;

        long delay = 0;
        long period = 40;

        if(onCooldown(new NBTItem(player.getInventory().getItemInMainHand()), UseType.SPECIAL, specialCooldown)){
            alertCooldown(player, item, UseType.SPECIAL);
            return;
        }

        if(player.getInventory().getItemInOffHand() != null){
            System.out.println(player.getInventory().getItemInOffHand());
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
