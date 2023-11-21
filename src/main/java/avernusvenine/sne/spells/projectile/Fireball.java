package avernusvenine.sne.spells.projectile;

import avernusvenine.sne.Globals;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.classes.DefaultClass.ClassType;
import avernusvenine.sne.spells.Spell;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Fireball extends ProjectileSpell {

    public Fireball(){
        id = "fireball";
        castTime = 40;
        resourceCost = 10;
        levelRequired = 1;

        material = Material.FIRE_CHARGE;

        validClasses = Globals.MANA_USERS;

        SpellDictionary.add(this);
    }

    @Override
    public void onCast(Player player) {
        createProjectile(player);
    }

    @Override
    public void onBlockHit(Player player, Block block, BlockFace face) {
        block.getWorld().createExplosion(block.getLocation(), 3.5f, false, false);
    }

    @Override
    public void onEntityHit(Player player, LivingEntity entity) {
        entity.getWorld().createExplosion(entity.getLocation(), 3, false, false);
    }
}
