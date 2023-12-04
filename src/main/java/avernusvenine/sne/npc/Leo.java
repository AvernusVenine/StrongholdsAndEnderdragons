package avernusvenine.sne.npc;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.dialogue.*;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.traits.DialogueTrait;

import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;
import avernusvenine.sne.races.Race.RaceType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Leo extends SneNPC {

    private enum Quests{
        LEOS_REQUEST(new ItemRetrievalQuest("leos_request",
                new ArrayList<>(){{

                }},
                new ArrayList<>(){{

                }}));

        private final Quest quest;

        Quests(Quest quest){
            this.quest = quest;
        }

        public Quest getQuest(){
            return quest;
        }
    }

    public Leo(){
        id = "leo";
        name = "Leo";

        createNPC();

        registerQuests();
        registerDialogue();

        NPCDictionary.put(id, this);
    }

    @Override
    public Branch getBranch(Player player) {
        if(PlayerDictionary.get(player).getPlayerCharacter().getRaceType() == RaceType.FELIDAE){
            return branches.get("greeting").append(0, branches.get("fluent_greeting_choices"));
        }
        else {
            return branches.get("greeting").append(0, branches.get("not_fluent_choices"));
        }
    }


    @Override
    public void createNPC(){
        npc = registry.createNPC(EntityType.CAT, name);
        npc.addTrait(new DialogueTrait());
    }

    protected void registerDialogue(){

        // Not fluent dialogue

        branches.put("greeting", new TextBranch("Mrrrrello", true));
        branches.put("not_fluent_response", new TextBranch(new String[]{"Mra mrow mrow mrow mrout rorow...", "*sigh*"}));

        branches.put("exit", new ExitBranch());

        // Fluent dialogue

        branches.put("response", new TextBranch("Finally, a biped who can understand me!", true));
        branches.put("self_description_response", new TextBranch("My names Leo; my owners this towns artificer, Avernus", true));

        // Leos Request

        branches.put("leos_request_prompt", new TextBranch(new String[]{"As a matter of fact I am", "I've been looking for some special fish, but I can't seem to get my paws on any"}, true));
        branches.put("leos_request_accept_response", new TextBranch("Finally! Maybe I can have a good meal for once"));
        branches.put("leos_request_deny_response", new TextBranch(new String[]{"*sigh*", "I'll be stuck with rats and dry food for my entire life"}));
        branches.put("leos_request_accept", new QuestBranch(quests.get("leos_request")));

        ChoiceBranch fluentChoices = new ChoiceBranch();
        fluentChoices.append(0, branches.get("no_quests_response"), "Need help with anything?");
        fluentChoices.append(1, branches.get("self_description_response"), "Who are you?");
        fluentChoices.build();
        branches.put("fluent_choices", fluentChoices);

        // Tree assembly

        ChoiceBranch greetingChoices = new ChoiceBranch();
        greetingChoices.append(0, branches.get("not_fluent_response"), "Hi there little guy");
        greetingChoices.append(1, branches.get("response"), "[ANIMAL SPEAKING] Hello to you too, little man!",
                (Player player) -> PlayerDictionary.get(player).getPlayerCharacter().getRaceType() == RaceType.FELIDAE);
        greetingChoices.build();
        branches.put("greeting_choices", greetingChoices);

        ChoiceBranch mainChoices = new ChoiceBranch();
        mainChoices.append(0, branches.get("self_description_response"), "Who are you?");
        mainChoices.append(1, branches.get("leos_request_prompt"), "[QUEST] You're looking a little hungry",
                (Player player) -> true);
        mainChoices.build();
        branches.put("main_choices", mainChoices);

        ChoiceBranch leosRequest = new ChoiceBranch();
        leosRequest.append(0, branches.get("leos_request_accept"), "[ACCEPT] I'll see what I can fish up");
        leosRequest.append(1, branches.get("leos_request_deny_response"), "[DENY] Sorry, I'm a little busy right now");
        leosRequest.build();
        branches.put("leos_request", leosRequest);
    }

    protected boolean checkQuestAvailability(Player player, Quest quest){
        return false;
    }

    protected void registerQuests(){

        List<ItemStack> items = new ArrayList<>();
        items.add(ItemDictionary.get("common_greentop").getItem());
        List<ItemStack> rewards = new ArrayList<>();
        items.add(new ItemStack(Material.STRING, 32));
        Quest leosRequest = new ItemRetrievalQuest("leos_request", items, rewards);
        quests.put("leos_request", leosRequest);

    }
}
