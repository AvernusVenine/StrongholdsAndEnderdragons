package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class SeaSpider extends Fish{

    public SeaSpider(){
        id = "sea_spider";
        material = Material.COOKED_COD;

        deviation = .1f;
        average = .5f;

        biomes.add(Biome.BEACH);
        biomes.add(Biome.OCEAN);
        biomes.add(Biome.LUKEWARM_OCEAN);
        biomes.add(Biome.WARM_OCEAN);
        biomes.add(Biome.COLD_OCEAN);
        biomes.add(Biome.STONY_SHORE);

        rarity = Rarity.UNCOMMON;

        TextComponent displayName = Component.text("Sea Spider").color(uncommonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Close relative of the more poisonous Sea Cave Spider").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 14);

        ItemDictionary.put(id, this);
    }
}
