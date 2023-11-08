package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class WardenOfTheDeep extends Fish{

    public WardenOfTheDeep(){
        id = "warden_of_the_deep";
        material = Material.COOKED_COD;

        deviation = 1.5f;
        average = 10;

        biomes.add(Biome.DEEP_DARK);

        rarity = Rarity.LEGENDARY;

        TextComponent displayName = Component.text("Warden Of The Deep").color(legendaryColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Best left uneaten...").color(commonColor).toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 2);
        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 60, 10);
        player.addPotionEffect(effect);
        player.getWorld().spawnEntity(player.getLocation(), EntityType.WARDEN);
        player.getWorld().playSound(player, Sound.ENTITY_WARDEN_EMERGE, 1, 1);
    }

}
