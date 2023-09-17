package avernusvenine.sne.items.armor;

import avernusvenine.sne.items.CustomItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PhoenixHelmet extends CustomItem {

    public PhoenixHelmet(){
        id = "phoenix_helmet";
        material = Material.NETHERITE_HELMET;

        String displayName = ChatColor.GOLD + "" + ChatColor.MAGIC + "AA" + ChatColor.RESET
                + ChatColor.GOLD + "" + ChatColor.BOLD + "HELM OF THE PHOENIX" + ChatColor.RESET
                + ChatColor.GOLD + "" + ChatColor.MAGIC + "AA";

        List<String> lore = new ArrayList<String>();
        String loreOne = ChatColor.YELLOW + "A shard of immortality";
        lore.add(loreOne);

        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = CustomItem.generateItem(material, 1, displayName, lore, itemFlags, true);
    }

    public void killedWhileWorn(Player player, Entity entity, EntityDamageByEntityEvent event){
        if(player.getCooldown(material) > 0){
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

        player.setCooldown(material, 12000);

        player.setHealth(player.getMaxHealth()/10);

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 4));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));

        player.getWorld().playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
    }

}
