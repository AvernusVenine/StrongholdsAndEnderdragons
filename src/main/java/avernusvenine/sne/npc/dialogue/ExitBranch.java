package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.PlayerDictionary;
import org.bukkit.entity.Player;

public class ExitBranch extends Branch{
    @Override
    public DialogueTask run(Player player) {
        PlayerDictionary.get(player).getPlayerDialogueHandler().next(-1);
        return null;
    }
}
