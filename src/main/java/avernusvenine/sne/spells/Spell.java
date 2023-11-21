package avernusvenine.sne.spells;

import avernusvenine.sne.classes.DefaultClass.ClassType;
import net.kyori.adventure.text.TextComponent;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Spell {

    protected List<ClassType> validClasses = new ArrayList<>();

    protected int castTime;
    protected int range;
    protected int resourceCost;
    protected int levelRequired;
    protected double damage;
    protected int cooldown;
    protected String id;
    protected TextComponent displayName;
    protected int customModel;
    protected int cooldownCustomModel;
    protected Material material = Material.BOOK;

    public abstract void onCast(Player player);

    public boolean validClass(ClassType type){
        return validClasses.contains(type);
    }

    public int getRange(){
        return range;
    }

    public int getResourceCost(){
        return resourceCost;
    }

    public int getCastTime(){
        return castTime;
    }

    public String getID(){
        return id;
    }

    public int getLevelRequired(){
        return levelRequired;
    }

    public int getCustomModel(){
        return customModel;
    }

    public int getCooldownModel(){
        return cooldownCustomModel;
    }

    public int getCooldown(){
        return cooldown;
    }

    public Material getMaterial(){
        return material;
    }

    public TextComponent getDisplayName(){
        return displayName;
    }
}
