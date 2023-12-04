package avernusvenine.sne.npc;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.dialogue.Branch;
import avernusvenine.sne.quests.Quest;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public abstract class SneNPC {

    protected static final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    protected net.citizensnpcs.api.npc.NPC npc;

    protected String id;
    protected String name;


    protected HashMap<String, Quest> quests = new HashMap<>();
    protected HashMap<String, Branch> trees = new HashMap<>();
    protected HashMap<String, Branch> branches = new HashMap<>();

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

    public abstract Branch getBranch(Player player);

    public String getUUID(){
        return npc.getUniqueId().toString();
    }
}
