package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Tiefling extends Race {

    public Tiefling(){
        type = RaceType.TIEFLING;
        id = "tiefling";
        chatPrefix = ChatColor.RED + "" + ChatColor.BOLD +  "[TIEFLING]";
    }

}
