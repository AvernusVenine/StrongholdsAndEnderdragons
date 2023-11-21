package avernusvenine.sne.spells.targeted;

import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SwiftStep extends TargetedSpell{

    public SwiftStep(){
        id = "swift_step";
        resourceCost = 10;
        levelRequired = 1;
        range = 15;
        cooldown = 200;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        customModel = 1;
        cooldownCustomModel = 2;

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        Block target = getBlockTarget(player);

        if (target != null){
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 1);
            player.teleport(target.getLocation());
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 1);
        }
    }

    @Override
    public Entity getEntityTarget(Player player){
        return null;
    }
}
