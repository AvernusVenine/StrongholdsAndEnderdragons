package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.professions.Profession.ProfessionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class DeepslateSalmon extends Fish{

    public DeepslateSalmon(){
        id = "deepslate_salmon";
        material = Material.COOKED_COD;

        deviation = .5f;
        average = 2;

        biomes.add(Biome.DEEP_DARK);
        biomes.add(Biome.LUSH_CAVES);

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Deepslate Salmon").color(commonColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Lesser known cousin of the stone salmon").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 3);

        ItemDictionary.put(id, this);
        ProfessionType.FISHING.getProfession().addInitialRecipe(item);
    }
}
