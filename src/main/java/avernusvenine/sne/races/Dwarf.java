package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Dwarf extends DefaultRace{

    public Dwarf(){
        type = RaceType.DWARF;
        id = "dwarf";
        chatPrefix = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[DWARF]";
    }

}
