package avernusvenine.sne;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.consumable.cookable.*;
import avernusvenine.sne.items.consumable.fish.*;
import avernusvenine.sne.items.consumable.ingredient.*;
import avernusvenine.sne.items.interactable.castable.AbilityBook;
import avernusvenine.sne.items.interactable.castable.SpellBook;
import avernusvenine.sne.items.interactable.utility.*;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemDictionary {

    private static Map<String, SneItem> itemDictionary = new HashMap<>();

    public static void loadItems(){
        // INGREDIENTS
        new Salt();
        new Oil();
        new Chocolate();
        new RawFishFilet();

        // FISH
        new SculkScreecher();
        new CommonGreentop();
        new WardenOfTheDeep();
        new DeepslateSalmon();
        new StonePerch();
        new GlowingGoliath();
        new SuspiciousStarfish();
        new IronSwordfish();
        new GravelLurker();
        new FreshwaterFrogfish();
        new GoldenSnapper();
        new TuffTrout();
        new DiamondDiscus();
        new SnowCrab();
        new SlimeSkipper();
        new CreeperCarp();
        new RainbowShrimp();
        new SeaSpider();

        // COOKING
        new CookedFishFilet();
        new HoneyBakedHam();

        // UTILITY
        new CookingKit();

        // ITEMS
        new SpellBook();
        new AbilityBook();
    }

    public static void put(String id, SneItem item){
        itemDictionary.put(id, item);
    }

    public static void replace(String id, SneItem item){
        itemDictionary.replace(id, item);
    }

    public static SneItem get(String id){
        return itemDictionary.get(id);
    }

    public static SneItem get(ItemStack item){
        NBTItem nbtItem = new NBTItem(item);
        return itemDictionary.get(nbtItem.getString(NBTFlags.itemID));
    }

    public static Map<String, SneItem> getDictionary(){
        return itemDictionary;
    }

}
