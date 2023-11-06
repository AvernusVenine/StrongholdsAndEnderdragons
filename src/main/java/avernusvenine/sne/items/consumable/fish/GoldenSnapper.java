package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class GoldenSnapper extends Fish{

    public GoldenSnapper(){
        id = "golden_snapper";
        material = Material.COD;

        deviation = .3f;
        average = 1.2f;

        biomes.add(Biome.BADLANDS);
        biomes.add(Biome.ERODED_BADLANDS);
        biomes.add(Biome.WOODED_BADLANDS);

        rarity = Rarity.RARE;

        TextComponent displayName = Component.text("Golden Snapper").color(rareColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Feeds on gold found throughout the badlands").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);
    }

}
