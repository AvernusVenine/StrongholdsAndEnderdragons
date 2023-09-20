package avernusvenine.sne.commands;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class GetRank implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        try{
            if(command.getName().equalsIgnoreCase("getrank")){
                if(!StrongholdsAndEnderdragons.databaseHandler.playerExists(args[0])){
                    sender.sendMessage("Player not found!");
                    return true;
                }
                sender.sendMessage(StrongholdsAndEnderdragons.databaseHandler.getPlayerRank(args[0]));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return true;
    }

}