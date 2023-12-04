package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.players.PlayerCharacter.QuestStatus.Status;
import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

public class QuestBranch extends Branch{

    private final Quest quest;

    public QuestBranch(Quest quest){
        this.quest = quest;
    }

    @Override
    public DialogueTask run(Player player) {
        PlayerDictionary.get(player).getPlayerCharacter().addQuest(quest.getID(), Status.ACCEPTED, 0);
        return null;
    }
}
