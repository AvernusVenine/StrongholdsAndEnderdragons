package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Gnome extends Race {

    public Gnome(){
        type = RaceType.GNOME;
        id = "gnome";
        chatPrefix = ChatColor.YELLOW + "" + ChatColor.BOLD +  "[GNOME]";
    }

}
