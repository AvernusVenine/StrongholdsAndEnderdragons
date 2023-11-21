package avernusvenine.sne.status;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SlowImmunity extends StatusEffect{

    public SlowImmunity(Player player, int duration){
        this.ticks = 0;
        this.timeBetweenTicks = duration;
        onInitial(player);
    }

    @Override
    public void onTick(LivingEntity entity) {

    }

    @Override
    public void onCancel(LivingEntity entity) {

    }

    @Override
    public void onInitial(LivingEntity entity) {
        entity.removePotionEffect(PotionEffectType.SLOW);
    }

    @Override
    public void onCompletion(LivingEntity entity) {

    }
}
