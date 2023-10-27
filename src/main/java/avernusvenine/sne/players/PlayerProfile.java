package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.DialogueHandler;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.quests.Quest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerProfile {

    protected PlayerCharacter playerCharacter;
    protected Player player;

    protected boolean inDialogue = false;
    protected ActionBarTask actionBarTask;
    protected DialogueHandler dialogueHandler;

    public PlayerProfile(Player player){
        this.player = player;

        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void onPlayerQuit(){
        actionBarTask.cancelTask();
    }

    public void onDialogueStart(DialogueHandler handler){
        dialogueHandler = handler;
        inDialogue = true;
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

    public void removeDialogueHandler(){
        dialogueHandler = null;
    }

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
    }

    public String getUUID(){
        return player.getUniqueId().toString();
    }

    public void setInDialogue(boolean inDialogue){
        if(inDialogue)
            actionBarTask.changeToDialogueBox();
        else
            actionBarTask.changeToOverlay();

        this.inDialogue = inDialogue;
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
            }, 0, 40);

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
