package avernusvenine.sne.items.interactable.castable;

import avernusvenine.sne.ItemDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class AbilityBook extends InstantCastable{

    public AbilityBook(){
        id = "ability_book";
        material = Material.BOOK;

        customModel = -1;
        cooldownCustomModel = -1;
        overrideItemModel = true;

        TextComponent displayName = Component.text("Ability Book");

        List<ItemFlag> itemFlags = new ArrayList<>();
        itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
        itemFlags.add(ItemFlag.HIDE_ITEM_SPECIFICS);

        item = generateCastableItem(material, 1, displayName, new ArrayList<>(), itemFlags, false, id);

        ItemDictionary.put(id, this);
    }

}
