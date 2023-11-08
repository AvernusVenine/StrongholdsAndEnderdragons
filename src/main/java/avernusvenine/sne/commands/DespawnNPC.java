package avernusvenine.sne.commands;

import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.npc.SneNPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DespawnNPC implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(!(sender instanceof Player))
            return true;

        if(command.getName().equalsIgnoreCase("despawnnpc")){

            SneNPC npc = NPCDictionary.get(args[0]);

            if(npc != null){
                npc.despawnNPC();
            }
        }

        return true;
    }

}