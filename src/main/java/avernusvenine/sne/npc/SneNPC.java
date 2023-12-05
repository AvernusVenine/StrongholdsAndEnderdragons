package avernusvenine.sne.npc;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.dialogue.*;
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

    public static TextBranch generateQuestTree(Object prompt, Object accept, String acceptButton, Object deny, String denyButton,
                                               Object turnIn, Object completion, Object failure, String promptButton,
                                               ConditionalInterface promptCondition, String turnInButton,
                                               ConditionalInterface turnInCondition, Quest quest, ChoiceBranch main){
        TextBranch promptBranch = new TextBranch(prompt, true);
        TextBranch acceptBranch = new TextBranch(accept, true);
        TextBranch denyBranch = new TextBranch(deny, true);
        TextBranch turnInBranch = new TextBranch(turnIn, true);
        TextBranch completionBranch = new TextBranch(completion, true);
        TextBranch failureBranch = new TextBranch(failure, true);

        QuestBranch questAccept = new QuestBranch(quest);
        TurnInBranch questTurnIn = new TurnInBranch(quest);
        RewardBranch questReward = new RewardBranch(quest);

        NextBranch questRewardNext = new NextBranch(questReward);
        NextBranch questTurnInNext = new NextBranch(questTurnIn);

        ChoiceBranch questChoices = new ChoiceBranch();
        questChoices.append(questAccept, acceptButton).append(denyBranch, denyButton);
        questChoices.build();

        questAccept.append(acceptBranch);
        acceptBranch.append(main);
        denyBranch.append(main);

        promptBranch.append(questChoices);

        turnInBranch.append(0, questTurnInNext);
        questTurnIn.append(completionBranch).append(1, failureBranch);

        completionBranch.append(questRewardNext);
        questReward.append(main);
        failureBranch.append(main);

        if(promptCondition == null)
            promptCondition = (Player player) -> true;

        main.append(promptBranch, promptButton, promptCondition).append(turnInBranch, turnInButton, turnInCondition);

        return promptBranch;
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
