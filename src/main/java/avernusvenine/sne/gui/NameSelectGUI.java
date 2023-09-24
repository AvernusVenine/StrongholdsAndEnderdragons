package avernusvenine.sne.gui;

import avernusvenine.sne.StrongholdsAndEnderdragons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class NameSelectGUI extends DefaultGUI implements Listener {

    private Player player;

    public NameSelectGUI(Player player){
        id = "name_select";
        title = "Name Selection";
        this.player = player;

        initializeAnvil();
    }

    public void initializeAnvil(){
        ItemStack rightItem = new ItemStack(Material.PAPER, 1);
        ItemMeta rightMeta = rightItem.getItemMeta();
        rightMeta.setDisplayName(ChatColor.BOLD + "ENTER CHARACTER NAME");
        rightItem.setItemMeta(rightMeta);

        ItemStack leftItem = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta leftMeta = leftItem.getItemMeta();
        leftMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "BACK");
        leftItem.setItemMeta(leftMeta);

        new AnvilGUI.Builder().title(title)
                .interactableSlots()
                .itemLeft(leftItem)
                .itemOutput(rightItem)
                .text("ENTER CHARACTER NAME")
                .plugin(StrongholdsAndEnderdragons.plugin)
                .onClick((slot, stateSnapshot) -> {
                    if(slot == AnvilGUI.Slot.INPUT_LEFT) {
                        player.openInventory(StrongholdsAndEnderdragons.guiDictionary.get("race_select").getInventory());
                        player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }

                    if(slot == AnvilGUI.Slot.OUTPUT) {
                        StrongholdsAndEnderdragons.playerCharacters.get(player.getUniqueId().toString()).setName(stateSnapshot.getText());
                        player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 1, 1);

                        StrongholdsAndEnderdragons.playerCharacters.get(player.getUniqueId().toString()).createInDatabase();

                        CharacterSelectGUI gui = new CharacterSelectGUI(player);
                        StrongholdsAndEnderdragons.plugin.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);

                        player.openInventory(gui.getInventory());

                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }

                    return Collections.emptyList();
                })
                .open(player);
    }
}
