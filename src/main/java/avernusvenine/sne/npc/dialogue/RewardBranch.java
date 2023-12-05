package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.Globals;
import avernusvenine.sne.gui.quest.QuestRewardGUI;
import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

public class RewardBranch extends Branch{

    private Quest quest;

    public RewardBranch(Quest quest){
        this.quest = quest;
    }

    @Override
    public DialogueTask run(Player player) {
        Globals.openGUI(player, new QuestRewardGUI(player, quest));
        return null;
    }
}
