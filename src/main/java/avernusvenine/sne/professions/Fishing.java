package avernusvenine.sne.professions;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.SneItem.Rarity;
import avernusvenine.sne.items.consumable.fish.Fish;

import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fishing extends Profession {

    private HashMap<Biome, LootTable> lootTables = new HashMap<>();

    public Fishing(){
        type = ProfessionType.FISHING;
        id = "fishing";

        loadLootTables();
    }

    private void loadLootTables(){
        for(Map.Entry<String, SneItem> entry : ItemDictionary.getDictionary().entrySet()){
            if(entry.getValue() instanceof Fish fish){

                for(Biome biome : fish.getBiomes()){
                    if(!lootTables.containsKey(biome)){
                        LootTable lootTable = new LootTable();
                        lootTables.put(biome, lootTable);
                    }
                    lootTables.get(biome).addItem(fish);
                }

            }
        }
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
            Item temp = (Item) event.getCaught();
            assert temp != null;
            ItemStack item = temp.getItemStack();

            lootTables.get(player.getLocation().getBlock().getBiome()).changeItem(player, item);
        }
    }

    private class LootTable{

        private static HashMap<Rarity, Float> tierOne = new HashMap<>(){{
            put(Rarity.GARBAGE, 10f);
            put(Rarity.COMMON, 60f);
            put(Rarity.UNCOMMON, 20f);
            put(Rarity.RARE, 10f);
            put(Rarity.EPIC, 0f);
            put(Rarity.LEGENDARY, 0f);
            put(Rarity.ARTIFACT, 0f);
        }};

        private static HashMap<Rarity, Float> tierTwo = new HashMap<>(){{
            put(Rarity.GARBAGE, 7.5f);
            put(Rarity.COMMON, 55f);
            put(Rarity.UNCOMMON, 22.5f);
            put(Rarity.RARE, 12.5f);
            put(Rarity.EPIC, 2.5f);
            put(Rarity.LEGENDARY, 0f);
            put(Rarity.ARTIFACT, 0f);
        }};

        private static HashMap<Rarity, Float> tierThree = new HashMap<>(){{
            put(Rarity.GARBAGE, 5f);
            put(Rarity.COMMON, 50f);
            put(Rarity.UNCOMMON, 25f);
            put(Rarity.RARE, 15f);
            put(Rarity.EPIC, 4.5f);
            put(Rarity.LEGENDARY, .5f);
            put(Rarity.ARTIFACT, 0f);
        }};

        private static HashMap<Rarity, Float> tierFour = new HashMap<>(){{
            put(Rarity.GARBAGE, 2.5f);
            put(Rarity.COMMON, 45f);
            put(Rarity.UNCOMMON, 30f);
            put(Rarity.RARE, 16.5f);
            put(Rarity.EPIC, 5f);
            put(Rarity.LEGENDARY, 1f);
            put(Rarity.ARTIFACT, 0f);
        }};

        private static HashMap<Rarity, Float> tierFive = new HashMap<>(){{
            put(Rarity.GARBAGE, 0f);
            put(Rarity.COMMON, 42.5f);
            put(Rarity.UNCOMMON, 37.5f);
            put(Rarity.RARE, 17.5f);
            put(Rarity.EPIC, 7.5f);
            put(Rarity.LEGENDARY, 2.4f);
            put(Rarity.ARTIFACT, 0.1f);
        }};

        private List<SneItem> items = new ArrayList<>();

        public void addItem(SneItem item){
            this.items.add(item);
        }

        public void changeItem(Player player, ItemStack oldItem){
            int level = PlayerDictionary.get(player).getPlayerCharacter().getProfessionLevel(ProfessionType.FISHING);

            HashMap<Rarity, Float> tier;

            if(level < 25)
                tier = tierOne;
            else if(level < 50)
                tier = tierTwo;
            else if(level < 100)
                tier = tierThree;
            else if(level < 250)
                tier = tierFour;
            else
                tier = tierFive;

            float randomFloat = (float) (100 * Math.random());
            Rarity selectedRarity = Rarity.COMMON;

            if(randomFloat < tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.GARBAGE;
            randomFloat -= tier.get(Rarity.GARBAGE);
            if(randomFloat < tier.get(Rarity.COMMON))
                selectedRarity = Rarity.COMMON;
            randomFloat -= tier.get(Rarity.COMMON);
            if(randomFloat < tier.get(Rarity.UNCOMMON))
                selectedRarity = Rarity.UNCOMMON;
            randomFloat -= tier.get(Rarity.UNCOMMON);
            if(randomFloat < tier.get(Rarity.RARE))
                selectedRarity = Rarity.RARE;
            randomFloat -= tier.get(Rarity.RARE);
            if(randomFloat < tier.get(Rarity.EPIC))
                selectedRarity = Rarity.EPIC;
            randomFloat -= tier.get(Rarity.EPIC);
            if(randomFloat < tier.get(Rarity.LEGENDARY))
                selectedRarity = Rarity.LEGENDARY;
            randomFloat -= tier.get(Rarity.LEGENDARY);
            if(randomFloat < tier.get(Rarity.ARTIFACT))
                selectedRarity = Rarity.ARTIFACT;

            List<SneItem> validItem = new ArrayList<>();

            for(SneItem item : items){
                if(item instanceof Fish fish)
                    if(fish.canPlayerCatch(player) && fish.getRarity() == selectedRarity)
                        validItem.add(item);
                else{
                    validItem.add(item);
                }
            }

            int randomInt = (int) (Math.random() * validItem.size());
            SneItem selectedItem = validItem.get(randomInt);

            oldItem.setItemMeta(selectedItem.getItem().getItemMeta());
            oldItem.setType(selectedItem.getItem().getType());

            if(selectedItem instanceof Fish fish)
                fish.sizeFish(oldItem);
        }
    }
}
