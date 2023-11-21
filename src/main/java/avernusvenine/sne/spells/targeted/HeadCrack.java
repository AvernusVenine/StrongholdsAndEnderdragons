package avernusvenine.sne.spells.targeted;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.status.Casting;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class HeadCrack extends TargetedSpell{

    public HeadCrack(){
        id = "head_crack";
        resourceCost = 10;
        levelRequired = 1;
        range = 3;
        damage = 1;
        cooldown = 20;

        material = Material.CLAY_BALL;
        customModel = 1;
        cooldownCustomModel = 2;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        if(getEntityTarget(player) instanceof LivingEntity entity){
            entity.damage(damage, player);

            if(entity instanceof Player target)
                PlayerDictionary.get(target).removeStatusEffect(Casting.class);
        }
    }

    @Override
    public Block getBlockTarget(Player player){
        return null;
    }
}
