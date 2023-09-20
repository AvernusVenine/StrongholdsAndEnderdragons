package avernusvenine.sne.classes;

import avernusvenine.sne.items.CustomItem;

import java.util.ArrayList;
import java.util.List;

public class DefaultClass {

    public enum ClassType{
        ARTIFICER,
        BARBARIAN,
        BARD,
        BLOOD_HUNTER,
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


    protected List<CustomItem.ItemType> innateItemProficiency = new ArrayList<CustomItem.ItemType>();

    protected ClassType type;
    protected String id;


    public DefaultClass(){
        type = ClassType.DEFAULT_CLASS;
        id = "default_class";
    }

    //Getters and setters

    public String getID(){
        return id;
    }

    public List<CustomItem.ItemType> getInnateItemProficiency(){
        return innateItemProficiency;
    }

}
