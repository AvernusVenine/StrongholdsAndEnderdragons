package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Wizard extends DefaultClass{

    public Wizard(){
        type = ClassType.WIZARD;
        id = "wizard";
        chatPrefix = ChatColor.BLUE + "" + ChatColor.BOLD +  "[WIZARD]";
    }

}
