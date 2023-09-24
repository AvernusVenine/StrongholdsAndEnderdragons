package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Rogue extends DefaultClass{

    public Rogue(){
        type = ClassType.ROGUE;
        id = "rogue";
        chatPrefix = ChatColor.DARK_GRAY + "" + ChatColor.BOLD +  "[ROGUE]";
    }

}
