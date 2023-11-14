package avernusvenine.sne.classes;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.races.Race;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class DefaultClass {

    public enum ClassType{
        ARTIFICER(0, new Artificer()),
        BARBARIAN(1, new Barbarian()),
        BARD(2, new Bard()),
        SHAMAN(3, new Shaman()),
        CLERIC(4, new Cleric()),
        DRUID(5, new Druid()),
        DEFAULT_CLASS(6, null),
        FIGHTER(7, new Fighter()),
        MONK(8, new Monk()),
        PALADIN(9, new Paladin()),
        RANGER(10, new Ranger()),
        ROGUE(11, new Rogue()),
        SORCERER(12, new Sorcerer()),
        WARLOCK(13, new Warlock()),
        WIZARD(14, new Wizard());

        private final int id;
        private final DefaultClass sneClass;

        ClassType(int i, DefaultClass c){
            id = i;
            sneClass = c;
        }

        public int getID(){
            return id;
        }

        public DefaultClass getSneClass(){
            return sneClass;
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

    public int getMaxResource(int level){
        return 0;
    }

    public String getID(){
        return id;
    }

    public String getChatPrefix(){
        return chatPrefix;
    }

}
