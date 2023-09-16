package avernusvenine.sne;

import avernusvenine.sne.commands.GiveCustomItem;
import avernusvenine.sne.items.*;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Dictionary;
import java.util.Hashtable;

public final class StrongholdsAndEnderdragons extends JavaPlugin {

    // Dictionary of all custom items
    public static BiMap<String, ItemStack> customItemDictionary = HashBiMap.create();

    @Override
    public void onEnable() {

        registerCommand("givecustomitem", new GiveCustomItem());
        loadItems();

    }


    // Don't forget to put new custom items in here, so they can be spawned in
    public void loadItems(){
        CustomItem.init();
        customItemDictionary.put(CustomItem.getID(), CustomItem.getCustomItem());
        Mjolnir.init();
        customItemDictionary.put(Mjolnir.getID(), Mjolnir.getCustomItem());
    }

    public void registerCommand(String name, CommandExecutor executor){
        PluginCommand command = getCommand(name);

        if(command != null){
            command.setExecutor(executor);

        }

    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
