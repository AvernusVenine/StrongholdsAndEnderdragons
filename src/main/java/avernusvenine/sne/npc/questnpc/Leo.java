package avernusvenine.sne.npc.questnpc;

import avernusvenine.sne.npc.dialogue.DialogueSet;
import avernusvenine.sne.npc.dialogue.DialogueSet.DialogueType;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.quests.ItemRetrievalQuest;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Leo extends SneNPC {

    public Leo(){
        id = "leo";
        name = "Leo";
        id_value = 0;

        dialogueSet = new DialogueSet(id, DialogueType.QUEST);

        createNPC();

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
        dialogueSet.addGreeting(-1, 10, greeting);
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
            dialogueSet.addQuest(quest);

            String[] text;

            text = new String[]{
                    "Mrrrow?",
                    "1x Cod",
                    "",
                    ""
            };
            dialogueSet.addQuestPrompt(quest, text);

            text = new String[]{
                    "Maow mowww!",
                    "",
                    "",
                    ""
            };
            dialogueSet.addQuestAccept(quest, text);

            text = new String[]{
                    "Mow.",
                    "",
                    "",
                    ""
            };
            dialogueSet.addQuestDeny(quest, text);

            text = new String[]{
                    "Rrrrrow rrow rrow!!",
                    "",
                    "",
                    ""
            };
            dialogueSet.addQuestCompletion(quest, text);

            text = new String[]{
                    "Maow mow.",
                    "Mroww roww row.",
                    "",
                    ""
            };
            dialogueSet.addQuestCompletion(quest, text);
        }
    }
}
