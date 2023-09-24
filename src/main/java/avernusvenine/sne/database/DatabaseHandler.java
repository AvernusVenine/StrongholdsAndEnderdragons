package avernusvenine.sne.database;

import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.classes.DefaultClass;
import avernusvenine.sne.races.DefaultRace;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {

    private final Connection connection;

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
                "race TEXT NOT NULL, " +
                "class TEXT NOT NULL, " +
                "level INT NOT NULL DEFAULT 1, " +
                "xp INT NOT NULL DEFAULT 0)");

        statement.execute("CREATE TABLE IF NOT EXISTS feats(" +
                "character_id INT PRIMARY KEY, " +
                "feat_name TEXT NOT NULL)");

        statement.close();
    }

    public void createCharacter(PlayerCharacter playerCharacter) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO characters (uuid, name, class, race) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, playerCharacter.getUUID());
        preparedStatement.setString(2, playerCharacter.getName());

        switch(playerCharacter.getClassType()){
            case ARTIFICER:
                preparedStatement.setString(3, "artificer");
                break;
            case BARBARIAN:
                preparedStatement.setString(3, "barbarian");
                break;
            case SHAMAN:
                preparedStatement.setString(3, "blood_hunter");
                break;
            case BARD:
                preparedStatement.setString(3, "bard");
                break;
            case CLERIC:
                preparedStatement.setString(3, "cleric");
                break;
            case DRUID:
                preparedStatement.setString(3, "druid");
                break;
            case FIGHTER:
                preparedStatement.setString(3, "fighter");
                break;
            case MONK:
                preparedStatement.setString(3, "monk");
                break;
            case PALADIN:
                preparedStatement.setString(3, "paladin");
                break;
            case RANGER:
                preparedStatement.setString(3, "ranger");
                break;
            case ROGUE:
                preparedStatement.setString(3, "rogue");
                break;
            case SORCERER:
                preparedStatement.setString(3, "sorcerer");
                break;
            case WARLOCK:
                preparedStatement.setString(3, "warlock");
                break;
            case WIZARD:
                preparedStatement.setString(3, "wizard");
                break;
        }

        switch(playerCharacter.getRaceType()){
            case DWARF:
                preparedStatement.setString(4, "dwarf");
                break;
            case DRAGON_KIN:
                preparedStatement.setString(4, "dragon_kin");
                break;
            case ELF:
                preparedStatement.setString(4, "elf");
                break;
            case FELIDAE:
                preparedStatement.setString(4, "felidae");
                break;
            case GNOME:
                preparedStatement.setString(4, "gnome");
                break;
            case HALF_ELF:
                preparedStatement.setString(4, "half_elf");
                break;
            case HALF_ORC:
                preparedStatement.setString(4, "half_orc");
                break;
            case HUMAN:
                preparedStatement.setString(4, "human");
                break;
            case ORC:
                preparedStatement.setString(4, "orc");
                break;
            case TIEFLING:
                preparedStatement.setString(4, "tiefling");
                break;
        }

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public PlayerCharacter loadCharacter(int id, Player player) throws SQLException {

        PlayerCharacter playerCharacter = new PlayerCharacter(player);

        playerCharacter.setID(id);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM characters WHERE character_id = ?");
        preparedStatement.setString(1, Integer.toString(id));
        ResultSet resultSet = preparedStatement.executeQuery();

        playerCharacter.setName(resultSet.getString("name"));
        playerCharacter.setExperience(resultSet.getInt("xp"));
        playerCharacter.setLevel(resultSet.getInt("level"));

        DefaultClass.ClassType classType = DefaultClass.ClassType.DEFAULT_CLASS;
        DefaultRace.RaceType raceType = DefaultRace.RaceType.DEFAULT_RACE;

        String classTitle = resultSet.getString("class");
        String raceTitle = resultSet.getString("race");

        switch(classTitle){
            case "artificer":
                classType = DefaultClass.ClassType.ARTIFICER;
                break;
            case "barbarian":
                classType = DefaultClass.ClassType.BARBARIAN;
                break;
            case "blood_hunter":
                classType = DefaultClass.ClassType.SHAMAN;
                break;
            case "bard":
                classType = DefaultClass.ClassType.BARD;
                break;
            case "cleric":
                classType = DefaultClass.ClassType.CLERIC;
                break;
            case "druid":
                classType = DefaultClass.ClassType.DRUID;
                break;
            case "fighter":
                classType = DefaultClass.ClassType.FIGHTER;
                break;
            case "monk":
                classType = DefaultClass.ClassType.MONK;
                break;
            case "paladin":
                classType = DefaultClass.ClassType.PALADIN;
                break;
            case "ranger":
                classType = DefaultClass.ClassType.RANGER;
                break;
            case "rogue":
                classType = DefaultClass.ClassType.ROGUE;
                break;
            case "sorcerer":
                classType = DefaultClass.ClassType.SORCERER;
                break;
            case "warlock":
                classType = DefaultClass.ClassType.WARLOCK;
                break;
            case "wizard":
                classType = DefaultClass.ClassType.WIZARD;
                break;
        }

        switch(raceTitle){
            case "dwarf":
                raceType = DefaultRace.RaceType.DWARF;
                break;
            case "dragon_kin":
                raceType = DefaultRace.RaceType.DRAGON_KIN;
                break;
            case "elf":
                raceType = DefaultRace.RaceType.ELF;
                break;
            case "felidae":
                raceType = DefaultRace.RaceType.FELIDAE;
                break;
            case "gnome":
                raceType = DefaultRace.RaceType.GNOME;
                break;
            case "half_elf":
                raceType = DefaultRace.RaceType.HALF_ELF;
                break;
            case "half_orc":
                raceType = DefaultRace.RaceType.HALF_ORC;
                break;
            case "human":
                raceType = DefaultRace.RaceType.HUMAN;
                break;
            case "orc":
                raceType = DefaultRace.RaceType.ORC;
                break;
            case "tiefling":
                raceType = DefaultRace.RaceType.TIEFLING;
                break;
        }

        playerCharacter.setClassType(classType);
        playerCharacter.setRaceType(raceType);

        return playerCharacter;
    }

    public void saveCharacter(PlayerCharacter playerCharacter) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE characters SET xp = ?, level = ?, WHERE character_id = ?");
        preparedStatement.setInt(1, playerCharacter.getExperience());
        preparedStatement.setInt(2, playerCharacter.getLevel());
        preparedStatement.setInt(3, playerCharacter.getID());
        preparedStatement.executeUpdate();
        preparedStatement.close();
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

    public void setPlayerRank(Player player, String rank) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET rank = ? WHERE uuid = ?");
        preparedStatement.setString(1, rank);
        preparedStatement.setString(2, player.getUniqueId().toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void setPlayerRank(String username, String rank) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET rank = ? WHERE username = ?");
        preparedStatement.setString(1, rank);
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public String getPlayerRank(Player player) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT rank FROM players WHERE uuid = ?")){
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("rank");
        }
    }

    public String getPlayerRank(String username) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT rank FROM players WHERE username = ?")){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("rank");
        }
    }

    public void closeConnection() throws SQLException {
        if(connection != null && !connection.isClosed())
            connection.close();
    }
}
