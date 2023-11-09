package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class DiamondDiscus extends Fish{

    public DiamondDiscus(){
        id = "diamond_discus";
        material = Material.COOKED_COD;

        deviation = .1f;
        average = .5f;

        biomes.add(Biome.DEEP_DARK);
        biomes.add(Biome.DRIPSTONE_CAVES);


        rarity = Rarity.RARE;

        TextComponent displayName = Component.text("Diamond Discus").color(rareColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Recently forced to migrate from Y-Level 11 to deeper caves").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 23);

        ItemDictionary.put(id, this);
    }
}
