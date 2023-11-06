package avernusvenine.sne.players;

import avernusvenine.sne.professions.Profession.ProfessionType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerProfession {

    private int level;
    private int experience;

    private ProfessionType type;

    private List<ItemStack> unlockedRecipes = new ArrayList<>();

    public PlayerProfession(int level, int experience, ProfessionType type){
        this.level = level;
        this.experience = experience;
        this.type = type;
    }

    public int getLevel(){
        return level;
    }

    public ProfessionType getType(){
        return type;
    }

    public int getExperience(){
        return experience;
    }

    public void addExperience(int experience){
        this.experience += experience;
    }

    public boolean hasUnlockedRecipe(ItemStack item){
        return unlockedRecipes.contains(item);
    }
}
