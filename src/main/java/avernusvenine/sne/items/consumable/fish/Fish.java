package avernusvenine.sne.items.consumable.fish;

import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.items.consumable.Food;
import avernusvenine.sne.professions.Profession.ProfessionType;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fish extends Food {

    protected float average = .5f;
    protected float deviation = .5f;

    protected List<Biome> biomes = new ArrayList<>();

    protected TextComponent initialLore;

    public void sizeFish(ItemStack oldItem){
        NBTItem nbtItem = new NBTItem(oldItem);
        Random rand = new Random();

        float randomFloat = (float) rand.nextGaussian() * deviation + average;

        List<Component> lore = new ArrayList<>();
        lore.add(initialLore);
        lore.add(Component.text(""));

        nbtItem.setFloat(NBTFlags.fishLength, randomFloat);

        if(randomFloat <= average) {
            nbtItem.setInteger(NBTFlags.fishSize, 0);
            lore.add(Component.text("SMALL").color(commonColor));
        }
        else if(randomFloat <= average + (deviation * 1.5f)) {
            nbtItem.setInteger(NBTFlags.fishSize, 1);
            nbtItem.setFloat(NBTFlags.foodLevel, nbtItem.getFloat(NBTFlags.foodLevel) + 1);
            nbtItem.setFloat(NBTFlags.saturation, nbtItem.getFloat(NBTFlags.saturation));
            lore.add(Component.text("MEDIUM").color(commonColor));
        }
        else if(randomFloat <= average + (deviation * 3)){
            nbtItem.setInteger(NBTFlags.fishSize, 2);
            nbtItem.setFloat(NBTFlags.foodLevel, nbtItem.getFloat(NBTFlags.foodLevel) + 2);
            nbtItem.setFloat(NBTFlags.saturation, nbtItem.getFloat(NBTFlags.saturation) + 1);
            lore.add(Component.text("LARGE").color(commonColor));
        }
        else{
            nbtItem.setInteger(NBTFlags.fishSize, 3);
            nbtItem.setFloat(NBTFlags.foodLevel, nbtItem.getFloat(NBTFlags.foodLevel) + 4);
            nbtItem.setFloat(NBTFlags.saturation, nbtItem.getFloat(NBTFlags.saturation) + 2);
            lore.add(Component.text("HUMONGOUS").color(commonColor));
        }

        lore.add(Component.text(""));

        if(randomFloat < 0.5f) {
            lore.add(Component.text(String.format("%.2f", randomFloat*100) + "cm").color(commonColor));
        }
        else{
            lore.add(Component.text(String.format("%.2f", randomFloat) + "m").color(commonColor));
        }

        ItemStack newItem = nbtItem.getItem();
        newItem.lore(lore);

        oldItem.setItemMeta(newItem.getItemMeta());
    }

    public List<Biome> getBiomes(){
        return biomes;
    }

    public int getFishExperience(){
        return switch (rarity) {
            case COMMON -> 10;
            case UNCOMMON -> 25;
            case RARE -> 50;
            case EPIC -> 100;
            case LEGENDARY -> 500;
            case ARTIFACT -> 5000;
            default -> 0;
        };
    }
}
