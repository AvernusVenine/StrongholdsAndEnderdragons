package avernusvenine.sne.status;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Bleeding extends StatusEffect{

    private static final HashMap<LivingEntity, StatusEffectTask> bleeding = new HashMap<>();

    public Bleeding(LivingEntity entity, int duration, int amplifier){
        ticks = duration;

        switch (amplifier){
            case 1 -> timeBetweenTicks = 20;
            case 2 -> timeBetweenTicks = 10;
            case 3 -> timeBetweenTicks = 5;
        }

        onInitial(entity);
    }

    @Override
    public void onTick(LivingEntity entity) {
        if(!entity.isValid()){
            task.cancel();
        }

        entity.damage(1);
    }

    @Override
    public void onCancel(LivingEntity entity) {
        task.cancel();

        if(entity instanceof Player player)
            removeFromPlayer(player);
        else
            bleeding.remove(entity);
    }

    @Override
    public void onInitial(LivingEntity entity) {
        task = new StatusEffectTask(entity);

        if(!(entity instanceof Player))
            bleeding.put(entity, task);
    }

    @Override
    public void onCompletion(LivingEntity entity) {

    }

    public static boolean checkBleeding(LivingEntity entity){
        return bleeding.containsKey(entity);
    }

    public static void removeBleeding(LivingEntity entity){
        if(!bleeding.containsKey(entity))
            return;

        bleeding.get(entity).cancel();
        bleeding.remove(entity);
    }
}
