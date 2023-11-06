package avernusvenine.sne;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.consumable.fish.*;

import java.util.HashMap;
import java.util.Map;

public class ItemDictionary {

    private static Map<String, SneItem> itemDictionary = new HashMap<>();

    public static void loadItems(){

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

    public static Map<String, SneItem> getDictionary(){
        return itemDictionary;
    }

}
