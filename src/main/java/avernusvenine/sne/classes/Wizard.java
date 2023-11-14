package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Wizard extends DefaultClass{

    public static final int INITIAL_MANA = 50;

    public Wizard(){
        type = ClassType.WIZARD;
        id = "wizard";
        chatPrefix = ChatColor.BLUE + "" + ChatColor.BOLD +  "[WIZARD]";
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_MANA;
    }
}
