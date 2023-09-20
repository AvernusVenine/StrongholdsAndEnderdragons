package avernusvenine.sne.events;

import avernusvenine.sne.StrongholdsAndEnderdragons;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerEventHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event){
        try {
            if(StrongholdsAndEnderdragons.databaseHandler.playerExists(event.getPlayer()))
                return;

            StrongholdsAndEnderdragons.databaseHandler.addPlayer(event.getPlayer());
            Bukkit.getServer().getConsoleSender().sendMessage("Added " + event.getPlayer().getName() + " to the database");
        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to add player to database!");
            e.printStackTrace();
        }
    }

}
