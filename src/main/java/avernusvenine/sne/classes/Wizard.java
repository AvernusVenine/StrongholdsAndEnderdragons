package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Wizard extends DefaultClass{

    private static final int[] MAX_MANA = new int[]{
            0, // Placeholder 0
            50, 50, 50, 50, 50 // 1-5
    };


    public Wizard(){
        type = ClassType.WIZARD;
        id = "wizard";
        chatPrefix = ChatColor.BLUE + "" + ChatColor.BOLD +  "[WIZARD]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return MAX_MANA[level];
    }
}
