package avernusvenine.sne.players;

import avernusvenine.sne.quests.Quest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerProfile {

    protected PlayerCharacter playerCharacter;
    protected Player player;

    public PlayerProfile(Player player){
        this.player = player;
    }


    //Getters and setters

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter){this.playerCharacter = playerCharacter;}

    public String getUUID(){return player.getUniqueId().toString();}

}
