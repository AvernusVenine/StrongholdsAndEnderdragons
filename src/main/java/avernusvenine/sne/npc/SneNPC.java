package avernusvenine.sne.npc;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.dialogue.DialogueSet;
import avernusvenine.sne.npc.traits.DialogueTrait;
import avernusvenine.sne.quests.Quest;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.sql.SQLException;
import java.util.*;

public abstract class SneNPC {

    private static final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    protected net.citizensnpcs.api.npc.NPC npc;

    protected DialogueSet dialogueSet;

    protected String id;
    protected int id_value;
    protected String name;

    protected List<Quest> quests = new ArrayList<>();

    public void createNPC(){
        npc = registry.createNPC(EntityType.CAT, name);
        npc.addTrait(new DialogueTrait());
    }

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

    public DialogueSet getDialogueSet(){
        return dialogueSet;
    }

    public String getUUID(){
        return npc.getUniqueId().toString();
    }
}
