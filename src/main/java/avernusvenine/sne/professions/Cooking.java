package avernusvenine.sne.professions;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Cooking extends Profession {

    public Cooking(){
        type = ProfessionType.COOKING;
        id = "cooking";

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(StrongholdsAndEnderdragons.plugin,"test"),
                new ItemStack(Material.CACTUS));
        // RecipeChoice List represents all items in the recipe, must compare with items in cooking grid
        recipe.getChoiceList().get(1).test();
        recipe.
    }

}
