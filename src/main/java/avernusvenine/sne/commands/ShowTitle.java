package avernusvenine.sne.commands;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.Duration;

public class ShowTitle implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(command.getName().equalsIgnoreCase("showtitle")){
            Player player = (Player) sender;

            Component comp = Component.text(args[0]).font(Key.key("sne:dialogue_one"));
            comp = comp.append(Component.text(convertWidthToMinecraftCode(Integer.valueOf(args[1]))).font(Key.key("space:default")));

            if(args.length < 3){
                player.showTitle(Title.title(Component.empty(), comp, Title.Times.times(Duration.ZERO, Duration.ofSeconds(5), Duration.ZERO)));
                return true;
            }

            comp = comp.append(Component.text(args[2]).font(Key.key("sne:dialogue_two")));
            comp = comp.append(Component.text(convertWidthToMinecraftCode(Integer.valueOf(args[3]))).font(Key.key("space:default")));

            player.showTitle(Title.title(Component.empty(), comp, Title.Times.times(Duration.ZERO, Duration.ofSeconds(5), Duration.ZERO)));

        }

        return true;
    }

    public static String convertWidthToMinecraftCode(int width){

        int code = 0xD0000 + width;
        int highSurrogate = 0xD800 + ((code - 0x10000) >> 10);
        int lowSurrogate = 0xDC00 + ((code - 0x10000) & 0x3FF);

        return new String(new int[]{highSurrogate, lowSurrogate}, 0, 2);
    }


}
