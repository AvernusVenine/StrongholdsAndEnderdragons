package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

import java.util.List;

public class QuestDialogueHandler extends DialogueHandler{

    @Override
    public void advance(Player player){

        String[] dialogue = new String[4];

        PlayerCharacter playerCharacter = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter();

        if(textScrolling){
            skipDialogue(player);
            return;
        }

        switch(phase){
            case GREETING:
                List<String[]> greeting = set.getGreeting(player);

                if(iterator < greeting.size()){
                    dialogue = greeting.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                if(set.getQuests().isEmpty())
                    phase = DialogueHandler.Phase.CLOSE;

                for(Quest quest : set.getQuests()){
                    if(playerCharacter.getQuestStatus(quest.getID()) != PlayerCharacter.QuestStatus.Status.COMPLETED
                            && playerCharacter.checkQuestCompletion(quest.getQuestPrerequisites())){
                        currentQuest = quest;
                        phase = DialogueHandler.Phase.QUEST_PROMPT;
                        break;
                    }
                }

                if(phase == DialogueHandler.Phase.GREETING)
                    phase = DialogueHandler.Phase.CLOSE;

                advance(player);
                return;
            case CLOSE:
                close(player);
                return;
            case QUEST_PROMPT:
                List<String[]> prompt = set.getQuestDialogue(currentQuest).prompt;

                if(iterator < prompt.size()){
                    dialogue = prompt.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                if(playerCharacter.getQuestStatus(currentQuest.getID()) == PlayerCharacter.QuestStatus.Status.ACCEPTED){
                    phase = DialogueHandler.Phase.QUEST_COMPLETION_GUI;
                    advance(player);
                    return;
                }

                phase = DialogueHandler.Phase.QUEST_PROMPT_GUI;
                advance(player);
                return;
            case QUEST_PROMPT_GUI:
                promptQuest(player);
                return;
            case QUEST_ACCEPT:
                List<String[]> accept = set.getQuestDialogue(currentQuest).accept;

                if(iterator < accept.size()){
                    dialogue = accept.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                phase = DialogueHandler.Phase.CLOSE;
                advance(player);
                return;
            case QUEST_DENY:
                List<String[]> deny = set.getQuestDialogue(currentQuest).deny;

                if(iterator < deny.size()){
                    dialogue = deny.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                phase = DialogueHandler.Phase.CLOSE;
                advance(player);
                return;
            case QUEST_COMPLETION:
                List<String[]> completion = set.getQuestDialogue(currentQuest).completion;

                if(iterator < completion.size()){
                    dialogue = completion.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                phase = DialogueHandler.Phase.QUEST_REWARD_GUI;

                playerCharacter.updateQuestStatus(currentQuest.getID(), PlayerCharacter.QuestStatus.Status.COMPLETED);
                advance(player);
                return;
            case QUEST_COMPLETION_GUI:
                promptQuestCompletion(player);
                return;
            case QUEST_REWARD_GUI:
                rewardPlayer(player);
                close(player);
                return;
        }

        displayToPlayer(player, dialogue);
    }

}
