package avernusvenine.sne.classes;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.races.Race;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class DefaultClass {

    public enum ClassType{
        ARTIFICER(0),
        BARBARIAN(1),
        BARD(2),
        SHAMAN(3),
        CLERIC(4),
        DRUID(5),
        DEFAULT_CLASS(6),
        FIGHTER(7),
        MONK(8),
        PALADIN(9),
        RANGER(10),
        ROGUE(11),
        SORCERER(12),
        WARLOCK(13),
        WIZARD(14);

        private final int id;

        ClassType(int i){
            id = i;
        }

        public int getID(){
            return id;
        }

        public static ClassType fromID(int id){
            for(ClassType type : values()){
                if(type.getID() == id)
                    return type;
            }
            return null;
        }
    }

    protected ClassType type;
    protected String id;

    protected String chatPrefix;


    public DefaultClass(){
        type = ClassType.DEFAULT_CLASS;
        id = "default_class";
        chatPrefix = ChatColor.GRAY + "[CLASSLESS]";
    }

    //Getters and setters

    public String getID(){
        return id;
    }

    public String getChatPrefix(){
        return chatPrefix;
    }

}
