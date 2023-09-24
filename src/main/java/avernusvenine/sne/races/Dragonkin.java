package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Dragonkin extends DefaultRace{

    public Dragonkin(){
        type = RaceType.DRAGON_KIN;
        id = "dragon_kin";
        chatPrefix = ChatColor.DARK_RED  + "" + ChatColor.BOLD + "[DRAGONKIN]";
    }

}
