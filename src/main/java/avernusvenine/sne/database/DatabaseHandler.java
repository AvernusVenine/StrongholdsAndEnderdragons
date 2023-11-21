package avernusvenine.sne.database;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.players.PlayerProfession;
import avernusvenine.sne.professions.Profession.ProfessionType;
import avernusvenine.sne.races.Race;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {

    private final Connection connection;

    private final String questTablePrefix = "quests_";
    private final String professionTablePrefix = "professions_";
    private final String recipeTablePrefix = "recipes_";

    public DatabaseHandler(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        Statement statement = connection.createStatement();

        // Table for players and all things associated with their account
        statement.execute("CREATE TABLE IF NOT EXISTS players(" +
                "uuid TEXT PRIMARY KEY, " +
                "username TEXT NOT NULL, " +
                "prefix TEXT NOT NULL DEFAULT '', " +
                "rank TEXT NOT NULL DEFAULT 'DefaultRank')");

        // Tables for player characters
        statement.execute("CREATE TABLE IF NOT EXISTS characters(" +
                "character_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "uuid TEXT NOT NULL," +
                "name TEXT NOT NULL, " +
                "race INTEGER NOT NULL, " +
                "class INTEGER NOT NULL, " +
                "level INT NOT NULL DEFAULT 1, " +
                "xp INT NOT NULL DEFAULT 0)");

        // Table for NPC locations in the world
        statement.execute("CREATE TABLE IF NOT EXISTS npcs(" +
                "npc_id TEXT PRIMARY KEY, " +
                "x INT NOT NULL, " +
                "y INT NOT NULL, " +
                "z INT NOT NULL, " +
                "pitch REAL NOT NULL," +
                "yaw REAL NOT NULL, " +
                "world TEXT NOT NULL)");

        statement.close();
    }

    public void createCharacter(PlayerCharacter playerCharacter) throws SQLException{

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO characters (uuid, name, class, race) VALUES (?, ?, ?, ?)")){

            preparedStatement.setString(1, playerCharacter.getUUID());
            preparedStatement.setString(2, playerCharacter.getName());
            preparedStatement.setInt(3, playerCharacter.getClassType().getID());
            preparedStatement.setInt(4, playerCharacter.getRaceType().getID());

            preparedStatement.executeUpdate();
        }
    }

    public PlayerCharacter loadCharacter(int id, Player player) throws SQLException {

        PlayerCharacter playerCharacter = new PlayerCharacter(player);

        playerCharacter.setID(id);

        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS "
                + questTablePrefix + playerCharacter.getID() + "(" +
                "quest_id TEXT PRIMARY KEY, " +
                "status INTEGER NOT NULL DEFAULT FALSE, " +
                "progress INTEGER DEFAULT 0)");

        statement.execute("CREATE TABLE IF NOT EXISTS "
                + professionTablePrefix + playerCharacter.getID() + "(" +
                "profession INTEGER PRIMARY KEY, " +
                "xp INTEGER DEFAULT 0, " +
                "level INTEGER DEFAULT 0)");

        statement.execute("CREATE TABLE IF NOT EXISTS "
                + recipeTablePrefix + playerCharacter.getID() + "(" +
                "item STRING PRIMARY KEY, " +
                "profession INTEGER NOT NULL)");
        statement.close();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM characters WHERE character_id = ?");
        preparedStatement.setString(1, Integer.toString(id));
        ResultSet resultSet = preparedStatement.executeQuery();

        playerCharacter.setName(resultSet.getString("name"));
        playerCharacter.setExperience(resultSet.getInt("xp"));
        playerCharacter.setLevel(resultSet.getInt("level"));
        playerCharacter.setClassType(DefaultClass.ClassType.fromID(resultSet.getInt("class")));
        playerCharacter.setRaceType(Race.RaceType.fromID(resultSet.getInt("race")));

        preparedStatement = connection.prepareStatement("SELECT * FROM " + questTablePrefix + playerCharacter.getID());

        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            playerCharacter.addQuest(resultSet.getString("quest_id"),
                    PlayerCharacter.QuestStatus.Status.fromID(resultSet.getInt("status")),
                    resultSet.getInt("progress"));
        }
        preparedStatement.close();

        preparedStatement = connection.prepareStatement("SELECT * FROM " + professionTablePrefix + playerCharacter.getID());

        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            playerCharacter.setProfession(resultSet.getInt("level"), resultSet.getInt("xp"),
                    ProfessionType.fromID(resultSet.getInt("profession")));
        }
        preparedStatement.close();

        preparedStatement = connection.prepareStatement("SELECT * FROM " + recipeTablePrefix + playerCharacter.getID());

        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            playerCharacter.unlockRecipe(ItemDictionary.get(resultSet.getString("item")).getItem(),
                    ProfessionType.fromID(resultSet.getInt("profession")));
        }
        preparedStatement.close();

        return playerCharacter;
    }

    public void saveCharacter(PlayerCharacter playerCharacter) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE characters SET xp = ?, level = ? WHERE character_id = ?");
        preparedStatement.setInt(1, playerCharacter.getExperience());
        preparedStatement.setInt(2, playerCharacter.getLevel());
        preparedStatement.setInt(3, playerCharacter.getID());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        for(Map.Entry<String, PlayerCharacter.QuestStatus> entry : playerCharacter.getQuests().entrySet()){
            preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO " + questTablePrefix + playerCharacter.getID()
                    + " (status, progress, quest_id) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, entry.getValue().status.getID());
            preparedStatement.setInt(2, entry.getValue().progress);
            preparedStatement.setString(3, entry.getKey());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

        for(Map.Entry<ProfessionType, PlayerProfession> entry : playerCharacter.getProfessions().entrySet()){
            preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO " + professionTablePrefix + playerCharacter.getID()
                    + " (profession, xp, level) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, entry.getKey().getID());
            preparedStatement.setInt(2, entry.getValue().getExperience());
            preparedStatement.setInt(3, entry.getValue().getLevel());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            for(ItemStack item : entry.getValue().getUnlockedRecipes()){
                preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO " + recipeTablePrefix + playerCharacter.getID()
                        + " (item, profession) VALUES (?, ?)");

                NBTItem nbtItem = new NBTItem(item);

                preparedStatement.setString(1, nbtItem.getString(NBTFlags.itemID));
                preparedStatement.setInt(2, entry.getKey().getID());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }
    }

    public Map<String, Integer> getCharacters(String uuid) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM characters WHERE uuid = ?");
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<String, Integer> characters = new HashMap<String, Integer>();

        while(resultSet.next()){
            characters.put(resultSet.getString("name"), resultSet.getInt("character_id"));
        }

        preparedStatement.close();
        return characters;
    }

    public void addPlayer(Player player) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, username) VALUES (?, ?)");
        preparedStatement.setString(1, player.getUniqueId().toString());
        preparedStatement.setString(2, player.getName());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public boolean playerExists(Player player) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")){
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public boolean playerExists(String username) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE username = ?")){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public void setPlayerRank(String username, String rank) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET rank = ? WHERE username = ?");
        preparedStatement.setString(1, rank);
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public String getPlayerRank(String username) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT rank FROM players WHERE username = ?")){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("rank");
        }
    }

    public void setNPCInfo(String id, int x, int y, int z, float pitch, float yaw, String world) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO npcs " +
                "(npc_id, x, y, z, pitch, yaw, world) values (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, id);
        preparedStatement.setInt(2, x);
        preparedStatement.setInt(3, y);
        preparedStatement.setInt(4, z);
        preparedStatement.setFloat(5, pitch);
        preparedStatement.setFloat(6, yaw);
        preparedStatement.setString(7, world);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void spawnNPCs() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM npcs");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            World world = Bukkit.getServer().getWorld(resultSet.getString("world"));

            if(world == null)
                continue;

            Location location = new Location(world, resultSet.getInt("x"), resultSet.getInt("y"),
                    resultSet.getInt("z"), resultSet.getFloat("pitch"), resultSet.getFloat("yaw"));

            NPCDictionary.get(resultSet.getString("npc_id")).spawnNPC(location);
        }
    }

    public void closeConnection() throws SQLException {
        if(connection != null && !connection.isClosed())
            connection.close();
    }
}
