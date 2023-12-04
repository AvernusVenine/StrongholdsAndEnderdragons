package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Artificer extends DefaultClass{

    private static final int INITIAL_FLUX = 50;
    private static final int FLUX_PER_LEVEL = 10;

    public Artificer(){
        type = ClassType.ARTIFICER;
        id = "artificer";
        chatPrefix = ChatColor.DARK_AQUA + "" + ChatColor.BOLD +  "[ARTIFICER]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_FLUX + FLUX_PER_LEVEL * level;
    }

}
