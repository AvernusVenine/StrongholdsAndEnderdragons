package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class RainbowShrimp extends Fish{

    public RainbowShrimp(){
        id = "rainbow_shrimp";
        material = Material.COOKED_COD;

        deviation = .0015f;
        average = .06f;

        biomes.add(Biome.WARM_OCEAN);
        biomes.add(Biome.LUKEWARM_OCEAN);
        biomes.add(Biome.DEEP_LUKEWARM_OCEAN);

        rarity = Rarity.RARE;

        TextComponent displayName = Component.text("Rainbow Shrimp").color(rareColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Pot of gold caught separately").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 10);

        ItemDictionary.put(id, this);
    }
}
