package avernusvenine.sne.items.consumable.cookable;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.items.consumable.Food;
import avernusvenine.sne.professions.Cooking;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HoneyBakedHam extends Food {

    public HoneyBakedHam(){
        id = "honey_baked_ham";
        material = Material.COOKED_PORKCHOP;

        foodLevel = 6;
        saturation = 4;

        rarity = Rarity.UNCOMMON;

        TextComponent displayName = Component.text("Honey Baked Ham").color(uncommonColor).toBuilder().build();
        item = generateFoodItem(material, 1, displayName, new ArrayList<>(), new ArrayList<>(), id, foodLevel, saturation);

        ItemDictionary.put(id, this);

        List<ItemStack> recipe = new ArrayList<>();
        recipe.add(new ItemStack(Material.HONEY_BOTTLE, 1));
        recipe.add(new ItemStack(Material.COOKED_PORKCHOP, 1));
        Cooking.addRecipe(recipe, item);
    }

}
