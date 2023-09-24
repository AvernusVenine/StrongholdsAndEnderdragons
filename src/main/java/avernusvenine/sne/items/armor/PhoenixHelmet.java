package avernusvenine.sne.items.armor;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.CustomItem;

import de.tr7zw.changeme.nbtapi.NBTItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PhoenixHelmet extends CustomItem {

    public PhoenixHelmet(){
        super();

        id = "phoenix_helmet";
        material = Material.NETHERITE_HELMET;
        slot = InventorySlot.HELMET;

        specialCooldown = timeToTicks(0, 30, 0);

        String displayName = ChatColor.GOLD + "" + ChatColor.MAGIC + "AA" + ChatColor.RESET
                + ChatColor.GOLD + "" + ChatColor.BOLD + "HELM OF THE PHOENIX" + ChatColor.RESET
                + ChatColor.GOLD + "" + ChatColor.MAGIC + "AA";

        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "A glimpse of rebirth");
        lore.add("");
        lore.add("");
        lore.add(ChatColor.GRAY + "Rebirth I");
        lore.add("");
        lore.add("");
        lore.add(ChatColor.GOLD + "Rebirth:");
        lore.add(ChatColor.GRAY + "Prevents the wearers death and ignites nearby creatures");
        lore.add("");
        lore.add("");
        lore.add(artifact);


        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = CustomItem.generateItem(material, 1, displayName, lore, itemFlags, true, id);
    }

    public void killedWhileWorn(Player player, EntityDamageEvent event){

        int range = 4;
        int knockbackSpeed = 3;
        int fireTicks = 60;

        ItemStack item = player.getInventory().getArmorContents()[3];

        if(item == null)
            return;

        if(onCooldown(new NBTItem(item), UseType.SPECIAL, specialCooldown)){
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

        addCooldown(item, UseType.SPECIAL, player, slot);

        player.setHealth(1);

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 4));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));

        player.getWorld().playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);

        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 1);

        List<Entity> entities = player.getNearbyEntities(range, range, range);

        for(Entity e : entities){

            if(StrongholdsAndEnderdragons.invalidEntities.contains(e.getType()))
                continue;

            Vector vector = e.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
            e.setVelocity(vector.multiply(knockbackSpeed));

            e.setFireTicks(fireTicks);
        }
    }

}
