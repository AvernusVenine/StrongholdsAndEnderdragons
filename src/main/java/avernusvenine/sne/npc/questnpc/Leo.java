package avernusvenine.sne.npc.questnpc;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.DialogueHandler;
import avernusvenine.sne.npc.DialogueSet;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.traits.DialogueTrait;
import avernusvenine.sne.npc.traits.QuestTrait;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.players.PlayerProfile;
import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;

import net.citizensnpcs.api.CitizensAPI;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Leo extends SneNPC {

    public Leo(){
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.CAT, "Leo");
        id = "leo";

        dialogueHandler = new DialogueHandler(id);

        npc.addTrait(new DialogueTrait());
        registerQuests();
        registerDialogue();
    }

    protected void registerDialogue(){
        String[] greeting = new String[]{
                "Mow mow!",
                "",
                "",
                ""
        };
        dialogueHandler.addGreeting(-1, 10, greeting);
    }

    protected void registerQuests(){
        // LEOS REQUEST
        {
            List<ItemStack> questItems = new ArrayList<>();
            questItems.add(new ItemStack(Material.COD, 1));

            List<ItemStack> rewardItems = new ArrayList<>();
            rewardItems.add(new ItemStack(Material.STRING, 16));

            ItemRetrievalQuest quest = new ItemRetrievalQuest("leos_request", questItems, rewardItems);
            quests.add(quest);
            dialogueHandler.addQuest(quest);

            String[] text;

            text = new String[]{
                    "Mrrrow?",
                    "",
                    "1x Cod",
                    ""
            };
            dialogueHandler.addQuestPrompt(quest, text);

            text = new String[]{
                    "Maow mowww!",
                    "",
                    "",
                    ""
            };
            dialogueHandler.addQuestAccept(quest, text);

            text = new String[]{
                    "Mow.",
                    "",
                    "",
                    ""
            };
            dialogueHandler.addQuestDeny(quest, text);

            text = new String[]{
                    "Rrrrrow rrow rrow!!",
                    "",
                    "",
                    ""
            };
            dialogueHandler.addQuestCompletion(quest, text);

            text = new String[]{
                    "Maow mow.",
                    "Mroww roww row.",
                    "",
                    ""
            };
            dialogueHandler.addQuestCompletion(quest, text);
        }
    }
}
