package avernusvenine.sne.professions;

import org.bukkit.event.Listener;

public class Profession implements Listener{

    public enum ProfessionType{
        FISHING(0),
        INSCRIPTION(1),
        JEWELCRAFTING(2),
        BLACKSMITHING(3),
        COOKING(4),
        HERBALISM(5),
        ALCHEMY(6),
        MINING(7),
        ENCHANTING(8);

        private final int id;

        ProfessionType(int i){
            id = i;
        }

        public int getID(){
            return id;
        }

        public static ProfessionType fromID(int id){
            for(ProfessionType type : values()){
                if(type.getID() == id)
                    return type;
            }
            return null;
        }
    }

    protected ProfessionType type;
    protected String id;

}
