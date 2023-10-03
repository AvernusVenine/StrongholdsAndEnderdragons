package avernusvenine.sne.npc;

import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class SneNPC {

    protected net.citizensnpcs.api.npc.NPC npc;

    protected String id;
    protected String name;

    public SneNPC(){
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Default NPC");
        id = "default_npc";
    }

    public void spawnNPC(Location location){
        npc.spawn(location);
    }


    //Getters and setters
    public String getID(){
        return id;
    }

}
