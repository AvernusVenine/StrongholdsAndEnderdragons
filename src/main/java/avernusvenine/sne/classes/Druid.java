package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Druid extends DefaultClass{

    public Druid(){
        type = ClassType.DRUID;
        id = "druid";
        chatPrefix = ChatColor.GREEN + "" + ChatColor.BOLD +  "[DRUID]";
    }

}
