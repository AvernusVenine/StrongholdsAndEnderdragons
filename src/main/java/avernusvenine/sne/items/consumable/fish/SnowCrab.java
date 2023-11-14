package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.professions.Profession.ProfessionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class SnowCrab extends Fish{

    public SnowCrab(){
        id = "snow_crab";
        material = Material.COOKED_COD;

        deviation = .03f;
        average = .15f;

        biomes.add(Biome.COLD_OCEAN);
        biomes.add(Biome.DEEP_COLD_OCEAN);
        biomes.add(Biome.FROZEN_OCEAN);
        biomes.add(Biome.DEEP_FROZEN_OCEAN);

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Snow Crab").color(commonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Commonly found around thermal vents in icy biomes").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 9);

        ItemDictionary.put(id, this);
        ProfessionType.FISHING.getProfession().addInitialRecipe(item);
    }
}
