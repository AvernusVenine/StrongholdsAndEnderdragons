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

public class CookedFishFilet extends Food {

    public CookedFishFilet(){
        id = "cooked_fish_filet";
        material = Material.COOKED_SALMON;

        foodLevel = 4;
        saturation = 1;

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Cooked Fish Filet").color(commonColor).toBuilder().build();
        item = generateFoodItem(material, 1, displayName,new ArrayList<>(), new ArrayList<>(), id, foodLevel, saturation);
        ItemDictionary.put(id, this);

        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(StrongholdsAndEnderdragons.plugin, "cooked_fish_filet"),
                item, Material.TROPICAL_FISH, .1f, 100);
        Bukkit.addRecipe(recipe);
    }

}
