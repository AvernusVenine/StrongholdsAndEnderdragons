package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Cleric extends DefaultClass {

    public Cleric(){
        type = ClassType.CLERIC;
        id = "cleric";
        chatPrefix = ChatColor.WHITE + "" + ChatColor.BOLD +  "[CLERIC]";
    }

}
