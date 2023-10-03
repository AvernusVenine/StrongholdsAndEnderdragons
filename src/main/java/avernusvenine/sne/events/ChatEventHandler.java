package avernusvenine.sne.events;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.players.PlayerCharacter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEventHandler implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){

        PlayerCharacter playerCharacter = PlayerDictionary.get(event.getPlayer().getUniqueId().toString()).getPlayerCharacter();

        if(playerCharacter == null)
            return;

        event.setFormat(playerCharacter.getChatPrefix() + "<" + playerCharacter.getName() + ">: " + event.getMessage());
    }

}
