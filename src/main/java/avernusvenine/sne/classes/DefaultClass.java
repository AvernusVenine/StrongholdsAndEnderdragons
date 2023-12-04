package avernusvenine.sne.classes;

import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.races.Race;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultClass {

    public enum ClassType{
        ARTIFICER(0, new Artificer()),
        BARBARIAN(1, new Barbarian()),
        BARD(2, new Bard()),
        SHAMAN(3, new Shaman()),
        CLERIC(4, new Cleric()),
        DRUID(5, new Druid()),
        FIGHTER(6, new Fighter()),
        MONK(7, new Monk()),
        PALADIN(8, new Paladin()),
        RANGER(9, new Ranger()),
        ROGUE(10, new Rogue()),
        SORCERER(11, new Sorcerer()),
        WARLOCK(12, new Warlock()),
        WIZARD(13, new Wizard());

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

    //Getters and setters

    public abstract void onLevelUp(PlayerCharacter character, int level);

    public abstract int getMaxResource(int level);

    public String getID(){
        return id;
    }

    public String getChatPrefix(){
        return chatPrefix;
    }

}
