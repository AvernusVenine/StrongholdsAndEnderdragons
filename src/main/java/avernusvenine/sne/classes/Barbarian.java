package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Barbarian extends DefaultClass {

    private static final int INITIAL_RAGE = 100;

    public Barbarian(){
        type = ClassType.BARBARIAN;
        id = "barbarian";
        chatPrefix = ChatColor.DARK_RED + "" + ChatColor.BOLD +  "[BARBARIAN]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_RAGE;
    }

}
