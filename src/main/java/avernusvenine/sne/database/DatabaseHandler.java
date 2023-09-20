package avernusvenine.sne.database;

import org.bukkit.entity.Player;

import java.sql.*;

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
                "character_id AUTO_INCREMENT INT PRIMARY KEY, " +
                "uuid STRING NOT NULL," +
                "race TEXT NOT NULL, " +
                "class TEXT NOT NULL, " +
                "level INT NOT NULL, " +
                "xp INT NOT NULL)");

        statement.execute("CREATE TABLE IF NOT EXISTS feats(" +
                "character_id INT PRIMARY KEY, " +
                "feat_name STRING NOT NULL)");

        statement.close();
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
