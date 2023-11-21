package avernusvenine.sne.spells.targeted;

import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;

public class CoupDeGrace extends TargetedSpell{

    public CoupDeGrace(){
        id = "coup_de_grace";
        resourceCost = 0;
        levelRequired = 1;
        range = 3;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        LivingEntity entity = (LivingEntity) getEntityTarget(player);
        entity.damage(entity.getHealth(), player);
        player.playSound(player, Sound.BLOCK_ANVIL_BREAK, 1, 1);
    }

    @Override
    public Block getBlockTarget(Player player){
        return null;
    }

    @Override
    public Entity getEntityTarget(Player player){
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation().add(player.getLocation().getDirection()), player.getEyeLocation().getDirection(),
                range, entity -> !entity.getUniqueId().equals(player.getUniqueId()));

        if(result == null || !(result.getHitEntity() instanceof LivingEntity target))
            return null;

        if((target.getHealth() / target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) < .2)
            return target;

        return null;
    }
}
