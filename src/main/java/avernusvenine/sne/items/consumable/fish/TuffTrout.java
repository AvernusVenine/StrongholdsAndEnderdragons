package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class TuffTrout extends Fish{

    public TuffTrout(){
        id = "tuff_trout";
        material = Material.COOKED_COD;

        deviation = .2f;
        average = 1f;

        biomes.add(Biome.DEEP_DARK);
        biomes.add(Biome.LUSH_CAVES);
        biomes.add(Biome.DRIPSTONE_CAVES);

        rarity = Rarity.UNCOMMON;

        TextComponent displayName = Component.text("Tuff Trout").color(uncommonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Tuff on the teeth").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 24);
        ItemDictionary.put(id, this);
    }

}
