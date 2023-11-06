package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class CommonGreentop extends Fish{

    public CommonGreentop(){
        id = "common_greentop";
        material = Material.COD;

        deviation = .2f;
        average = 1f;

        biomes.add(Biome.PLAINS);
        biomes.add(Biome.BIRCH_FOREST);
        biomes.add(Biome.MEADOW);
        biomes.add(Biome.CHERRY_GROVE);
        biomes.add(Biome.WINDSWEPT_FOREST);
        biomes.add(Biome.FOREST);
        biomes.add(Biome.FLOWER_FOREST);
        biomes.add(Biome.TAIGA);
        biomes.add(Biome.OLD_GROWTH_BIRCH_FOREST);
        biomes.add(Biome.DARK_FOREST);
        biomes.add(Biome.SUNFLOWER_PLAINS);

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Common Greentop").color(commonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Said the dirt hut was only for one night...").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);
    }

}
