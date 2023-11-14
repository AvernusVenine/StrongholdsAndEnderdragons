package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Sorcerer extends DefaultClass{

    public static final int INITIAL_MANA = 50;

    public Sorcerer(){
        type = ClassType.SORCERER;
        id = "sorcerer";
        chatPrefix = ChatColor.DARK_BLUE + "" + ChatColor.BOLD +  "[SORCERER]";
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_MANA;
    }

}
