package avernusvenine.sne.items.consumable.cookable;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.items.consumable.Food;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;

import java.util.ArrayList;

public class RawFishFilet extends Food {

    public RawFishFilet(){
        id = "raw_fish_filet";
        material = Material.TROPICAL_FISH;

        foodLevel = 1;
        saturation = 0;

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Raw Fish Filet").color(commonColor).toBuilder().build();
        item = generateFoodItem(material, 1, displayName,new ArrayList<>(), new ArrayList<>(), id, foodLevel, saturation);

        ItemDictionary.put(id, this);
    }

}
