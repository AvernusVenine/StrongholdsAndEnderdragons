package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.professions.Profession.ProfessionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class SlimeSkipper extends Fish{

    public SlimeSkipper(){
        id = "slime_skipper";
        material = Material.COOKED_COD;

        deviation = .1f;
        average = .5f;

        biomes.add(Biome.SWAMP);
        biomes.add(Biome.MANGROVE_SWAMP);

        rarity = Rarity.UNCOMMON;

        TextComponent displayName = Component.text("Slime Skipper").color(uncommonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("A slippery catch!").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 12);

        ItemDictionary.put(id, this);
        ProfessionType.FISHING.getProfession().addInitialRecipe(item);
    }
}
