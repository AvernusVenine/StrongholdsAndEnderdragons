package avernusvenine.sne;

import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.players.PlayerProfile;
import com.google.common.collect.BiMap;

import java.util.HashMap;
import java.util.Map;

public class PlayerDictionary {

    private static Map<String, PlayerProfile> playerDictionary = new HashMap<>();

    public static PlayerProfile get(String uuid){
        return playerDictionary.get(uuid);
    }

    public static void add(PlayerProfile profile){
        playerDictionary.put(profile.getUUID(), profile);
    }

    public static void remove(String uuid){
        playerDictionary.remove(uuid);
    }
}
