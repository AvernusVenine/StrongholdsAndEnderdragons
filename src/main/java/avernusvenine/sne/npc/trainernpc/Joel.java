package avernusvenine.sne.npc.trainernpc;

import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.npc.dialogue.DialogueSet;
import avernusvenine.sne.npc.dialogue.DialogueSet.DialogueType;
import avernusvenine.sne.npc.dialogue.TrainerDialogueSet;
import avernusvenine.sne.npc.traits.DialogueTrait;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.professions.Profession.ProfessionType;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.trait.HologramTrait;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Joel extends SneNPC {

    public Joel(){
        id = "joel";
        name = "Joel";

        TrainerDialogueSet trainerSet = new TrainerDialogueSet(id);
        trainerSet.setProfessionType(ProfessionType.FISHING);
        dialogueSet.put(DialogueType.TRAINER, trainerSet);

        createNPC();

        registerDialogue();

        NPCDictionary.put(id, this);
    }

    @Override
    public void createNPC(){
        npc = registry.createNPC(EntityType.PLAYER, name);
        npc.addTrait(new DialogueTrait());
    }

    @Override
    public DialogueSet getDialogueSet(Player player){
        PlayerCharacter playerCharacter = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter();

        if(playerCharacter.getProfessionLevel(ProfessionType.FISHING) == 0)
            return dialogueSet.get(DialogueType.TRAINER);

        return dialogueSet.get(DialogueType.SHOP);
    }

    protected void registerDialogue(){
        TrainerDialogueSet set = (TrainerDialogueSet) dialogueSet.get(DialogueType.TRAINER);

        String[] text = new String[]{
                "Morning angler!",
                "Any fishy tales to share?",
                "",
                ""
        };
        set.addGreeting(-1, 10, text);

        text = new String[]{
                "It seems you are new to the sport!",
                "Want to learn how you can get started?",
                "",
                ""
        };
        set.addLevelZero(text);

        text = new String[]{
                "Great!!",
                "If you ever need more training speak to me or",
                "another fishing trainer",
                ""
        };
        set.addProfessionAccept(text);

        text = new String[]{
                "I'll be here if you change your mind",
                "",
                "",
                ""
        };
        set.addProfessionDeny(text);

        text = new String[]{
                "Seems your full up on professions!",
                "Be sure to come back after getting rid of one you",
                "have right now",
                ""
        };
        set.addProfessionsFull(text);
    }
}
