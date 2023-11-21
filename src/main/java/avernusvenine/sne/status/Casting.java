package avernusvenine.sne.status;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.spells.Spell;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class Casting extends StatusEffect{

    private final Spell spell;
    private CastingParticleTask particleTask;

    public Casting(LivingEntity entity, Spell spell){
        this.ticks = 0;
        this.timeBetweenTicks = spell.getCastTime();
        this.spell = spell;
        onInitial(entity);
    }

    @Override
    public void onTick(LivingEntity entity){

    }

    @Override
    public void onCancel(LivingEntity entity) {
        entity.removePotionEffect(PotionEffectType.SLOW);

        if(!(entity instanceof Player player))
            return;
        removeFromPlayer(player);
        particleTask.cancel();
    }

    @Override
    public void onInitial(LivingEntity entity) {
        PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, timeBetweenTicks, 4);
        entity.addPotionEffect(effect);

        if(!(entity instanceof Player player))
            return;

        task = new StatusEffectTask(player);
        particleTask = new CastingParticleTask(player);
    }

    @Override
    public void onCompletion(LivingEntity entity) {
        if(!(entity instanceof Player player))
            return;

        removeFromPlayer(player);
        particleTask.cancel();
        spell.onCast(player);
    }

    public class CastingParticleTask{
        private final BukkitTask task;

        public CastingParticleTask(Player player){

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    double inner_radius = 2.5;
                    double outer_radius = 3.5;

                    Location location = player.getLocation();

                    for(double i  = 0; i <= 2*Math.PI*inner_radius; i+=0.1){
                        double x = (inner_radius * Math.cos(i)) + location.getX();
                        double z = (location.getZ() + inner_radius * Math.sin(i));

                        Location particle = new Location(player.getWorld(), x, location.getY(), z);
                        Particle.DustOptions dustOption = new Particle.DustOptions(Color.RED, 1.5f);

                        player.getWorld().spawnParticle(Particle.REDSTONE, particle, 1, dustOption);
                    }

                    for(double i  = 0; i <= 2*Math.PI*outer_radius; i+=0.1){
                        double x = (outer_radius * Math.cos(i)) + location.getX();
                        double z = (location.getZ() + outer_radius * Math.sin(i));

                        Location particle = new Location(player.getWorld(), x, location.getY(), z);
                        Particle.DustOptions dustOption = new Particle.DustOptions(Color.RED, 1.5f);

                        player.getWorld().spawnParticle(Particle.REDSTONE, particle, 1, dustOption);
                    }

                }

            }, 0, 5);
        }

        public void cancel(){
            task.cancel();
        }
    }

}
