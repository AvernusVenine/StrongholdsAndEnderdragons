package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Paladin extends DefaultClass{

    public Paladin(){
        type = ClassType.PALADIN;
        id = "paladin";
        chatPrefix = ChatColor.GOLD + "" + ChatColor.BOLD +  "[PALADIN]";
    }

}
