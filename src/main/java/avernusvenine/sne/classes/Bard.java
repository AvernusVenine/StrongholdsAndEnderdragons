package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Bard extends DefaultClass {

    public Bard(){
        type = ClassType.BARD;
        id = "bard";
        chatPrefix = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "[BARD]";
    }

}
