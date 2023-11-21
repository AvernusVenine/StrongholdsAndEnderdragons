package avernusvenine.sne.spells;

import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Disperse extends Spell{

    public Disperse(){
        id = "disperse";
        resourceCost = 25;
        levelRequired = 1;
        range = 3;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        for(Entity entity : player.getNearbyEntities(range, range, range)){
            if(entity instanceof LivingEntity){
                Vector vector = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                entity.setVelocity(vector.multiply(1));
            }
        }
    }
}
