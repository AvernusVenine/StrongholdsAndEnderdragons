package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.professions.Profession.ProfessionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreeperCarp extends Fish{

    public CreeperCarp(){
        id = "creeper_carp";
        material = Material.COOKED_COD;

        deviation = .1f;
        average = .75f;

        biomes.add(Biome.PLAINS);
        biomes.add(Biome.MEADOW);
        biomes.add(Biome.TAIGA);
        biomes.add(Biome.SUNFLOWER_PLAINS);
        biomes.add(Biome.RIVER);
        biomes.add(Biome.DRIPSTONE_CAVES);
        biomes.add(Biome.FOREST);
        biomes.add(Biome.FLOWER_FOREST);
        biomes.add(Biome.CHERRY_GROVE);
        biomes.add(Biome.STONY_PEAKS);
        biomes.add(Biome.WINDSWEPT_HILLS);
        biomes.add(Biome.WINDSWEPT_GRAVELLY_HILLS);
        biomes.add(Biome.WINDSWEPT_FOREST);
        biomes.add(Biome.TAIGA);
        biomes.add(Biome.OLD_GROWTH_BIRCH_FOREST);
        biomes.add(Biome.OLD_GROWTH_PINE_TAIGA);
        biomes.add(Biome.OLD_GROWTH_SPRUCE_TAIGA);
        biomes.add(Biome.BIRCH_FOREST);
        biomes.add(Biome.DARK_FOREST);

        rarity = Rarity.UNCOMMON;

        TextComponent displayName = Component.text("Creeper Carp").color(uncommonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Handle with care").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 21);

        ItemDictionary.put(id, this);
        ProfessionType.FISHING.getProfession().addInitialRecipe(item);
    }

    @Override
    public void onConsumption(Player player) {
        player.getWorld().createExplosion(player, 0);
    }
}
