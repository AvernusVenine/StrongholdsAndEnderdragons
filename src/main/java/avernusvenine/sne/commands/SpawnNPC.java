package avernusvenine.sne.commands;

import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.npc.SneNPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnNPC implements CommandExecutor {

    @Override

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("spawnnpc")){

            SneNPC npc = NPCDictionary.get(args[0]);

            if(npc != null){
                npc.spawnNPC(player.getLocation());
            }
        }

        return true;
    }

}
