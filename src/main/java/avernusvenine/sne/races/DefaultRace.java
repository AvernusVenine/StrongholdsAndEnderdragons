package avernusvenine.sne.races;

import avernusvenine.sne.StrongholdsAndEnderdragons;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DefaultRace {

    public enum RaceType {
        ELF,
        DRAGON_KIN,
        HALF_ELF,
        DWARF,
        GNOME,
        HUMAN,
        HALF_ORC,
        ORC,
        TIEFLING,
        FELIDAE,
        DEFAULT_RACE
    }

    protected String id;
    protected String chatPrefix;

    public DefaultRace.RaceType type;

    public DefaultRace(){
        type = RaceType.DEFAULT_RACE;
        id = "default_race";
        chatPrefix = ChatColor.GRAY + "[RACELESS]";
    }

    public static void setPlayerRace(Player player, RaceType type){

    }

    public String getChatPrefix(){
        return chatPrefix;
    }

}
