package avernusvenine.sne.npc.traits;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.DialogueSet;
import avernusvenine.sne.quests.Quest;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class QuestTrait extends Trait {

    protected static String title = "quest_trait";

    protected DialogueSet dialogue;
    protected Quest quest;

    public QuestTrait(DialogueSet dialogue, Quest quest){
        super(title);
        this.dialogue = dialogue;
        this.quest = quest;
    }

    public void displayText(Player player, DialogueSet.DialogueType type){
        player.sendTitle("\ue239", "", 0, 999999, 0);
    }

    @EventHandler
    public void onRightClick(net.citizensnpcs.api.event.NPCRightClickEvent event){
        if(event.getNPC() != this.getNPC())
            return;

        Player player = event.getClicker();

        if(PlayerDictionary.get(player.getUniqueId().toString()).isInDialogue()){
            player.clearTitle();
            PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(false);
            return;
        }

        displayText(player, DialogueSet.DialogueType.GREETING);
        PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(true);
    }

    @EventHandler
    public void onLeftClick(net.citizensnpcs.api.event.NPCLeftClickEvent event){
        if(event.getNPC() != this.npc)
            return;

        Player player = event.getClicker();

        if(PlayerDictionary.get(player.getUniqueId().toString()).isInDialogue()){
            player.clearTitle();
            PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(false);
        }
    }

}
