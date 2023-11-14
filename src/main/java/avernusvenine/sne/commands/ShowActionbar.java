package avernusvenine.sne.commands;

import avernusvenine.sne.Globals;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class ShowActionbar implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(command.getName().equalsIgnoreCase("showactionbar")){
            Player player = (Player) sender;

            Component comp = Component.text("\ue240\ue240\ue240\ue240\ue240\ue240\ue240\ue240\ue241\ue241").font(Key.key("sne:images"))
                    .append(Component.text(Globals.convertWidthToMinecraftCode(Integer.parseInt(args[0]))).font(Key.key("space:default")));

            player.sendActionBar(comp);
        }

        return true;
    }

}
