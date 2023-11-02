package avernusvenine.sne.professions;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.PlayerDictionary;

import avernusvenine.sne.items.consumable.fish.Fish;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fishing extends DefaultProfession{

    private HashMap<Biome, LootTable> lootTables = new HashMap<>();

    public Fishing(){
        type = ProfessionType.FISHING;
        id = "fishing";

        loadLootTables();
    }

    private void loadLootTables(){
        //DEEP DARK
        LootTable lootTable = new LootTable();
        lootTable.addItem(5, ItemDictionary.get("sculk_fish").getItem(), 1);
        lootTables.put(Biome.DEEP_DARK, lootTable);

        // WARM OCEAN
        lootTable = new LootTable();
        lootTables.put(Biome.OCEAN, lootTable);
    }

    @EventHandler
    public void onPlayerFishEvent(final PlayerFishEvent event){
        Player player = event.getPlayer();
        int level = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter().getProfessionLevel(ProfessionType.FISHING);

        if(level == 0){
            player.sendMessage("Speak to a trainer to learn this profession!");
            event.setCancelled(true);
        }

        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            event.setCancelled(true);
            player.getInventory().addItem(lootTables.get(player.getLocation().getBlock().getBiome()).getItem(level));
        }
    }

    private class LootTable{

        private List<Integer> chance = new ArrayList<>();
        private List<ItemStack> item = new ArrayList<>();
        private List<Integer> levelRequirement = new ArrayList<>();

        public void addItem(int chance, ItemStack item, int level){
            this.chance.add(chance);
            this.item.add(item);
            this.levelRequirement.add(level);
        }

        public void addItem(int chance, ItemStack item){
            this.chance.add(chance);
            this.item.add(item);
            this.levelRequirement.add(0);
        }

        public ItemStack getItem(int level){
            ItemStack fishedItem = new ItemStack(Material.COD);

            int max = 0;

            for(int i = 0; i < chance.size(); i++)
                if(levelRequirement.get(i) <= level)
                    max += i;

            int randomNum = (int) (1 + (Math.random() * (max - 1)));

            int val = 0;
            for(int i = 0; i < chance.size(); i++){
                if(levelRequirement.get(i) <= level)
                    val += chance.get(i);
                else
                    continue;

                if(val >= randomNum){
                    fishedItem = item.get(i);
                    break;
                }
            }

            Fish fish = (Fish) ItemDictionary.get(new NBTItem(fishedItem).getString(NBTFlags.itemID));

            return fish.generateSizedFish();
        }
    }
}
