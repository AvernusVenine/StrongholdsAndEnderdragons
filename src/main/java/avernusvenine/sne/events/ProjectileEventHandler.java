package avernusvenine.sne.events;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.spells.projectile.ProjectileSpell;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ProjectileEventHandler implements Listener {

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event){

        if(event.getEntity() instanceof Snowball snowball){
            ItemStack item = snowball.getItem();
            NBTItem nbtItem = new NBTItem(item);

            if(SpellDictionary.get(nbtItem.getString(NBTFlags.spellProjectileID)) instanceof ProjectileSpell spell){

                Player player = Bukkit.getPlayer(UUID.fromString(nbtItem.getString(NBTFlags.projectileOwnerUUID)));

                if(event.getHitEntity() instanceof LivingEntity entity)
                    spell.onEntityHit(player, entity);
                else if(event.getHitBlock() != null)
                    spell.onBlockHit(player, event.getHitBlock(), event.getHitBlockFace());
            }
        }
    }

}
