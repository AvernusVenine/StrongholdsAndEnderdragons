package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Warlock extends DefaultClass{

    public Warlock(){
        type = ClassType.WARLOCK;
        id = "warlock";
        chatPrefix = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +  "[WARLOCK]";
    }

}
