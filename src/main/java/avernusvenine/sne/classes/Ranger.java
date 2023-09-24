package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Ranger extends DefaultClass{

    public Ranger(){
        type = ClassType.RANGER;
        id = "ranger";
        chatPrefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD +  "[RANGER]";
    }

}
