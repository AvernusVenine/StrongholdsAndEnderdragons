package avernusvenine.sne.spells.targeted;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.status.food.FoodBuff;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GutShot extends TargetedSpell{

    public GutShot(){
        id = "gut_shot";
        resourceCost = 10;
        levelRequired = 1;
        range = 3;
        damage = 1;

        cooldown = 30;

        material = Material.CLAY_BALL;
        customModel = 3;
        cooldownCustomModel = 4;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        Entity entity = getEntityTarget(player);

        if(entity instanceof LivingEntity living){
            living.damage(damage, player);

            if(living instanceof Player target) {
                PlayerDictionary.get(target).removeStatusEffect(FoodBuff.class);
                PotionEffect effect = new PotionEffect(PotionEffectType.HUNGER, 120, 1);
                target.addPotionEffect(effect);
            }
        }
    }

    @Override
    public Block getBlockTarget(Player player){
        return null;
    }
}
