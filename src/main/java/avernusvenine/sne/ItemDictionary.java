package avernusvenine.sne;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.consumable.fish.SculkFish;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ItemDictionary {

    private static BiMap<String, SneItem> itemDictionary = HashBiMap.create();

    public static void loadItems(){
        new SculkFish();
    }

    public static void put(String id, SneItem item){
        itemDictionary.put(id, item);
    }

    public static SneItem get(String id){
        return itemDictionary.get(id);
    }

    public static String get(SneItem item){
        return itemDictionary.inverse().get(item);
    }

}
