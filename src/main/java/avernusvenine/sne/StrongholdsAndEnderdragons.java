package avernusvenine.sne;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Permission;

public final class StrongholdsAndEnderdragons extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }


    public void registerCommand(String name, CommandExecutor executor, Permission permission){
        PluginCommand command = getCommand(name);

        if(command != null){
            command.setExecutor(executor);
            command.setPermission(permission.toString());

        }

    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
