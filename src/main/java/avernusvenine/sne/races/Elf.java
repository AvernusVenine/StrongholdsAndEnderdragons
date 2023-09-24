package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Elf extends DefaultRace{

    public Elf(){
        type = RaceType.ELF;
        id = "elf";
        chatPrefix = ChatColor.AQUA + "" + ChatColor.BOLD +  "[ELF]";
    }

}
