package avernusvenine.sne.gui.utility;

import avernusvenine.sne.*;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.gui.charactercreation.ClassSelectGUI;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.interactable.castable.Castable;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.spells.Spell;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;

public class SpellSelectGUI extends DefaultGUI {

    private final ItemStack item;

    public SpellSelectGUI(Player player, ItemStack item){
        owner = player;
        this.item = item;
        id = "spell_select";
        title = "Spell Selection";
        initializeItems();
    }

    @Override
    public void initializeItems() {
        Castable castable = (Castable) ItemDictionary.get(item);
        List<String> spells = castable.getValidSpells();
        List<String> valid = new ArrayList<>();

        if(spells.isEmpty())
            valid = PlayerDictionary.get(owner).getPlayerCharacter().getUnlockedSpells();
        else{
            for(String spell : spells){
                if(PlayerDictionary.get(owner).getPlayerCharacter().checkSpellUnlocked(spell))
                    valid.add(spell);
            }
        }
        int size = (int) Math.ceil((double) valid.size() / 9.0)*9;

        if(size == 0)
            size = 9;

        inventory = Bukkit.createInventory(null, size, title);

        int i = 0;

        for(String id : valid){
            Spell spell = SpellDictionary.get(id);

            ItemStack item = createGUIItem(spell.getMaterial(), spell.getDisplayName(), new ArrayList<>(), spell.getID());
            SneItem.setCustomModel(item, spell.getCustomModel());

            inventory.setItem(i, item);
            i++;
        }

        ItemStack blankItem = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>());

        for(; i < size; i++){
            inventory.setItem(i, blankItem);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event){
        if(!event.getView().getOriginalTitle().equals(title))
            return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if(item == null || item.getType().isAir())
            return;

        NBTItem nbtItem = new NBTItem(item);
        String id = nbtItem.getString(nbtID);

        if(id.isEmpty())
            return;

        Castable.setSpell(id, this.item);
        player.closeInventory();
    }
}
