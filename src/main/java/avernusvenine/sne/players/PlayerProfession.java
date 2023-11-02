package avernusvenine.sne.players;

import avernusvenine.sne.professions.DefaultProfession;
import avernusvenine.sne.professions.DefaultProfession.ProfessionType;

public class PlayerProfession {

    private int level;
    private int experience;

    private ProfessionType type;

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

    public void addExperience(int experience){
        this.experience += experience;
    }
}
