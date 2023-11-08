package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class IronSwordfish extends Fish{

    public IronSwordfish(){
        id = "iron_swordfish";
        material = Material.COOKED_COD;

        deviation = .5f;
        average = 4;

        biomes.add(Biome.OCEAN);
        biomes.add(Biome.DEEP_OCEAN);
        biomes.add(Biome.LUKEWARM_OCEAN);
        biomes.add(Biome.DEEP_LUKEWARM_OCEAN);

        TextComponent displayName = Component.text("Iron Swordfish").color(rareColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        initialLore = Component.text("Almost as mighty as the pen").color(commonColor)
                .toBuilder().build();
        lore.add(initialLore);

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);

        item.getItemMeta().addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier("generic.attackDamage", 6, AttributeModifier.Operation.ADD_NUMBER));

        setCustomModel(item, 5);

        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        player.damage(2.0);
    }
}
