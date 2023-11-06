package avernusvenine.sne.npc.traits;

import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.players.PlayerProfile;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DialogueTrait extends Trait {

    protected static String title = "dialogue_trait";

    protected List<Boolean> npcType = new ArrayList<>();

    public DialogueTrait(){
        super(title);
    }

    @EventHandler
    public void onRightClick(net.citizensnpcs.api.event.NPCRightClickEvent event){
        if(event.getNPC() != this.getNPC())
            return;

        PlayerProfile profile = PlayerDictionary.get(event.getClicker().getUniqueId().toString());

        if(!profile.isInDialogue())
            profile.openDialogue(NPCDictionary.getByUUID(event.getNPC().getUniqueId().toString()).getDialogueSet(event.getClicker()));

        profile.advanceDialogue();
    }

}
