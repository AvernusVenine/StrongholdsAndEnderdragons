package avernusvenine.sne;

import avernusvenine.sne.gui.DefaultGUI;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Globals {

    public enum ActionType{
        UNKNOWN,
        PLAYER_LEFT_CLICK_ENTITY,
        PLAYER_DAMAGED_BY_ENTITY,
        PLAYER_RIGHT_CLICK_AIR,
        PLAYER_RIGHT_CLICK_ENTITY,
        PLAYER_LEFT_CLICK_AIR,
        PLAYER_KILLED,
        PLAYER_DAMAGED
    }

    public static final TextColor shadowless = TextColor.color(78, 92, 36);

    public static void openGUI(Player player, DefaultGUI gui){
        StrongholdsAndEnderdragons.plugin.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
        player.openInventory(gui.getInventory());
    }

    public static String convertWidthToMinecraftCode(int width){

        int code = 0xD0000 + width;
        int highSurrogate = 0xD800 + ((code - 0x10000) >> 10);
        int lowSurrogate = 0xDC00 + ((code - 0x10000) & 0x3FF);

        return new String(new int[]{highSurrogate, lowSurrogate}, 0, 2);
    }

    public static void registerGUI(DefaultGUI gui){
        Bukkit.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
    }

}
