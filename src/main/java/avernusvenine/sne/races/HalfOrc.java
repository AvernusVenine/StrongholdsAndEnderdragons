package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class HalfOrc extends DefaultRace{

    public HalfOrc(){
        type = RaceType.HALF_ORC;
        id = "half_orc";
        chatPrefix = ChatColor.GREEN + "" + ChatColor.BOLD +  "[HALF ORC]";
    }

}
