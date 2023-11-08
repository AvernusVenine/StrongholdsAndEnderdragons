package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class GlowingGoliath extends Fish{

    public GlowingGoliath(){
        id = "glowing_goliath";
        material = Material.COOKED_COD;

        deviation = 1f;
        average = 8;

        biomes.add(Biome.LUSH_CAVES);

        rarity = Rarity.EPIC;

        TextComponent displayName = Component.text("Glowing Goliath").color(epicColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Great for the skin").color(commonColor).toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        item = setCustomModel(item, 4);
        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, 100, 1);
        player.addPotionEffect(effect);
    }

}
