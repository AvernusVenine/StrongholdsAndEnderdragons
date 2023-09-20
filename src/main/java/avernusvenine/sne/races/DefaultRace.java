package avernusvenine.sne.races;

import avernusvenine.sne.StrongholdsAndEnderdragons;

import org.bukkit.entity.Player;

public class DefaultRace {

    public enum RaceType {
        ELF,
        DRAGON_FOLK,
        HALF_ELF,
        DWARF,
        GNOME,
        HUMAN,
        HALF_ORC,
        ORC,
        TIEFLING
    }

    protected String displayName;
    protected String id;

    public DefaultRace(){

    }

    public static void setPlayerRace(Player player, RaceType type){

    }

}
