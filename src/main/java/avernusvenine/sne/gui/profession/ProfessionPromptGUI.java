package avernusvenine.sne.gui.profession;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.gui.AcceptDenyGUI;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ProfessionPromptGUI extends AcceptDenyGUI {

    public ProfessionPromptGUI(){
        id = "profession_accept";
        title = "Learn Profession?";
        inventory = Bukkit.createInventory(null, 9, title);
        initializeItems();
    }

    @Override
    protected void onAccept(Player player) {

    }

    @Override
    protected void onDeny(Player player) {

    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        HandlerList.unregisterAll(this);
    }
}
