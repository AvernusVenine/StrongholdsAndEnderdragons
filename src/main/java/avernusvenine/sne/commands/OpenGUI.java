package avernusvenine.sne.commands;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class OpenGUI implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("opengui")){

            DefaultGUI gui = StrongholdsAndEnderdragons.guiDictionary.get(args[0]);

            if(gui == null){
                sender.sendMessage("GUI not found!");
                return true;
            }

            player.openInventory(gui.getInventory());
        }


        return true;
    }

}
