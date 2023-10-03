package avernusvenine.sne;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.armor.PhoenixHelmet;
import avernusvenine.sne.items.misc.Midas;
import avernusvenine.sne.items.misc.PlayerJournal;
import avernusvenine.sne.items.weapons.Mjolnir;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ItemDictionary {

    private static BiMap<String, SneItem> itemDictionary = HashBiMap.create();

    public static void loadItems(){
        {
            SneItem item = new SneItem();
            itemDictionary.put(item.getID(), item);
        }
        {
            Mjolnir item = new Mjolnir();
            itemDictionary.put(item.getID(), item);
        }
        {
            PhoenixHelmet item = new PhoenixHelmet();
            itemDictionary.put(item.getID(), item);
        }
        {
            Midas item = new Midas();
            itemDictionary.put(item.getID(), item);
        }
        {
            PlayerJournal item = new PlayerJournal();
            itemDictionary.put(item.getID(), item);
        }
    }

    public static SneItem get(String id){
        return itemDictionary.get(id);
    }

    public static String get(SneItem item){
        return itemDictionary.inverse().get(item);
    }

}
