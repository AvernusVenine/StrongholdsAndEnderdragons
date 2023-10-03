package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Dwarf extends Race {

    public Dwarf(){
        type = RaceType.DWARF;
        id = "dwarf";
        chatPrefix = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[DWARF]";
    }

}
