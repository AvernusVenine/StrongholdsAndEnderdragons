package avernusvenine.sne.spells.area;

import avernusvenine.sne.spells.Spell;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AreaSpell extends Spell {

    public List<Entity> getNearbyEntities(Player player){
        return player.getNearbyEntities(range, range, range);
    }

}
