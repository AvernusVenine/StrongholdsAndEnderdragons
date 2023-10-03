package avernusvenine.sne;

import avernusvenine.sne.npc.SneNPC;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class NPCDictionary {

    private static BiMap<String, SneNPC> npcDictionary = HashBiMap.create();

    public static void loadNPCs(){
        {
            SneNPC npc = new SneNPC();
            npcDictionary.put(npc.getID(), npc);
        }
    }

    public static SneNPC get(String id){
        return npcDictionary.get(id);
    }

    public static String get(SneNPC npc){
        return npcDictionary.inverse().get(npc);
    }

}
