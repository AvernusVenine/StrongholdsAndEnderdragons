package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Ranger extends DefaultClass{

    private static final int INITIAL_ENERGY = 100;

    public Ranger(){
        type = ClassType.RANGER;
        id = "ranger";
        chatPrefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD +  "[RANGER]";
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_ENERGY;
    }

}
