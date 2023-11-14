package avernusvenine.sne.commands;

import avernusvenine.sne.PlayerDictionary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetResource implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(command.getName().equalsIgnoreCase("setresource")){
            Player player = (Player) sender;

            PlayerDictionary.get(player).setResource(Integer.parseInt(args[0]));
        }

        return true;
    }


}
