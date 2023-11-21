package avernusvenine.sne.spells.targeted;

import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class CheapShot extends TargetedSpell{

    public CheapShot(){
        id = "cheap_shot";
        resourceCost = 15;
        levelRequired = 1;
        range = 3;
        damage = 1;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        if(getEntityTarget(player) instanceof LivingEntity entity){
            PotionEffect effect = new PotionEffect(PotionEffectType.DARKNESS, 3, 3);
            entity.addPotionEffect(effect);
            entity.damage(damage, player);
        }
    }

    @Override
    public Block getBlockTarget(Player player){
        return null;
    }
}
