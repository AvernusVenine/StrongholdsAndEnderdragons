package avernusvenine.sne.npc.questnpc;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.dialogue.DialogueSet;
import avernusvenine.sne.npc.dialogue.DialogueSet.DialogueType;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.dialogue.QuestDialogueSet;
import avernusvenine.sne.npc.traits.DialogueTrait;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.quests.ItemRetrievalQuest;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Leo extends SneNPC {

    public Leo(){
        id = "leo";
        name = "Leo";

        QuestDialogueSet questSet = new QuestDialogueSet(id);
        dialogueSet.put(DialogueType.QUEST, questSet);

        createNPC();

        registerQuests();
        registerDialogue();
    }

    @Override
    public DialogueSet getDialogueSet(Player player){
        PlayerCharacter playerCharacter = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter();

        return dialogueSet.get(DialogueType.QUEST);
    }


    @Override
    public void createNPC(){
        npc = registry.createNPC(EntityType.CAT, name);
        npc.addTrait(new DialogueTrait());
    }

    protected void registerDialogue(){
        String[] greeting = new String[]{
                "Mow mow!",
                "",
                "",
                ""
        };
        dialogueSet.get(DialogueType.QUEST).addGreeting(-1, 10, greeting);
    }

    protected void registerQuests(){
        QuestDialogueSet questDialogueSet = (QuestDialogueSet) dialogueSet.get(DialogueType.QUEST);

        // LEOS REQUEST
        {
            List<ItemStack> questItems = new ArrayList<>();
            questItems.add(new ItemStack(Material.COD, 1));

            List<ItemStack> rewardItems = new ArrayList<>();
            rewardItems.add(new ItemStack(Material.STRING, 16));

            ItemRetrievalQuest quest = new ItemRetrievalQuest("leos_request", questItems, rewardItems);
            quests.add(quest);
            questDialogueSet.addQuest(quest);

            String[] text;

            text = new String[]{
                    "Mrrrow?",
                    "1x Cod",
                    "",
                    ""
            };
            questDialogueSet.addQuestPrompt(quest, text);

            text = new String[]{
                    "Maow mowww!",
                    "",
                    "",
                    ""
            };
            questDialogueSet.addQuestAccept(quest, text);

            text = new String[]{
                    "Mow.",
                    "",
                    "",
                    ""
            };
            questDialogueSet.addQuestDeny(quest, text);

            text = new String[]{
                    "Rrrrrow rrow rrow!!",
                    "",
                    "",
                    ""
            };
            questDialogueSet.addQuestCompletion(quest, text);

            text = new String[]{
                    "Maow mow.",
                    "Mroww roww row.",
                    "",
                    ""
            };
            questDialogueSet.addQuestCompletion(quest, text);
        }
    }
}
