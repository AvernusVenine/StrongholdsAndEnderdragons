package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Bard extends DefaultClass {

    private static final int INITIAL_MANA = 50;
    private static final int MANA_PER_LEVEL = 0;

    public Bard(){
        type = ClassType.BARD;
        id = "bard";
        chatPrefix = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "[BARD]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {

    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_MANA + MANA_PER_LEVEL * level;
    }

}
