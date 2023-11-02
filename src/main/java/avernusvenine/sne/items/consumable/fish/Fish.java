package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.items.consumable.Food;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fish extends Food {

    protected float average;
    protected float deviation;

    public ItemStack generateSizedFish(){
        NBTItem nbtItem = new NBTItem(item);
        Random rand = new Random();

        float randomFloat = (float) rand.nextGaussian() * deviation + average;

        List<Component> lore = item.lore();

        nbtItem.setFloat(NBTFlags.fishLength, randomFloat);

        if(randomFloat <= average - deviation) {
            nbtItem.setInteger(NBTFlags.fishSize, 0);
            lore.add(Component.text("SMALL").color(commonColor));
        }
        else if(average - deviation < randomFloat && randomFloat <= average + (deviation * 1.5f)) {
            nbtItem.setInteger(NBTFlags.fishSize, 1);
            nbtItem.setFloat(NBTFlags.foodLevel, nbtItem.getFloat(NBTFlags.foodLevel) + 1);
            nbtItem.setFloat(NBTFlags.saturation, nbtItem.getFloat(NBTFlags.saturation) + 0.5f);
            lore.add(Component.text("MEDIUM").color(commonColor));
        }
        else {
            nbtItem.setInteger(NBTFlags.fishSize, 2);
            nbtItem.setFloat(NBTFlags.foodLevel, nbtItem.getFloat(NBTFlags.foodLevel) + 3);
            nbtItem.setFloat(NBTFlags.saturation, nbtItem.getFloat(NBTFlags.saturation) + 1);
            lore.add(Component.text("LARGE").color(commonColor));
        }

        randomFloat *= 100;

        if(randomFloat < 0.5f)
            lore.add(Component.text((int) randomFloat + "cm").color(commonColor));
        else{
            randomFloat = (int) randomFloat;
            lore.add(Component.text(randomFloat/100 + "m").color(commonColor));
        }

        item = nbtItem.getItem();
        item.lore(lore);

        return item;
    }

}
