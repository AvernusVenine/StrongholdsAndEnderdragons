package avernusvenine.sne.spells.targeted;

import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass.ClassType;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ShadowStrike extends TargetedSpell{

    public ShadowStrike(){
        id = "shadow_strike";
        resourceCost = 20;
        levelRequired = 1;
        range = 6;
        damage = 5;

        validClasses = new ArrayList<>();
        validClasses.add(ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        if(getEntityTarget(player) instanceof LivingEntity entity) {
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 1);
            player.teleport(entity.getLocation());
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 1);
            entity.damage(damage, player);
        }
    }

    @Override
    public Block getBlockTarget(Player player){
        return null;
    }
}
