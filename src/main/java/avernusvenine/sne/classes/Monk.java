package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Monk extends DefaultClass{

    private static final int INITIAL_QI = 100;

    public Monk(){
        type = ClassType.MONK;
        id = "monk";
        chatPrefix = ChatColor.AQUA + "" + ChatColor.BOLD +  "[MONK]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_QI;
    }

}
