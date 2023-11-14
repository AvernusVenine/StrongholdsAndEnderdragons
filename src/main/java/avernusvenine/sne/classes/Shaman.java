package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Shaman extends DefaultClass{

    private static final int INITIAL_MANA = 50;

    public Shaman(){
        type = ClassType.SHAMAN;
        id = "shaman";
        chatPrefix = ChatColor.RED + "" + ChatColor.BOLD +  "[SHAMAN]";
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_MANA;
    }

}
