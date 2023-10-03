package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class HalfElf extends Race {

    public HalfElf(){
        type = RaceType.HALF_ELF;
        id = "half_elf";
        chatPrefix = ChatColor.DARK_AQUA + "" + ChatColor.BOLD +  "[HALF ELF]";
    }

}
