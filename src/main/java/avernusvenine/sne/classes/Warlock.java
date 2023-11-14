package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Warlock extends DefaultClass{

    public static final int INITIAL_MANA = 50;

    public Warlock(){
        type = ClassType.WARLOCK;
        id = "warlock";
        chatPrefix = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +  "[WARLOCK]";
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_MANA;
    }

}
