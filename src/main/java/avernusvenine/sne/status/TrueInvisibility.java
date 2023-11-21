package avernusvenine.sne.status;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;

public class TrueInvisibility extends StatusEffect{

    public TrueInvisibility(Player player, int duration){
        this.ticks = duration;
        this.timeBetweenTicks = 1;
        onInitial(player);
    }

    @Override
    public void onTick(LivingEntity entity) {
        byte[] empty = new byte[]{0, 0, 0, 0, 0, 0};
        PacketContainer packet = StrongholdsAndEnderdragons.manager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, entity.getEntityId());
        packet.getByteArrays().write(0, empty);

        for(Entity ent : entity.getNearbyEntities(50, 50, 50)){
            if(!(ent instanceof Player receiver))
                continue;
            try {
                StrongholdsAndEnderdragons.manager.sendServerPacket(receiver, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCancel(LivingEntity entity) {
        entity.removePotionEffect(PotionEffectType.INVISIBILITY);
        task.cancel();
    }

    @Override
    public void onInitial(LivingEntity entity) {
        PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, ticks, 1);
        entity.addPotionEffect(effect);

        task = new StatusEffectTask(entity);
    }

    @Override
    public void onCompletion(LivingEntity entity) {

    }
}
