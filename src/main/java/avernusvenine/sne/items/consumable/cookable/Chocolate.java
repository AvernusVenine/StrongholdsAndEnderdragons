package avernusvenine.sne.items.consumable.cookable;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.items.consumable.Food;
import avernusvenine.sne.professions.Cooking;
import avernusvenine.sne.professions.Profession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.ArrayList;

public class Chocolate extends Food {

    public Chocolate(){
        id = "chocolate";
        material = Material.PUMPKIN_PIE;

        foodLevel = 1;
        saturation = 0;

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Chocolate").color(commonColor).toBuilder().build();
        item = generateFoodItem(material, 1, displayName, new ArrayList<>(), new ArrayList<>(), id, foodLevel, saturation);

        ItemDictionary.put(id, this);

        List<ItemStack> recipe = new ArrayList<>();
        recipe.add(new ItemStack(Material.COCOA_BEANS, 1));
        recipe.add(new ItemStack(Material.SUGAR, 1));
        Cooking.addRecipe(recipe, item);
    }

}
