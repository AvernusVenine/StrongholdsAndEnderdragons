package avernusvenine.sne.professions;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.SneItem.Rarity;
import avernusvenine.sne.items.consumable.fish.Fish;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fishing extends Profession {

    private final HashMap<Biome, LootTable> lootTables = new HashMap<>();

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

        public void generateTrash(ItemStack oldItem){

            Material[] trashMaterial = new Material[]{
                    Material.PAPER,
                    Material.IRON_NUGGET,
                    Material.BOWL,
                    Material.BONE_MEAL,
                    Material.STRING,
                    Material.KELP,
                    Material.SEAGRASS
            };

            int randomNum = (int) (trashMaterial.length * Math.random());

            oldItem.setType(trashMaterial[randomNum]);
            ItemMeta meta = oldItem.getItemMeta();

            TextComponent displayName = Component.text("Trash").color(TextColor.color(170, 170, 170));

            List<TextComponent> lore = new ArrayList<>();
            lore.add(Component.text("Old, useless trash").color(TextColor.color(170, 170, 170)));

            meta.displayName(displayName);
            meta.lore(lore);
            oldItem.setItemMeta(meta);
        }

        public void changeItem(Player player, ItemStack oldItem){
            int level = PlayerDictionary.get(player).getPlayerCharacter().getProfessionLevel(type);

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

            if(randomFloat <= tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.GARBAGE;
            else if(randomFloat <= tier.get(Rarity.COMMON)+tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.COMMON;
            else if(randomFloat <= tier.get(Rarity.UNCOMMON)+tier.get(Rarity.COMMON)+tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.UNCOMMON;
            else if(randomFloat <= tier.get(Rarity.RARE)+tier.get(Rarity.UNCOMMON)+tier.get(Rarity.COMMON)+tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.RARE;
            else if(randomFloat <= tier.get(Rarity.EPIC)+tier.get(Rarity.RARE)+tier.get(Rarity.UNCOMMON)+tier.get(Rarity.COMMON)
                    +tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.EPIC;
            else if(randomFloat <= tier.get(Rarity.LEGENDARY)+tier.get(Rarity.EPIC)+tier.get(Rarity.RARE)+tier.get(Rarity.UNCOMMON)
                    +tier.get(Rarity.COMMON)+tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.LEGENDARY;
            else if(randomFloat <= tier.get(Rarity.ARTIFACT)+tier.get(Rarity.LEGENDARY)+tier.get(Rarity.EPIC)+tier.get(Rarity.RARE)
                    +tier.get(Rarity.UNCOMMON) +tier.get(Rarity.COMMON)+tier.get(Rarity.GARBAGE))
                selectedRarity = Rarity.ARTIFACT;

            if(selectedRarity == Rarity.GARBAGE){
                generateTrash(oldItem);
                return;
            }

            List<SneItem> validItem = new ArrayList<>();

            for(ItemStack item : PlayerDictionary.get(player).getPlayerCharacter().getUnlockedRecipes(type)){
                NBTItem nbtItem = new NBTItem(item);

                if(ItemDictionary.get(nbtItem.getString(NBTFlags.itemID)) instanceof Fish fish){
                    if(fish.getRarity() == selectedRarity &&
                            fish.getBiomes().contains(player.getLocation().getBlock().getBiome())){
                        validItem.add(fish);
                    }
                }
                else{
                    validItem.add(ItemDictionary.get(nbtItem.getString(NBTFlags.itemID)));
                }
            }

            if(validItem.isEmpty()){
                generateTrash(oldItem);
                player.sendMessage("You do not have the knowledge required to catch this fish!");
                return;
            }

            int randomInt = (int) (Math.random() * validItem.size());
            SneItem selectedItem = validItem.get(randomInt);

            oldItem.setItemMeta(selectedItem.getItem().getItemMeta());
            oldItem.setType(selectedItem.getItem().getType());

            if(selectedItem instanceof Fish fish) {
                fish.sizeFish(oldItem);
                PlayerDictionary.get(player).getPlayerCharacter().addProfessionExperience(type, fish.getFishExperience(), player);
            }
        }
    }
}
