package avernusvenine.sne;

import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.questnpc.Leo;
import avernusvenine.sne.npc.trainernpc.Joel;
import avernusvenine.sne.players.PlayerCharacter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class NPCDictionary {

    private static HashMap<String, SneNPC> npcDictionary = new HashMap<>();
    private static HashMap<String, SneNPC> npcDictionaryUUID = new HashMap<>();


    public static void loadNPCs(){
        new Leo();
        new Joel();

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

    public static void put(String id, SneNPC npc){
        npcDictionary.put(id, npc);
        npcDictionaryUUID.put(npc.getUUID(), npc);
    }

    public static SneNPC getByUUID(String uuid){
        return npcDictionaryUUID.get(uuid);
    }
}
