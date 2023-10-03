package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Dragonkin extends Race {

    public Dragonkin(){
        type = RaceType.DRAGON_KIN;
        id = "dragon_kin";
        chatPrefix = ChatColor.DARK_RED  + "" + ChatColor.BOLD + "[DRAGONKIN]";
    }

}
