package avernusvenine.sne.players;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.SpellDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.interactable.Interactable;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class CooldownHandler {

    protected final Player player;
    protected Map<String, CooldownTask> cooldownTasks = new HashMap<>();

    public CooldownHandler(Player player){
        this.player = player;
    }

    public void add(String id, int cooldown){
        if(cooldown == 0)
            return;

        cooldownTasks.put(id, new CooldownTask(id, cooldown));
        changeModelData(id, true);
    }

    public void remove(String id){
        cooldownTasks.get(id).cancel();
        cooldownTasks.remove(id);
    }

    public boolean check(String id){
        return cooldownTasks.containsKey(id);
    }

    protected void changeModelData(String id, boolean bool){
        for(ItemStack item : player.getInventory()){

            if(item == null || item.getType() == Material.AIR)
                continue;

            NBTItem nbtItem = new NBTItem(item);

            if(nbtItem.getString(NBTFlags.spellID).equals(id)){
                if(bool)
                    SneItem.setCustomModel(item, nbtItem.getInteger(NBTFlags.cooldownModelID));
                else
                    SneItem.setCustomModel(item, nbtItem.getInteger(NBTFlags.customModelID));
            }

        }
    }

    public void removeAll(){
        for(Map.Entry<String, CooldownTask> entry : cooldownTasks.entrySet())
            entry.getValue().cancel();

    }

    protected class CooldownTask{

        private final String id;
        private final BukkitTask task;

        public CooldownTask(String id, int cooldown){
            this.id = id;

            task = Bukkit.getScheduler().runTaskLater(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    changeModelData(id, false);
                    remove(id);
                }
            }, cooldown);
        }

        public void cancel(){
            task.cancel();
            changeModelData(id, false);
        }
    }

}
