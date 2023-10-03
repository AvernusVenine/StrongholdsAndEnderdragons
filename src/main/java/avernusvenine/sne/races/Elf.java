package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Elf extends Race {

    public Elf(){
        type = RaceType.ELF;
        id = "elf";
        chatPrefix = ChatColor.AQUA + "" + ChatColor.BOLD +  "[ELF]";
    }

}
