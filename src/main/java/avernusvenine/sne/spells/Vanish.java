package avernusvenine.sne.spells;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.status.TrueInvisibility;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Vanish extends Spell{

    public Vanish(){
        id = "vanish";
        resourceCost = 0;
        levelRequired = 1;

        validClasses = new ArrayList<>();
        validClasses.add(DefaultClass.ClassType.ROGUE);

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        PlayerDictionary.get(player).addStatusEffect(new TrueInvisibility(player, 200));
        PlayerDictionary.get(player).addResource(50);
    }
}
