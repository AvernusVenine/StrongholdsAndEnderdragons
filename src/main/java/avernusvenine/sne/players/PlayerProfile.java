package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.dialogue.DialogueHandler;
import avernusvenine.sne.npc.dialogue.DialogueSet;
import avernusvenine.sne.npc.dialogue.DialogueSet.DialogueType;

import avernusvenine.sne.npc.dialogue.QuestDialogueHandler;
import avernusvenine.sne.npc.dialogue.TrainerDialogueHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PlayerProfile {

    protected PlayerCharacter playerCharacter;
    protected Player player;

    protected boolean inDialogue = false;
    protected ActionBarTask actionBarTask;
    protected DialogueHandler dialogueHandler;

    public PlayerProfile(Player player){
        this.player = player;

        dialogueHandler = new DialogueHandler();

        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void onPlayerQuit(){
        actionBarTask.cancelTask();
        dialogueHandler.reset();
    }

    public void openDialogue(DialogueSet set){

        switch(set.getType()){
            case QUEST:
                dialogueHandler = new QuestDialogueHandler();
                break;
            case TRAINER:
                dialogueHandler = new TrainerDialogueHandler();
                break;
            case DEFAULT:
                dialogueHandler = new DialogueHandler();
                break;
        }

        dialogueHandler.setDialogueSet(set);
        inDialogue = true;
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToDialogueBox();
    }

    public void closeDialogue(){
        inDialogue = false;
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
        dialogueHandler.reset();
    }

    public void closeQuestCompletion(){
        dialogueHandler.advance(player, DialogueHandler.Phase.CLOSE);
        closeDialogue();
    }

    public void advanceDialogue(){
        dialogueHandler.advance(player);
    }

    public void onQuestAccept(){
        dialogueHandler.advance(player, DialogueHandler.Phase.QUEST_ACCEPT);
        playerCharacter.updateQuestStatus(dialogueHandler.getCurrentQuestID(), PlayerCharacter.QuestStatus.Status.ACCEPTED);
    }

    public void onQuestDeny(){
        dialogueHandler.advance(player, DialogueHandler.Phase.QUEST_DENY);
    }

    public void onQuestCompletion(){
        dialogueHandler.advance(player, DialogueHandler.Phase.QUEST_COMPLETION);
    }

    // Getters and Setters

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
    }

    public String getUUID(){
        return player.getUniqueId().toString();
    }

    public boolean isInDialogue(){
        return inDialogue;
    }

    public class ActionBarTask{

        private final BukkitTask task;

        private Component currentActionBar;

        public ActionBarTask(Player player){

            currentActionBar = Component.empty();

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendActionBar(currentActionBar);
                }
            }, 1, 40);

        }

        public void changeToDialogueBox(){
            currentActionBar = Component.text('\ue239');
        }

        public void changeToEmpty(){
            currentActionBar = Component.empty();
        }

        public void changeToOverlay(){
            currentActionBar = Component.empty();
        }

        public void cancelTask(){
            task.cancel();
        }

    }

}
