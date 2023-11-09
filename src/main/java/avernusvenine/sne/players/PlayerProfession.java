package avernusvenine.sne.players;

import avernusvenine.sne.professions.Profession.ProfessionType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerProfession {

    private final double INCREASE_PER_LEVEL = 1.01;

    private int level;
    private int experience;

    private final ProfessionType type;

    private final List<ItemStack> unlockedRecipes = new ArrayList<>();

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

    public void addExperience(int experience, Player player){
        this.experience += experience;

        if(this.experience >= 50 * Math.pow(INCREASE_PER_LEVEL, level)){
            this.experience -= (int) (50 * Math.pow(1.01, level));
            level++;
            player.sendMessage(Component.text("Congratulations! You have reached level " + level + " in " + type));
        }
    }

    public boolean hasUnlockedRecipe(ItemStack item){
        return unlockedRecipes.contains(item);
    }

    public List<ItemStack> getUnlockedRecipes(){
        return unlockedRecipes;
    }

    public void unlockRecipe(ItemStack item){
        unlockedRecipes.add(item);
    }

    public void unlockRecipe(List<ItemStack> item){
        unlockedRecipes.addAll(item);
    }
}
