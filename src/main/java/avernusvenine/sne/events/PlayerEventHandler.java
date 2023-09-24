package avernusvenine.sne.events;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.CharacterSelectGUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerEventHandler implements Listener {

    private void openCharacterSelect(Player player){
        CharacterSelectGUI gui = new CharacterSelectGUI(player);
        StrongholdsAndEnderdragons.plugin.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
        player.openInventory(gui.getInventory());
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event){
        Player player = event.getPlayer();

        try {
            if(StrongholdsAndEnderdragons.databaseHandler.playerExists(player)) {
                openCharacterSelect(player);
                return;
            }

            openCharacterSelect(player);

            StrongholdsAndEnderdragons.databaseHandler.addPlayer(player);
            Bukkit.getServer().getConsoleSender().sendMessage("Added " + event.getPlayer().getName() + " to the database");
        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to add player to database!");
            e.printStackTrace();
        }
    }

}
