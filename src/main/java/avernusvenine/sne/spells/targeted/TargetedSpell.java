package avernusvenine.sne.spells.targeted;


import avernusvenine.sne.spells.Spell;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public abstract class TargetedSpell extends Spell {


    public Entity getEntityTarget(Player player){
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation().add(player.getLocation().getDirection()), player.getEyeLocation().getDirection(),
                range, entity -> !entity.getUniqueId().equals(player.getUniqueId()));

        if(result == null)
            return null;

        return result.getHitEntity();
    }

    public Block getBlockTarget(Player player){
        RayTraceResult result = player.getWorld().rayTraceBlocks(
                player.getEyeLocation().add(player.getLocation().getDirection()), player.getEyeLocation().getDirection(),
                range);

        if(result == null)
            return null;

        return result.getHitBlock();
    }

}
