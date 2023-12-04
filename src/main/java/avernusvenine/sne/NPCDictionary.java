package avernusvenine.sne;

import avernusvenine.sne.npc.Leo;
import avernusvenine.sne.npc.SneNPC;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCDictionary {

    private static HashMap<String, SneNPC> npcDictionary = new HashMap<>();
    private static HashMap<String, SneNPC> npcDictionaryUUID = new HashMap<>();


    public static void loadNPCs(){
        new Leo();

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

    public static SneNPC get(UUID uuid){
        return npcDictionaryUUID.get(uuid.toString());
    }
}
