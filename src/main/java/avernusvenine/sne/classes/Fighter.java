package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Fighter extends DefaultClass {

    public Fighter(){
        type = ClassType.FIGHTER;
        id = "fighter";
        chatPrefix = ChatColor.GRAY + "" + ChatColor.BOLD +  "[FIGHTER]";
    }

}
