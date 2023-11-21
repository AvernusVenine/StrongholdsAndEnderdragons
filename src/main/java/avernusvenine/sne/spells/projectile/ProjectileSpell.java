package avernusvenine.sne.spells.projectile;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.spells.Spell;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

public abstract class ProjectileSpell extends Spell {

    protected Material material;

    public abstract void onBlockHit(Player player, Block block, BlockFace face);
    public abstract void onEntityHit(Player player, LivingEntity entity);

    public void createProjectile(Player player){
        ItemStack item = new ItemStack(material, 1);
        NBTItem nbtItem = new NBTItem(item);

        nbtItem.setString(NBTFlags.spellProjectileID, id);
        nbtItem.setString(NBTFlags.projectileOwnerUUID, player.getUniqueId().toString());

        item = nbtItem.getItem();

        Location location = player.getEyeLocation().add(player.getLocation().getDirection());

        Snowball snowball = player.getWorld().spawn(location, Snowball.class);
        snowball.setItem(item);
        snowball.setVelocity(player.getLocation().getDirection().multiply(2));
    }

}
