package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Ranger extends DefaultClass{

    private static final int INITIAL_ENERGY = 100;

    public Ranger(){
        type = ClassType.RANGER;
        id = "ranger";
        chatPrefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD +  "[RANGER]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_ENERGY;
    }

}
