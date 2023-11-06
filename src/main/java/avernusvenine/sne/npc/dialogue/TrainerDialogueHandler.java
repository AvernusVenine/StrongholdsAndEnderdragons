package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.gui.profession.ProfessionPromptGUI;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.professions.Profession.ProfessionType;
import org.bukkit.entity.Player;

import java.util.List;

public class TrainerDialogueHandler extends DialogueHandler{

    @Override
    public void advance(Player player){

        String[] dialogue = new String[4];

        PlayerCharacter playerCharacter = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter();

        TrainerDialogueSet trainerSet = (TrainerDialogueSet) set;

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

                if(playerCharacter.getProfessionsFull()) {
                    phase = Phase.TRAINER_PROFESSIONS_FULL;
                    advance(player);
                    return;
                }

                if(playerCharacter.getProfessionLevel(trainerSet.getProfessionType()) == 0)
                    phase = Phase.TRAINER_INITIAL;

                if(phase == Phase.GREETING)
                    phase = Phase.CLOSE;

                advance(player);
                return;
            case CLOSE:
                close(player);
                return;
            case TRAINER_INITIAL:
                List<String[]> initial = trainerSet.getLevelZero();

                if(iterator < initial.size()){
                    dialogue = initial.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;
                phase = Phase.TRAINER_INITIAL_GUI;

                advance(player);
                return;
            case TRAINER_INITIAL_GUI:
                promptProfession(player);
                return;
            case TRAINER_PROFESSION_ACCEPT:
                List<String[]> accept = trainerSet.getProfessionAccept();

                if(iterator < accept.size()){
                    dialogue = accept.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;
                phase = Phase.CLOSE;

                advance(player);
                return;
            case TRAINER_PROFESSION_DENY:
                List<String[]> deny = trainerSet.getProfessionAccept();

                if(iterator < deny.size()){
                    dialogue = deny.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;
                phase = Phase.CLOSE;

                advance(player);
                return;
            case TRAINER_PROFESSIONS_FULL:

                List<String[]> full = trainerSet.getProfessionsFull();

                if(iterator < full.size()){
                    dialogue = full.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;
                phase = Phase.CLOSE;

                advance(player);
                return;
        }

        displayToPlayer(player, dialogue);
    }

    public void promptProfession(Player player){
        ProfessionPromptGUI gui = new ProfessionPromptGUI();
        player.openInventory(gui.getInventory());
        Globals.registerGUI(gui);
    }

    public ProfessionType getProfessionType(){
        TrainerDialogueSet trainerSet = (TrainerDialogueSet) set;
        return trainerSet.getProfessionType();
    }
}
