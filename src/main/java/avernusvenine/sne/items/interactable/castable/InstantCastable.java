package avernusvenine.sne.items.interactable.castable;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.spells.Spell;
import avernusvenine.sne.spells.targeted.TargetedSpell;
import org.bukkit.entity.Player;

public class InstantCastable extends Castable{

    @Override
    public void startCast(Player player, Spell spell){
        player.setCooldown(spell.getMaterial(), 20);
        PlayerDictionary.get(player).removeResource(spell.getResourceCost());
        spell.onCast(player);
    }

}
