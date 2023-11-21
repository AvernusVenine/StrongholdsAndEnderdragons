package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Wizard extends DefaultClass{

    private static final int[] MAX_MANA = new int[]{
            0, // Placeholder 0
            50, 50, 50, 50, 50 // 1-5
    };


    public Wizard(){
        type = ClassType.WIZARD;
        id = "wizard";
        chatPrefix = ChatColor.BLUE + "" + ChatColor.BOLD +  "[WIZARD]";
    }

    @Override
    public int getMaxResource(int level){
        return MAX_MANA[level];
    }
}
