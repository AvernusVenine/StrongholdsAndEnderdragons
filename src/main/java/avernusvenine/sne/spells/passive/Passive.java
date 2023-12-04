package avernusvenine.sne.spells.passive;

import avernusvenine.sne.spells.Spell;
import org.bukkit.entity.Player;

public abstract class Passive extends Spell {

    public abstract void addPassive(Player player);
    public abstract void removePassive(Player player);

}
