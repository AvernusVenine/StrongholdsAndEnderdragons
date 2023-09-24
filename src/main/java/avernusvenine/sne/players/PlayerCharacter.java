package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.races.DefaultRace;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlayerCharacter {

    protected final String uuid;

    // Open an anvil screen for the player, prompting them to enter text to name their character
    protected String characterName;
    protected int experience;
    protected int level;
    protected int id;
    protected DefaultClass.ClassType classType;
    protected DefaultRace.RaceType raceType;

    public PlayerCharacter(Player player){
        uuid = player.getUniqueId().toString();
    }

    public void createInDatabase(){
        try{
            StrongholdsAndEnderdragons.databaseHandler.createCharacter(this);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void saveToDatabase(){
        try{
            StrongholdsAndEnderdragons.databaseHandler.saveCharacter(this);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setExperience(int xp){
        experience = xp;
    }

    public int getExperience(){return experience;}

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){return level;}

    public void setID(int id){
        this.id = id;
    }

    public int getID(){return id;}

    public void setRaceType(DefaultRace.RaceType type){
        this.raceType = type;
    }

    public DefaultRace.RaceType getRaceType(){return raceType;}

    public void setClassType(DefaultClass.ClassType type){
        this.classType = type;
    }

    public DefaultClass.ClassType getClassType(){return classType;}

    public void setName(String name){
        this.characterName = name;
    }

    public String getName(){return characterName;}

    public String getUUID(){return uuid;}

    public String getChatPrefix(){

        String prefix = StrongholdsAndEnderdragons.raceDictionary.get(raceType).getChatPrefix() + " " +
                ChatColor.RESET + StrongholdsAndEnderdragons.classDictionary.get(classType).getChatPrefix() +
                ChatColor.RESET + ChatColor.BOLD + " [" + level + "] " + ChatColor.RESET;

        return prefix;
    }
}
