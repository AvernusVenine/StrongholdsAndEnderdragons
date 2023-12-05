package avernusvenine.sne.npc;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.dialogue.*;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.traits.DialogueTrait;

import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.players.PlayerCharacter.QuestStatus.Status;
import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;
import avernusvenine.sne.races.Race.RaceType;
import avernusvenine.sne.status.AnimalSpeaking;
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
                    new ItemStack(Material.COD, 1);
                }},
                new ArrayList<>(){{
                    new ItemStack(Material.STRING, 32);
                }}));

        private final Quest quest;

        Quests(Quest quest){
            this.quest = quest;
        }

        public Quest getQuest(){
            return quest;
        }
        public String getID(){
            return quest.getID();
        }
    }

    public Leo(){
        id = "leo";
        name = "Leo";

        createNPC();
        registerDialogue();

        NPCDictionary.put(id, this);
    }

    @Override
    public Branch getBranch(Player player) {
        return branches.get("greeting_one");
    }


    @Override
    public void createNPC(){
        npc = registry.createNPC(EntityType.CAT, name);
        npc.addTrait(new DialogueTrait());
    }

    protected void registerDialogue(){
        // Main choice branch, used to circle back too
        ChoiceBranch mainChoices = new ChoiceBranch();

        // Greetings
        TextBranch greetingOne = new TextBranch("Mrrrrello", true);
        {
            TextBranch notFluentResponse = new TextBranch(new String[]{"Mra mrow mrow mrow mrout rorow...", "*sigh*"});
            TextBranch greetingResponse = new TextBranch("Finally, a biped who can understand me!", true);

            ChoiceBranch greetingChoices = new ChoiceBranch();
            greetingChoices.append(0, notFluentResponse, "Hi there little guy");
            greetingChoices.append(1, greetingResponse, "[FELIDAE] Greetings, little kin",
                    (Player player) -> PlayerDictionary.get(player).getPlayerCharacter().getRaceType() == RaceType.FELIDAE);
            greetingChoices.append(2, greetingResponse, "[ANIMAL SPEAKING] Hello to you too, little man!",
                    (Player player) -> PlayerDictionary.get(player).hasStatusEffect(AnimalSpeaking.class) &&
                            PlayerDictionary.get(player).getPlayerCharacter().getRaceType() != RaceType.FELIDAE);
            greetingChoices.build();

            greetingOne.append(0, greetingChoices);
            greetingResponse.append(0, mainChoices);
        }
        branches.put("greeting_one", greetingOne);

        // Leos Request
        generateQuestTree(
                new String[]{"As a matter of fact I am", "I've been looking for some special fish", "but I can't seem to get my paws on any"},
                "Finally! Maybe I can have a good meal for once",
                "[ACCEPT] I'll see what I can fish up",
                new String[]{"*sigh*", "I'll be stuck with rats and dry food for my entire life"},
                "[DENY] Sorry, I'm a little busy right now",
                "Oh finally! Bring it here",
                "Perfect! Here's a small something as a thank you",
                new String[]{"So you don't have my fish?", "..."},
                "[QUEST] You're looking a little hungry",
                null,
                "[QUEST] I've got your fish",
                (Player player) -> PlayerDictionary.get(player).getPlayerCharacter().getQuestStatus(Quests.LEOS_REQUEST.getID()) == Status.ACCEPTED,
                Quests.LEOS_REQUEST.getQuest(),
                mainChoices
        );

        mainChoices.build();
    }
}
