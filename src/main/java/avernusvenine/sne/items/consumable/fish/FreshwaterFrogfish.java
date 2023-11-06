package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.items.SneItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FreshwaterFrogfish extends Fish{

    public FreshwaterFrogfish(){
        id = "freshwater_frogfish";
        material = Material.COD;

        deviation = .03f;
        average = .2f;

        biomes.add(Biome.SWAMP);
        biomes.add(Biome.MANGROVE_SWAMP);

        rarity = SneItem.Rarity.RARE;

        TextComponent displayName = Component.text("Freshwater Frogfish").color(rareColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Ribbit").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        Vector v = player.getVelocity();
        v.setY(5);
        player.setVelocity(v);
    }

}
