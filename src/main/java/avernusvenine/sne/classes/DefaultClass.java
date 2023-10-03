package avernusvenine.sne.classes;

import avernusvenine.sne.items.SneItem;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class DefaultClass {

    public enum ClassType{
        ARTIFICER,
        BARBARIAN,
        BARD,
        SHAMAN,
        CLERIC,
        DRUID,
        DEFAULT_CLASS,
        FIGHTER,
        MONK,
        PALADIN,
        RANGER,
        ROGUE,
        SORCERER,
        WARLOCK,
        WIZARD
    }

    public enum FeatType{

    }

    // Work in progress, might not implement
    public enum SkillType{
        ACROBATICS,
        MUSIC,
        STEALTH,
        POTIONS,
        HERBALISM,
        MINING,

    }


    protected List<SneItem.ItemType> innateItemProficiency = new ArrayList<SneItem.ItemType>();

    protected ClassType type;
    protected String id;

    protected String chatPrefix;


    public DefaultClass(){
        type = ClassType.DEFAULT_CLASS;
        id = "default_class";
        chatPrefix = ChatColor.GRAY + "[CLASSLESS]";
    }

    //Getters and setters

    public String getID(){
        return id;
    }

    public String getChatPrefix(){return chatPrefix;}

    public List<SneItem.ItemType> getInnateItemProficiency(){
        return innateItemProficiency;
    }

}
