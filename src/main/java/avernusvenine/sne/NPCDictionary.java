package avernusvenine.sne;

import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.questnpc.Leo;
import avernusvenine.sne.players.PlayerCharacter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import java.sql.SQLException;
import java.util.Map;

public class NPCDictionary {

    private static BiMap<String, SneNPC> npcDictionary = HashBiMap.create();
    private static BiMap<String, SneNPC> npcDictionaryUUID = HashBiMap.create();


    public static void loadNPCs(){
        {
            Leo npc = new Leo();
            npcDictionary.put(npc.getID(), npc);
            npcDictionaryUUID.put(npc.getUUID(), npc);
        }

        try {
            StrongholdsAndEnderdragons.databaseHandler.spawnNPCs();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void despawnAllNPCs(){
        for(Map.Entry<String, SneNPC> entry : npcDictionary.entrySet()){
            entry.getValue().destroyNPC();
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
