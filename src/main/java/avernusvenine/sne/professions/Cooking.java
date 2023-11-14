package avernusvenine.sne.professions;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.PlayerDictionary;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Cooking extends Profession {

    private static HashMap<List<ItemStack>, ItemStack> recipes = new HashMap<>();

    public Cooking(){
        type = ProfessionType.COOKING;
        id = "cooking";
    }

    public static boolean checkRecipes(List<ItemStack> input, ItemStack result, Player player){

        // Checking for fish recycling
        int count = 0;
        boolean recycling = true;
        for(ItemStack item : input){
            if(item.getType() == Material.COOKED_COD){
                NBTItem nbtItem = new NBTItem(item);
                count += (int) Math.pow(2, nbtItem.getInteger(NBTFlags.fishSize));
            }
            else{
                recycling = false;
                break;
            }
        }

        if(recycling && !input.isEmpty()){
            ItemStack filet = ItemDictionary.get("raw_fish_filet").getItem();
            result.setType(filet.getType());
            result.setItemMeta(filet.getItemMeta());
            result.setAmount(count);
            return true;
        }

        for(Map.Entry<List<ItemStack>, ItemStack> entry : recipes.entrySet()){
            boolean valid = false;
            List<ItemStack> items = new ArrayList<>(input);

            for(ItemStack recipeItem : entry.getKey()){
                valid = false;

                for(ItemStack givenItem : items){
                    if(compareItemNBT(recipeItem, givenItem)){
                        valid = true;
                        items.remove(givenItem);
                        break;
                    }
                }

                if(!valid)
                    break;
            }

            if(valid){
                if(PlayerDictionary.get(player).getPlayerCharacter().hasRecipeUnlocked(entry.getValue(), ProfessionType.COOKING)) {
                    result.setType(entry.getValue().getType());
                    result.setItemMeta(entry.getValue().getItemMeta());
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private static boolean compareItemNBT(ItemStack itemOne, ItemStack itemTwo){
        NBTItem nbtOne = new NBTItem(itemOne);
        NBTItem nbtTwo = new NBTItem(itemTwo);

        if(nbtOne.getString(NBTFlags.itemID).equalsIgnoreCase("") &&
                nbtTwo.getString(NBTFlags.itemID).equalsIgnoreCase("")){
            return itemOne.getType() == itemTwo.getType();
        }

        return nbtOne.getString(NBTFlags.itemID).equalsIgnoreCase(nbtTwo.getString(NBTFlags.itemID)) &&
                nbtOne.getInteger(NBTFlags.fishSize) <= nbtTwo.getInteger(NBTFlags.fishSize);
    }

    public static void addRecipe(List<ItemStack> recipe, ItemStack result){
        recipes.put(recipe, result);
    }
}
