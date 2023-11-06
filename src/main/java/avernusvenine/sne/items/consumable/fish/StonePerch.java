package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class StonePerch extends Fish{

    public StonePerch(){
        id = "stone_perch";
        material = Material.COD;

        deviation = .2f;
        average = .75f;

        biomes.add(Biome.STONY_PEAKS);
        biomes.add(Biome.WINDSWEPT_HILLS);
        biomes.add(Biome.WINDSWEPT_GRAVELLY_HILLS);
        biomes.add(Biome.WINDSWEPT_FOREST);
        biomes.add(Biome.STONY_SHORE);
        biomes.add(Biome.DRIPSTONE_CAVES);

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Stone Perch").color(commonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Not great for your teeth").color(commonColor).toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);
    }

}
