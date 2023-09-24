package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Barbarian extends DefaultClass {

    public Barbarian(){
        type = ClassType.BARBARIAN;
        id = "barbarian";
        chatPrefix = ChatColor.DARK_RED + "" + ChatColor.BOLD +  "[BARBARIAN]";
    }

}
