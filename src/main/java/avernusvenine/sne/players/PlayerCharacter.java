package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.quests.Quest;
import avernusvenine.sne.races.Race;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerCharacter {

    protected final String uuid;

    protected BiMap<String, QuestStatus> quests = HashBiMap.create();
    protected BiMap<String, Float> relationships = HashBiMap.create();

    protected String characterName;
    protected int experience;
    protected int level;
    protected int id;
    protected DefaultClass.ClassType classType;
    protected Race.RaceType raceType;

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

    public void addRelationship(String id, double level){

    }

    public float getRelationship(String id){

        if(relationships.get(id) == null){
            relationships.put(id, 0f);
            return 0f;
        }

        return relationships.get(id);
    }

    public BiMap<String, Float> getRelationships(){
        return relationships;
    }

    public void addQuest(String id, boolean status, int progress){
        quests.put(id, new QuestStatus(status, progress));
    }

    public void addQuestProgress(String id, int progress){
        quests.get(id).progress += progress;
    }

    public void updateQuestProgress(String id, int progress){
        quests.get(id).progress = progress;
    }

    public void updateQuestStatus(String id, boolean status){
        quests.get(id).status = status;
    }

    //Getters and setters

    public BiMap<String, QuestStatus> getQuests(){
        return quests;
    }

    public boolean getQuestStatus(String id){
        return quests.get(id).status;
    }

    public int getQuestProgress(String id){
        return quests.get(id).progress;
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

    public void setRaceType(Race.RaceType type){
        this.raceType = type;
    }

    public Race.RaceType getRaceType(){return raceType;}

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

        ChatColor levelColor = ChatColor.WHITE;

        if(level < 11 && level > 5)
            levelColor = ChatColor.GRAY;
        else if(level < 16 && level > 10)
            levelColor = ChatColor.GOLD;
        else if(level > 15)
            levelColor = ChatColor.GRAY;


        String prefix = StrongholdsAndEnderdragons.raceDictionary.get(raceType).getChatPrefix() + " " +
                ChatColor.RESET + StrongholdsAndEnderdragons.classDictionary.get(classType).getChatPrefix() +
                ChatColor.RESET +  levelColor + "" + ChatColor.BOLD + " [" + level + "] " + ChatColor.RESET;

        return prefix;
    }

    public class QuestStatus{

        public boolean status;
        public int progress;

        public QuestStatus(boolean status, int progress){
            this.status = status;
            this.progress = progress;
        }

    }
}
