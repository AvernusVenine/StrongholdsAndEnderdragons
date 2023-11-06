package avernusvenine.sne.items.interactable.utility;

import avernusvenine.sne.Globals;
import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.gui.utility.CookingGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class CookingKit extends Utility {

    public CookingKit(){
        id = "cooking_kit";
        material = Material.LEATHER_HORSE_ARMOR;

        TextComponent displayName = Component.text("Cooking Kit").color(epicColor).toBuilder().build();

        List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ATTRIBUTES);
        flags.add(ItemFlag.HIDE_ITEM_SPECIFICS);
        flags.add(ItemFlag.HIDE_UNBREAKABLE);

        item = generateItem(material, 1, displayName, new ArrayList<>(), flags, true, id);

        ItemDictionary.put(id, this);
    }

    @Override
    public void openGUI(Player player){
        Globals.openGUI(player, new CookingGUI(player));
    }

}
