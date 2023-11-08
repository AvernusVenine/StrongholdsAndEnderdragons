package avernusvenine.sne.commands;

import avernusvenine.sne.ItemDictionary;
import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.SneNPC;
import avernusvenine.sne.professions.Profession.ProfessionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class UnlockRecipe implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(!(sender instanceof Player player))
            return true;

        if(command.getName().equalsIgnoreCase("unlockrecipe")){

            ItemStack item = ItemDictionary.get(args[0]).getItem();

            if(item != null){
                PlayerDictionary.get(player).getPlayerCharacter().unlockRecipe(item, ProfessionType.fromID(Integer.parseInt(args[1])));
            }
        }

        return true;
    }
}
