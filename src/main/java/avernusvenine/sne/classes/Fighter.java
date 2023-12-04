package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Fighter extends DefaultClass {

    private static final int INITIAL_ENERGY = 100;

    public Fighter(){
        type = ClassType.FIGHTER;
        id = "fighter";
        chatPrefix = ChatColor.GRAY + "" + ChatColor.BOLD +  "[FIGHTER]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_ENERGY;
    }

}
