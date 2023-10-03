package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Orc extends Race {

    public Orc(){
        type = RaceType.ORC;
        id = "orc";
        chatPrefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD +  "[ORC]";
    }

}
