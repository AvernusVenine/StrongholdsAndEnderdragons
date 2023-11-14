package avernusvenine.sne.gui.charactercreation;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.players.PlayerCharacter;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;

public class CharacterSelectGUI extends DefaultGUI {

    public CharacterSelectGUI(Player player){
        owner = player;
        id = "character_select";
        title = "Character Selection";
        inventory = Bukkit.createInventory(null, 9,title);
        initializeItems();
    }

    public void initializeItems(){
        String uuid = owner.getUniqueId().toString();
        Map<String, Integer> characters;

        try{
            characters = StrongholdsAndEnderdragons.databaseHandler.getCharacters(uuid);
        }
        catch(SQLException e){
            e.printStackTrace();
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load character!");
            return;
        }

        int i = 0;
        for(HashMap.Entry<String, Integer> entry : characters.entrySet()) {
            ItemStack item = createGUIItem(Material.WHITE_STAINED_GLASS_PANE, entry.getKey(), new ArrayList<String>(), "load_character");

            NBTItem nbtItem = new NBTItem(item);
            nbtItem.setInteger("CharacterID", entry.getValue());

            inventory.setItem(i, nbtItem.getItem());
            i++;
        }

        inventory.setItem(i, createGUIItem(Material.BLUE_STAINED_GLASS_PANE,
                ChatColor.BLUE + "" + ChatColor.BOLD + "CREATE NEW CHARACTER", new ArrayList<String>(), "new_character"));
        i++;

        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<String>());

        while(i < 9){
            inventory.setItem(i, blankItem);
            i++;
        }

    }

    @Override
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){

        if(!event.getView().getOriginalTitle().equals(title))
            return;

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if(item == null || item.getType().isAir())
            return;

        NBTItem nbtItem = new NBTItem(item);

        switch(nbtItem.getString(nbtID)){
            case "new_character":
                Globals.openGUI(player, new ClassSelectGUI(player));

                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
                event.setCancelled(true);

                PlayerDictionary.get(player).setPlayerCharacter(new PlayerCharacter(player));
                break;
            case "load_character":
                try{
                    PlayerDictionary.get(player).setPlayerCharacter(StrongholdsAndEnderdragons.databaseHandler
                            .loadCharacter(nbtItem.getInteger("CharacterID"), player));
                }
                catch(SQLException e){
                    e.printStackTrace();
                }

                player.playSound(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                event.setCancelled(true);
                player.closeInventory();

                PlayerDictionary.get(player).onCharacterSelect();
                break;
        }
    }

}
