package avernusvenine.sne.classes;

import org.bukkit.ChatColor;

public class Cleric extends DefaultClass {

    private static final int INITIAL_DIVINITY = 100;

    public Cleric(){
        type = ClassType.CLERIC;
        id = "cleric";
        chatPrefix = ChatColor.WHITE + "" + ChatColor.BOLD +  "[CLERIC]";
    }

    @Override
    public int getMaxResource(int level){
        return INITIAL_DIVINITY;
    }

}
