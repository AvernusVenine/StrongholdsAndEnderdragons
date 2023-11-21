package avernusvenine.sne.spells.area;

import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.classes.DefaultClass;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class FanOfKnives extends AreaSpell{

    public FanOfKnives(){
        id = "fan_of_knives";
        resourceCost = 25;
        levelRequired = 1;
        range = 5;
        damage = 5;
        cooldown = 200;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        customModel = 5;
        cooldownCustomModel = 6;

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        List<Entity> targets = getNearbyEntities(player);

        new KnivesParticleTask(player);

        if(targets.isEmpty())
            return;

        for(Entity entity : targets){
            if(entity instanceof LivingEntity living){
                living.damage(damage, player);
            }
        }

    }

    private class KnivesParticleTask {

        private final BukkitTask task;
        private double radius = 2;
        private double height;

        public KnivesParticleTask(Player player){
            height = player.getEyeLocation().getY() - .5;

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    if(radius > range)
                        task.cancel();

                    Location location = player.getLocation();

                    for(double i  = 0; i <= 2*Math.PI*radius; i+=0.25){
                        double x = (radius * Math.cos(i)) + location.getX();
                        double z = (location.getZ() + radius * Math.sin(i));

                        Location particle = new Location(player.getWorld(), x, height, z);

                        player.getWorld().spawnParticle(Particle.CRIT, particle, 1);
                    }

                    radius += .5;
                    height -= .05;
                }

            }, 0, 1);
        }

    }
}
