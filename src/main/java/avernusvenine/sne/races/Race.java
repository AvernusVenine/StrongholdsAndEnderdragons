package avernusvenine.sne.races;

import org.bukkit.ChatColor;

public class Race {

    public enum RaceType {
        ELF(0),
        DRAGON_KIN(1),
        HALF_ELF(2),
        DWARF(3),
        GNOME(4),
        HUMAN(5),
        HALF_ORC(6),
        ORC(7),
        TIEFLING(8),
        FELIDAE(9),
        DEFAULT_RACE(10);

        private final int id;

        RaceType(int i){
            id = i;
        }

        public int getID(){
            return id;
        }

        public static RaceType fromID(int id){
            for(RaceType type : values()){
                if(type.getID() == id)
                    return type;
            }
            return null;
        }
    }

    protected String id;
    protected String chatPrefix;

    public Race.RaceType type;

    public Race(){
        type = RaceType.DEFAULT_RACE;
        id = "default_race";
        chatPrefix = ChatColor.GRAY + "[RACELESS]";
    }

    public String getChatPrefix(){
        return chatPrefix;
    }

}
