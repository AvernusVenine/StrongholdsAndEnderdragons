package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Sorcerer extends DefaultClass{

    public Sorcerer(){
        type = ClassType.SORCERER;
        id = "sorcerer";
        chatPrefix = ChatColor.DARK_BLUE + "" + ChatColor.BOLD +  "[SORCERER]";
    }

}
