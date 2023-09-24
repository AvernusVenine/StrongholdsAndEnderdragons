package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Monk extends DefaultClass{

    public Monk(){
        type = ClassType.MONK;
        id = "monk";
        chatPrefix = ChatColor.AQUA + "" + ChatColor.BOLD +  "[MONK]";
    }

}
