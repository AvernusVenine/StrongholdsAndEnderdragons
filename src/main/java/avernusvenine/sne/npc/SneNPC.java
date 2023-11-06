package avernusvenine.sne.npc;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.dialogue.DialogueSet;
import avernusvenine.sne.npc.dialogue.DialogueSet.DialogueType;
import avernusvenine.sne.quests.Quest;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public abstract class SneNPC {

    protected static final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    protected net.citizensnpcs.api.npc.NPC npc;

    protected HashMap<DialogueType, DialogueSet> dialogueSet = new HashMap<>();

    protected String id;
    protected String name;

    protected List<Quest> quests = new ArrayList<>();

    public abstract void createNPC();

    public void spawnNPC(Location location){
        npc.despawn();
        npc.spawn(location);
    }

    public void despawnNPC(){
        npc.despawn();
    }

    public void destroyNPC(){
        npc.destroy();
    }

    public void saveToDatabase(){
        try {
            Location location = npc.getStoredLocation();
            StrongholdsAndEnderdragons.databaseHandler.setNPCInfo(id, location.getBlockX(), location.getBlockY(),
                    location.getBlockZ(), location.getPitch(), location.getYaw(), location.getWorld().getName());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Getters and setters
    public String getID(){
        return id;
    }

    public DialogueSet getDialogueSet(Player player){
        return dialogueSet.get(DialogueType.DEFAULT);
    }

    public String getUUID(){
        return npc.getUniqueId().toString();
    }
}
