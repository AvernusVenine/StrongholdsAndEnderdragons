package avernusvenine.sne.spells;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.status.SlowImmunity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Sprint extends Spell{

    public Sprint(){
        id = "sprint";
        resourceCost = 20;
        levelRequired = 1;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        customModel = 3;
        cooldownCustomModel = 4;

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        PlayerDictionary.get(player).addStatusEffect(new SlowImmunity(player, 120));
        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 6, 2);
        player.addPotionEffect(effect);
    }
}
