package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Cleric extends DefaultClass {

    private static final int INITIAL_DIVINITY = 100;

    public Cleric(){
        type = ClassType.CLERIC;
        id = "cleric";
        chatPrefix = ChatColor.WHITE + "" + ChatColor.BOLD +  "[CLERIC]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_DIVINITY;
    }

}
