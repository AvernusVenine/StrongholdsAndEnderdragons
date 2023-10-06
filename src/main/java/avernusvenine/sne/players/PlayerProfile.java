package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerProfile {

    protected PlayerCharacter playerCharacter;
    protected Player player;

    protected boolean inDialogue = false;
    protected int dialoguePhase = 0;

    public PlayerProfile(Player player){
        this.player = player;
    }


    //Getters and setters

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter){this.playerCharacter = playerCharacter;}

    public String getUUID(){return player.getUniqueId().toString();}

    public void setInDialogue(boolean inDialogue){
        this.inDialogue = inDialogue;
    }

    public boolean isInDialogue(){
        return inDialogue;
    }

    public void setDialoguePhase(int phase){
        this.dialoguePhase = phase;
    }

    public int getDialoguePhase(){
        return dialoguePhase;
    }

}
