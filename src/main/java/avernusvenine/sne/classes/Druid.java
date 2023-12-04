package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Druid extends DefaultClass{

    public static final int INITIAL_MANA = 50;

    public Druid(){
        type = ClassType.DRUID;
        id = "druid";
        chatPrefix = ChatColor.GREEN + "" + ChatColor.BOLD +  "[DRUID]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_MANA;
    }

}
