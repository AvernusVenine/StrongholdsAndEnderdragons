package avernusvenine.sne.professions;

import org.bukkit.event.Listener;

public class DefaultProfession implements Listener{

    public enum ProfessionType{
        FISHING,
        INSCRIPTION,
        JEWELCRAFTING,
        BLACKSMITHING,
        COOKING,
        HERBALISM,
        ALCHEMY,
        MINING,
        ENCHANTING
    }

    protected ProfessionType type;
    protected String id;

}
