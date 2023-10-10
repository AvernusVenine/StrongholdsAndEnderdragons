package avernusvenine.sne.npc.questnpc;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.DialogueSet;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.traits.DialogueTrait;
import avernusvenine.sne.npc.traits.QuestTrait;

import avernusvenine.sne.players.PlayerProfile;
import net.citizensnpcs.api.CitizensAPI;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Leo extends SneNPC {

    public Leo(){
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.CAT, "Leo");
        id = "leo";

        npc.addTrait(new DialogueTrait());
    }

    public void advanceDialogue(Player player){

        String dialogue;

        PlayerProfile profile = PlayerDictionary.get(player.getUniqueId().toString());
        double relationship = profile.getPlayerCharacter().getRelationship(id);


        switch(profile.getDialoguePhase()){
            default:
                dialogue = "....";
                break;
            case -1:
                closeDialogue(player);
                return;
            case 0:
                dialogue = "Mrrrrow!";
                profile.setDialoguePhase(-1);
                break;
        }

        showDialogue(player, new String[4]);
    }

}
