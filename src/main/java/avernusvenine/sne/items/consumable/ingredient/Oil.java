package avernusvenine.sne.items.consumable.ingredient;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.items.SneItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;

import java.util.ArrayList;

public class Oil extends SneItem {

    public Oil(){
        id = "oil";
        material = Material.PAPER;

        rarity = Rarity.COMMON;

        TextComponent displayName = Component.text("Oil").color(commonColor).toBuilder().build();
        item = generateItem(material, 1, displayName, new ArrayList<>(), new ArrayList<>(), false, id);

        ItemDictionary.put(id, this);
    }

}
