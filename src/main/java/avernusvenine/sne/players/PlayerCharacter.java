package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.professions.DefaultProfession;
import avernusvenine.sne.quests.Quest;
import avernusvenine.sne.races.Race;
import avernusvenine.sne.professions.DefaultProfession.ProfessionType;

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

    protected HashMap<DefaultProfession.ProfessionType, PlayerProfession> professions = new HashMap<>();

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

    public void addRelationship(String id, float level){
        relationships.replace(id, relationships.get(id) + level);
    }

    public float getRelationship(String id){

        if(relationships.get(id) == null)
            relationships.put(id, 0f);

        return relationships.get(id);
    }

    public BiMap<String, Float> getRelationships(){
        return relationships;
    }

    public void addQuest(String id, QuestStatus.Status status, int progress){
        quests.put(id, new QuestStatus(status, progress));
    }

    public void addQuestProgress(String id, int progress){
        quests.get(id).progress += progress;
    }

    public void updateQuestProgress(String id, int progress){
        quests.get(id).progress = progress;
    }

    public void updateQuestStatus(String id, QuestStatus.Status status){
        quests.get(id).status = status;
    }

    public boolean checkQuestCompletion(String id){

        if(!quests.containsKey(id))
            return false;

        return quests.get(id).status == QuestStatus.Status.COMPLETED;
    }

    public boolean checkQuestCompletion(List<String> idList){

        if(idList.isEmpty())
            return true;

        for(String id : idList){

            if(!quests.containsKey(id))
                return false;
            else if(quests.get(id).status != QuestStatus.Status.COMPLETED)
                return false;

        }

        return true;
    }


    //Getters and setters

    public BiMap<String, QuestStatus> getQuests(){
        return quests;
    }

    public QuestStatus.Status getQuestStatus(String id){

        if(quests.get(id) == null)
            addQuest(id, QuestStatus.Status.NOT_ACCEPTED, 0);

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

    public void setProfession(int level, int experience, ProfessionType type){
        if(professions.size() == 4)
            return;

        professions.put(type, new PlayerProfession(level, experience, type));
    }

    public int getProfessionLevel(ProfessionType type){
        if(!professions.containsKey(type))
            return 0;
        else
            return professions.get(type).getLevel();
    }

    public void addProfessionExperience(ProfessionType type, int experience){
        professions.get(type).addExperience(experience);
    }

    public class QuestStatus{

        public enum Status {
            NOT_ACCEPTED(0),
            ACCEPTED(1),
            IN_PROGRESS(2),
            COMPLETED(3);

            final int value;

            Status(final int i){
                this.value = i;
            }

            public int getValue(){
                return value;
            }
        }

        public Status status;
        public int progress;

        public QuestStatus(Status status, int progress){
            this.status = status;
            this.progress = progress;
        }

        public static Status convertToEnum(int i){
            return switch (i) {
                case 0 -> Status.NOT_ACCEPTED;
                case 1 -> Status.ACCEPTED;
                case 2 -> Status.IN_PROGRESS;
                case 3 -> Status.COMPLETED;
                default -> Status.NOT_ACCEPTED;
            };
        }

    }
}
