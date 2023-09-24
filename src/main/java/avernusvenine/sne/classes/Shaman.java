package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Shaman extends DefaultClass{

    public Shaman(){
        type = ClassType.SHAMAN;
        id = "shaman";
        chatPrefix = ChatColor.RED + "" + ChatColor.BOLD +  "[SHAMAN]";
    }

}
