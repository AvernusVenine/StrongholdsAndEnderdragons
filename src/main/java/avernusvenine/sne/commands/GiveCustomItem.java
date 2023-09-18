package avernusvenine.sne.commands;

import avernusvenine.sne.StrongholdsAndEnderdragons;


import avernusvenine.sne.items.CustomItem;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

public class GiveCustomItem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        // Checks to see that the sender isn't a console
        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("givecustomitem")){

            CustomItem item = StrongholdsAndEnderdragons.customItemDictionary.get(args[0]);

            // Happens if the item is not found
            if(item == null){
                sender.sendMessage("Item not found!");
                return true;
            }

            player.getInventory().addItem(item.getCustomItem());
        }

        return true;
    }

}
