package avernusvenine.sne.players;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.classes.DefaultClass.ClassType;
import avernusvenine.sne.races.Race.RaceType;
import avernusvenine.sne.professions.Profession.ProfessionType;

import avernusvenine.sne.spells.Spell;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerCharacter {

    private final int[] XP_PER_LEVEL = new int[]{
            0, // 0 placeholder
            250, 750, 1250, 2250, 3250, // 1-5
            4250, 5500, 6750, 8000, 9250, // 5-10
            10500, 12000, 13500, 15000, 16500, // 10-15
            18000, 20000, 22000, 24000, 26000 // 15-20
    };

    private int currentResource;

    protected final String uuid;
    protected final Player player;

    protected HashMap<String, QuestStatus> quests = new HashMap<>();
    protected HashMap<String, Float> relationships = new HashMap<>();

    protected String characterName;
    protected int experience;
    protected int level;
    protected int id;
    protected ClassType classType;
    protected RaceType raceType;

    protected HashMap<ProfessionType, PlayerProfession> professions = new HashMap<>();
    protected List<String> spells = new ArrayList<>();

    public PlayerCharacter(Player player){
        uuid = player.getUniqueId().toString();
        this.player = player;

        setProfession(0, 0, ProfessionType.FISHING);
        setProfession(0, 0, ProfessionType.COOKING);

        currentResource = 100;
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

    // SPELLS

    public void learnSpell(Spell spell){
        spells.add(spell.getID());
        player.sendMessage(Component.text("You have learned the spell ").append(spell.getDisplayName()).append(Component.text("!")));
    }

    public void addSpell(String id){
        spells.add(id);
    }

    public List<String> getUnlockedSpells(){
        return spells;
    }

    public boolean checkSpellUnlocked(String id){
        return spells.contains(id);
    }


    // RELATIONSHIPS

    public void addRelationship(String id, float level){
        relationships.replace(id, relationships.get(id) + level);
    }

    public float getRelationship(String id){

        relationships.putIfAbsent(id, 0f);

        return relationships.get(id);
    }

    public HashMap<String, Float> getRelationships(){
        return relationships;
    }


    // QUESTS

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

        return quests.get(id).status == QuestStatus.Status.TURNED_IN;
    }

    public boolean checkQuestCompletion(List<String> idList){

        if(idList.isEmpty())
            return true;

        for(String id : idList){

            if(!quests.containsKey(id))
                return false;
            else if(quests.get(id).status != QuestStatus.Status.TURNED_IN)
                return false;

        }

        return true;
    }

    public HashMap<String, QuestStatus> getQuests(){
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

    public boolean hasRecipeUnlocked(ItemStack item, ProfessionType type){
        return professions.get(type).hasUnlockedRecipe(item);
    }


    // RECIPES AND PROFESSIONS

    public void unlockRecipe(ItemStack item, ProfessionType type){
        professions.get(type).unlockRecipe(item);
    }

    public void unlockRecipe(List<ItemStack> item, ProfessionType type){
        professions.get(type).unlockRecipe(item);
    }

    public List<ItemStack> getUnlockedRecipes(ProfessionType type){
        return professions.get(type).getUnlockedRecipes();
    }

    public void setProfession(int level, int experience, ProfessionType type){
        if(professions.containsKey(type)){
            professions.replace(type, new PlayerProfession(level, experience, type));
            return;
        }

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

    public HashMap<ProfessionType, PlayerProfession> getProfessions(){
        return professions;
    }

    public boolean getProfessionsFull(){
        return professions.size() >= 4;
    }

    public void addProfessionExperience(ProfessionType type, int experience){
        professions.get(type).addExperience(experience, player);
    }


    // LEVELING AND EXPERIENCE

    public void addExperience(int xp){
        experience += xp;

        if(experience >= XP_PER_LEVEL[level]){
            experience -= XP_PER_LEVEL[level];
            setLevel(level + 1);

            player.sendMessage(Component.text("Congratulations, you have reached level " + level + "!"));
        }
    }

    public void setExperience(int xp){
        experience = xp;
    }

    public int getExperience(){return experience;}

    public void setLevel(int level){
        this.level = level;

        if(classType != null)
            currentResource = classType.getSneClass().getMaxResource(level);
    }

    public int getLevel(){return level;}

    // RESOURCE MANAGEMENT

    public void addResource(int amount){
        currentResource += amount;
        if(currentResource > classType.getSneClass().getMaxResource(level))
            currentResource = classType.getSneClass().getMaxResource(level);
    }

    public void removeResource(int amount){
        currentResource -= amount;
    }

    public void setResource(int amount){
        currentResource = amount;
        if(currentResource > classType.getSneClass().getMaxResource(level))
            currentResource = classType.getSneClass().getMaxResource(level);
    }

    public int getCurrentResource(){
        return currentResource;
    }


    //Getters and setters

    public void setID(int id){
        this.id = id;
    }

    public int getID(){return id;}

    public void setRaceType(RaceType type){
        this.raceType = type;
    }

    public RaceType getRaceType(){return raceType;}

    public void setClassType(ClassType type){
        this.classType = type;
    }

    public ClassType getClassType(){return classType;}

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


    public static class QuestStatus{

        public enum Status {
            NOT_ACCEPTED(0),
            ACCEPTED(1),
            IN_PROGRESS(2),
            TURNED_IN(3),
            COMPLETED(4);

            final int id;

            Status(final int i){
                this.id = i;
            }

            public int getID(){
                return id;
            }

            public static Status fromID(int id){
                for(Status type : values()){
                    if(type.getID() == id)
                        return type;
                }
                return null;
            }
        }

        public Status status;
        public int progress;

        public QuestStatus(Status status, int progress){
            this.status = status;
            this.progress = progress;
        }
    }
}
