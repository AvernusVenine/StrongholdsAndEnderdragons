package avernusvenine.sne.professions;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Profession implements Listener{

    public enum ProfessionType{
        FISHING(0, new Fishing()),
        INSCRIPTION(1, null),
        JEWELCRAFTING(2, null),
        BLACKSMITHING(3, null),
        COOKING(4, new Cooking()),
        HERBALISM(5, null),
        ALCHEMY(6, null),
        MINING(7, null),
        ENCHANTING(8, null);

        private final int id;
        private final Profession profession;

        ProfessionType(int i, Profession p){
            id = i;
            profession = p;
        }

        public int getID(){
            return id;
        }

        public Profession getProfession(){
            return profession;
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

    protected List<ItemStack> initialRecipes = new ArrayList<>();

    public List<ItemStack> getInitialRecipes(){
        return initialRecipes;
    }

}
