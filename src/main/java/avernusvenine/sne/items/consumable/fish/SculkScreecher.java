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

public class SculkScreecher extends Fish{

    public SculkScreecher(){
        id = "sculk_screecher";
        material = Material.COD;

        deviation = .01f;
        average = .05f;

        biomes.add(Biome.DEEP_DARK);

        rarity = Rarity.EPIC;

        TextComponent displayName = Component.text("Sculk Screecher").color(epicColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("A small blind fish found in only the deepest of caves").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 80, 1);
        player.addPotionEffect(effect);
    }
}
