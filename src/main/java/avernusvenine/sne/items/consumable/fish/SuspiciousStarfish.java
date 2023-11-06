package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SuspiciousStarfish extends Fish{

    public SuspiciousStarfish(){
        id = "suspicious_starfish";
        material = Material.COD;

        deviation = .04f;
        average = .2f;

        biomes.add(Biome.BEACH);
        biomes.add(Biome.OCEAN);
        biomes.add(Biome.WARM_OCEAN);
        biomes.add(Biome.LUKEWARM_OCEAN);

        rarity = Rarity.RARE;

        TextComponent displayName = Component.text("Suspicious Starfish").color(rareColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Could've sworn you saw it vent").color(commonColor).toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 1, 1);

        int rand = (int) (Math.random() * 10);

        switch (rand){
            case 0:
                effect = new PotionEffect(PotionEffectType.POISON, 100, 2);
                break;
            case 1:
                effect = new PotionEffect(PotionEffectType.BLINDNESS, 80, 1);
                break;
            case 2:
                effect = new PotionEffect(PotionEffectType.WITHER, 120, 1);
                break;
            case 3:
                effect = new PotionEffect(PotionEffectType.CONFUSION, 100, 1);
                break;
            case 4:
                effect = new PotionEffect(PotionEffectType.HUNGER, 100, 3);
                break;
            case 5:
                effect = new PotionEffect(PotionEffectType.WEAKNESS, 200, 2);
                break;
            case 6:
                effect = new PotionEffect(PotionEffectType.SLOW, 80, 3);
                break;
            case 7:
                player.setFireTicks(80);
                return;
            case 8:
                effect = new PotionEffect(PotionEffectType.REGENERATION, 80, 2);
                break;
            case 9:
                effect = new PotionEffect(PotionEffectType.ABSORPTION, 600, 1);
                break;
            case 10:
                effect = new PotionEffect(PotionEffectType.GLOWING, 100, 1);
                break;
            case 11:
                effect = new PotionEffect(PotionEffectType.LEVITATION, 120, 1);
                break;
            case 12:
                effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 10);
                break;
            case 13:
                effect = new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 1);
                break;
            case 14:
                effect = new PotionEffect(PotionEffectType.WATER_BREATHING, 300, 1);
                break;
            case 15:
                effect = new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 1);
                break;
        }

        player.addPotionEffect(effect);
    }

}
