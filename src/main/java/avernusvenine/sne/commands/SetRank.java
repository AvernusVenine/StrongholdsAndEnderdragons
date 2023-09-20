package avernusvenine.sne.commands;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class SetRank implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        try{
            if(command.getName().equalsIgnoreCase("setrank")){

                if(!StrongholdsAndEnderdragons.databaseHandler.playerExists(args[0])){
                    sender.sendMessage("Player not found!");
                    return true;
                }
                StrongholdsAndEnderdragons.databaseHandler.setPlayerRank(args[0], args[1]);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return true;
    }

}
