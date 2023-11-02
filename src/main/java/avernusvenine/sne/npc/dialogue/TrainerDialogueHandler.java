package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.professions.DefaultProfession;
import avernusvenine.sne.professions.DefaultProfession.ProfessionType;
import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

import java.util.List;

public class TrainerDialogueHandler extends DialogueHandler{

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

                if(playerCharacter.getProfessionLevel(ProfessionType.FISHING) == 0)
                    phase = Phase.TRAINER_INITIAL;


                advance(player);
                return;
            case CLOSE:
                close(player);
                return;
            case TRAINER_INITIAL:
                break;
        }

        displayToPlayer(player, dialogue);
    }

}
