package avernusvenine.sne.items.weapons;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class Mjolnir extends CustomItem {

    public Mjolnir() {
        id = "mjolnir";
        material = Material.NETHERITE_AXE;

        String displayName = ChatColor.GRAY + "" + ChatColor.MAGIC + "AA" + ChatColor.RESET
                + ChatColor.GRAY + "" + ChatColor.BOLD + "MJOLNIR" + ChatColor.RESET
                + ChatColor.GRAY + "" + ChatColor.MAGIC + "AA";

        List<String> lore = new ArrayList<String>();
        String loreOne = ChatColor.DARK_PURPLE + "Whosoever holds this hammer...";
        lore.add(loreOne);

        List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
        itemFlags.add(ItemFlag.HIDE_ENCHANTS);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = CustomItem.generateItem(material, 1, displayName, lore, itemFlags, true);
    }

    public void rightClickAir(Player player){

        int count = 3;
        int range = 4;

        long delay = 0;
        long period = 40;

        if(player.getCooldown(material) > 0)
            return;

        player.setCooldown(material, 200);

        List<Entity> entities = player.getNearbyEntities(range, range, range);

        Runnable func = () -> {
            for(Entity entity : entities){
                if(entity.isDead() || StrongholdsAndEnderdragons.invalidEntities.contains(entity.getType()))
                    continue;

                entity.getWorld().strikeLightning(entity.getLocation());
            }
        };

        new TaskHandler(delay, period, count, func);

    }

    public void leftClickAtEntity(Player player, Entity entity){

        if(player.getCooldown(material) > 0)
            return;

        player.getWorld().strikeLightning(entity.getLocation());
        player.setCooldown(material, 60);

    }

}
