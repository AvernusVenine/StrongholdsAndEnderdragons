package avernusvenine.sne.status;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public abstract class StatusEffect {

    protected int level;
    protected int ticks;
    protected int timeBetweenTicks;

    protected StatusEffectTask task;

    public abstract void onTick(LivingEntity entity);

    public abstract void onCancel(LivingEntity entity);

    public abstract void onInitial(LivingEntity entity);

    public abstract void onCompletion(LivingEntity entity);

    public void cancel(){
        task.cancel();
    }

    protected void removeFromPlayer(Player player){
        PlayerDictionary.get(player).removeStatusEffect(this);
    }

    public class StatusEffectTask{

        private final BukkitTask task;
        private final LivingEntity entity;

        private int counter = 0;

        public StatusEffectTask(LivingEntity entity){
            this.entity = entity;

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {

                    if(counter > ticks) {
                        complete();
                        return;
                    }

                    onTick(entity);
                    counter++;
                }
            }, 0, timeBetweenTicks);
        }

        public void cancel(){
            task.cancel();
            onCancel(entity);
        }

        public void complete(){
            task.cancel();
            onCompletion(entity);
        }

    }
}
