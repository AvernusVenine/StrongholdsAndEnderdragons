package avernusvenine.sne.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class ResetCooldowns implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("resetcooldown")){
            player.setCooldown(player.getInventory().getItemInMainHand().getType(), 0);
        }


        return true;
    }
}
