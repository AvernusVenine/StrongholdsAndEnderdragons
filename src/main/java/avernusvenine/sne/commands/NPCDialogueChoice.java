package avernusvenine.sne.commands;

import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.SneNPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NPCDialogueChoice implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(!(sender instanceof Player player))
            return true;

        if(command.getName().equalsIgnoreCase("NPCDialogueChoice")){

            if(!PlayerDictionary.get(player).getPlayerDialogueHandler().isInDialogue())
                return true;

            PlayerDictionary.get(player).getPlayerDialogueHandler().next(Integer.parseInt(args[0]));
            PlayerDictionary.get(player).getPlayerDialogueHandler().setChoosing(false);
        }

        return true;
    }
}
