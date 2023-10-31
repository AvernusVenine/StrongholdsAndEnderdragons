package avernusvenine.sne.events;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.CharacterSelectGUI;

import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.players.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

        PlayerDictionary.add(new PlayerProfile(player));

        try {
            openCharacterSelect(player);

            if(StrongholdsAndEnderdragons.databaseHandler.playerExists(player))
                return;

            StrongholdsAndEnderdragons.databaseHandler.addPlayer(player);
            Bukkit.getServer().getConsoleSender().sendMessage("Added " + event.getPlayer().getName() + " to the database");
        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to add player to database!");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event){
        Player player = event.getPlayer();

        PlayerProfile playerProfile = PlayerDictionary.get(player.getUniqueId().toString());

        if(playerProfile == null)
            return;

        playerProfile.getPlayerCharacter().saveToDatabase();
        playerProfile.onPlayerQuit();
        PlayerDictionary.remove(player.getUniqueId().toString());
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event){
        Player player = event.getPlayer();

        if(PlayerDictionary.get(player.getUniqueId().toString()).isInDialogue()){
            event.setCancelled(true);
        }

    }

}
