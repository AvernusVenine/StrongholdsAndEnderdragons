package avernusvenine.sne.status;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AnimalSpeaking extends StatusEffect{

    public AnimalSpeaking(int duration){
        this.ticks = 1;
        this.timeBetweenTicks = duration;
    }

    @Override
    public void onTick(LivingEntity entity) {

    }

    @Override
    public void onCancel(LivingEntity entity) {
        if(!(entity instanceof Player player))
            return;
        removeFromPlayer(player);
    }

    @Override
    public void onInitial(LivingEntity entity) {

    }

    @Override
    public void onCompletion(LivingEntity entity) {
        if(!(entity instanceof Player player))
            return;
        removeFromPlayer(player);
    }
}
