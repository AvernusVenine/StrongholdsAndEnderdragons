package avernusvenine.sne.items.interactable;

import avernusvenine.sne.Globals.ActionType;
import avernusvenine.sne.NBTFlags;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.classes.DefaultClass.ClassType;
import avernusvenine.sne.items.SneItem;

import avernusvenine.sne.players.PlayerCharacter;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Interactable extends SneItem {

    private final static TextComponent INVALID_CLASS_RESPONSE = Component.text("Your class is unable to use this item!");
    private final static TextComponent INVALID_LEVEL_RESPONSE = Component.text("Your level is too low to use this item!");

    protected List<ClassType> validClasses = new ArrayList<>(){{
        add(ClassType.ARTIFICER);
        add(ClassType.BARBARIAN);
        add(ClassType.BARD);
        add(ClassType.CLERIC);
        add(ClassType.DRUID);
        add(ClassType.FIGHTER);
        add(ClassType.MONK);
        add(ClassType.PALADIN);
        add(ClassType.RANGER);
        add(ClassType.ROGUE);
        add(ClassType.SHAMAN);
        add(ClassType.SORCERER);
        add(ClassType.WARLOCK);
        add(ClassType.WIZARD);
    }};
    protected int levelRequired;

    protected int customModel;
    protected int cooldownCustomModel;
    protected boolean overrideItemModel = false;

    public abstract void onItemUse(Player player, Entity entity, ActionType type, Event event);

    public boolean denyIfInvalid(Player player){
        PlayerCharacter character = PlayerDictionary.get(player).getPlayerCharacter();

        if(!validClasses.contains(character.getClassType())){
            player.sendMessage(INVALID_CLASS_RESPONSE);
            return false;
        }
        else if(character.getLevel() < levelRequired){
            player.sendMessage(INVALID_LEVEL_RESPONSE);
            return false;
        }
        return true;
    }

    public boolean canClassUse(ClassType type){
        return validClasses.contains(type);
    }

    public boolean canLevelUse(int level){
        return level >= levelRequired;
    }

    public int getCustomModel(){
        return customModel;
    }

    public int getCooldownModel(){
        return cooldownCustomModel;
    }

    public boolean overrideItemModel(){
        return overrideItemModel;
    }
}
