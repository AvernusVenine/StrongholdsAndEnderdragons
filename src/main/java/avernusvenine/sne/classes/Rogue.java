package avernusvenine.sne.classes;

import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.ChatColor;

public class Rogue extends DefaultClass{

    private static final int INITIAL_CLOAK = 100;

    public Rogue(){
        type = ClassType.ROGUE;
        id = "rogue";
        chatPrefix = ChatColor.DARK_GRAY + "" + ChatColor.BOLD +  "[ROGUE]";
    }

    @Override
    public void onLevelUp(PlayerCharacter character, int level) {
        switch (level){
            case 1:
                character.addSpell("eviscerate");
                break;
        }
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_CLOAK;
    }
}
