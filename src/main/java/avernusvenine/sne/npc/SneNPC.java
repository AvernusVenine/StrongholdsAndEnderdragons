package avernusvenine.sne.npc;

import avernusvenine.sne.quests.Quest;
import org.bukkit.Location;

import java.util.*;

public abstract class SneNPC {

    protected net.citizensnpcs.api.npc.NPC npc;

    protected DialogueSet dialogueSet;

    protected String id;
    protected String name;

    protected List<Quest> quests = new ArrayList<>();

    public void spawnNPC(Location location){
        npc.spawn(location);
    }

    //Getters and setters
    public String getID(){
        return id;
    }

    public DialogueSet getDialogueHandler(){
        return dialogueSet;
    }

    public String getUUID(){
        return npc.getUniqueId().toString();
    }

}
