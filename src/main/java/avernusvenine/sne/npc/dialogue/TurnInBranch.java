package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.Globals;
import avernusvenine.sne.gui.quest.ItemRetrievalCompletionGUI;
import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

public class TurnInBranch extends Branch{

    private Quest quest;

    public TurnInBranch(Quest quest){
        this.quest = quest;
    }

    @Override
    public DialogueTask run(Player player) {

        if(quest instanceof ItemRetrievalQuest retrievalQuest)
            Globals.openGUI(player, new ItemRetrievalCompletionGUI(player, retrievalQuest));

        return null;
    }
}
