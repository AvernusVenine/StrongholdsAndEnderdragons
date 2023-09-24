package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Human extends DefaultRace{

    public Human(){
        type = RaceType.HUMAN;
        id = "human";
        chatPrefix = ChatColor.WHITE + "" + ChatColor.BOLD +  "[HUMAN]";
    }

}
