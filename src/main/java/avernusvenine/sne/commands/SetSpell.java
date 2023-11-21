package avernusvenine.sne.commands;

import avernusvenine.sne.items.interactable.castable.Castable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SetSpell implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if(!(sender instanceof Player player))
            return true;

        if(command.getName().equalsIgnoreCase("setspell")){
            ItemStack item = player.getInventory().getItemInMainHand();

            Castable.setSpell(args[0], item);
        }


        return true;
    }

}
