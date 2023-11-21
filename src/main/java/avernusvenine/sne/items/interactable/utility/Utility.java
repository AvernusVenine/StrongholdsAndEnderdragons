package avernusvenine.sne.items.interactable.utility;

import avernusvenine.sne.Globals;
import avernusvenine.sne.Globals.ActionType;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.items.interactable.Interactable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Utility extends Interactable {

    @Override
    public void onItemUse(Player player, Entity entity, ActionType type, Event event){
        if(type == ActionType.PLAYER_RIGHT_CLICK_AIR)
            openGUI(player);
    }

    public void openGUI(Player player){

    }
}
