package avernusvenine.sne.events;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.charactercreation.CharacterSelectGUI;

import avernusvenine.sne.players.PlayerProfile;
import avernusvenine.sne.status.SlowImmunity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class PlayerEventHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event){
        Player player = event.getPlayer();

        PlayerDictionary.add(new PlayerProfile(player));

        try {
            Globals.openGUI(player, new CharacterSelectGUI(player));

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

        if(PlayerDictionary.get(player).isInDialogue()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityPotionEffectEvent(EntityPotionEffectEvent event){
        if(!(event.getEntity() instanceof Player player) || event.getNewEffect() == null)
            return;

        PotionEffectType type = event.getNewEffect().getType();
        PlayerProfile profile = PlayerDictionary.get(player);

        if (type.equals(PotionEffectType.SLOW)) {
            if(profile.hasStatusEffect(SlowImmunity.class))
                event.setCancelled(true);
        }
    }
}
