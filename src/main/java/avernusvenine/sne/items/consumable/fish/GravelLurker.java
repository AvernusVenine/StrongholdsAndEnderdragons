package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class GravelLurker extends Fish{

    public GravelLurker(){
        id = "gravel_lurker";
        material = Material.COOKED_COD;

        deviation = .25f;
        average = 1;

        biomes.add(Biome.STONY_PEAKS);
        biomes.add(Biome.WINDSWEPT_HILLS);
        biomes.add(Biome.WINDSWEPT_GRAVELLY_HILLS);
        biomes.add(Biome.TAIGA);
        biomes.add(Biome.STONY_SHORE);
        biomes.add(Biome.DRIPSTONE_CAVES);

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Gravel Lurker").color(commonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Nests in large gravel patches next to sources of water").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 6);

        ItemDictionary.put(id, this);
    }

}
