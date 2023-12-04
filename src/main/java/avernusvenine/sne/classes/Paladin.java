package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Paladin extends DefaultClass{

    private static final int INITIAL_DIVINITY = 100;

    public Paladin(){
        type = ClassType.PALADIN;
        id = "paladin";
        chatPrefix = ChatColor.GOLD + "" + ChatColor.BOLD +  "[PALADIN]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_DIVINITY;
    }

}
