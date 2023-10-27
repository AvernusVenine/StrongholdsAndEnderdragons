package avernusvenine.sne;

import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.questnpc.Leo;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class NPCDictionary {

    private static BiMap<String, SneNPC> npcDictionary = HashBiMap.create();
    private static BiMap<String, SneNPC> npcDictionaryUUID = HashBiMap.create();

    public static void loadNPCs(){
        {
            Leo npc = new Leo();
            npcDictionary.put(npc.getID(), npc);
            npcDictionaryUUID.put(npc.getUUID(), npc);
        }
    }

    public static SneNPC get(String id){
        return npcDictionary.get(id);
    }

    public static SneNPC getByUUID(String uuid){
        return npcDictionaryUUID.get(uuid);
    }

    public static String get(SneNPC npc){
        return npcDictionary.inverse().get(npc);
    }
}
