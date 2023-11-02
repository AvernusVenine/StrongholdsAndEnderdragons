package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SculkFish extends Fish{

    public SculkFish(){
        id = "sculk_fish";
        material = Material.COD;

        TextComponent displayName = Component.text("Sculk Fish").color(epicColor).toBuilder().build();

        List<TextComponent> lore = new ArrayList<>();
        lore.add(Component.text("A small blind fish found in only the deepest of caves").color(commonColor)
                .toBuilder().build());

        item = generateFoodItem(material, 1, displayName, lore, new ArrayList<>(), id, foodLevel, saturation);
        setCustomModel(item, 0);

        ItemDictionary.put(id, this);
    }

    @Override
    public void onConsumption(Player player){
        PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 80, 1);
        player.addPotionEffect(effect);
    }
}
