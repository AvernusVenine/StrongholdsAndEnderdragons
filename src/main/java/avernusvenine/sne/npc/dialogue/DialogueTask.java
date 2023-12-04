package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.players.PlayerDialogueHandler;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;

public class DialogueTask {

    private final TextColor textColor = TextColor.color(130, 96, 69);
    private final int initialOffset = 270;
    private final String[] dialogue;
    private final boolean advance;

    private int iterator;
    private int line;

    private final BukkitTask task;

    public DialogueTask(String[] dialogue, Player player, boolean advance){
        iterator = 0;
        line = 0;
        this.dialogue = dialogue;
        this.advance = advance;

        int[] offset = new int[4];

        task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
            @Override
            public void run() {

                if(line == 4){
                    task.cancel();
                    PlayerDialogueHandler handler = PlayerDictionary.get(player).getPlayerDialogueHandler();
                    handler.setTextScrolling(false);

                    if(advance)
                        handler.next();

                }
                else {
                    Component component = Component.text("");

                    if(line == 0){

                        if(dialogue[0].isEmpty() || iterator >= dialogue[0].length()){
                            iterator = 0;
                            line = 1;
                            return;
                        }

                        String sub = dialogue[0].substring(0, iterator+1);
                        offset[0] += getOffset(sub.charAt(iterator));

                        component = Component.text(sub).font(Key.key("sne:dialogue_one")).color(textColor)
                                .append(Component.text(Globals.convertWidthToMinecraftCode(initialOffset-offset[0])).font(Key.key("space:default")));
                    }
                    else if(line == 1){

                        if(dialogue[1].isEmpty() || iterator >= dialogue[1].length()){
                            iterator = 0;
                            line = 2;
                            return;
                        }

                        String sub = dialogue[1].substring(0, iterator+1);
                        offset[1] += getOffset(sub.charAt(iterator));

                        component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one")).color(textColor)
                                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[0])).font(Key.key("space:default")))
                                .append(Component.text(sub).font(Key.key("sne:dialogue_two")).color(textColor))
                                .append(Component.text(Globals.convertWidthToMinecraftCode(initialOffset-offset[1])).font(Key.key("space:default")));

                    }
                    else if(line == 2){

                        if(dialogue[2].isEmpty() || iterator >= dialogue[2].length()){
                            iterator = 0;
                            line = 3;
                            return;
                        }

                        String sub = dialogue[2].substring(0, iterator+1);
                        offset[2] = getOffset(sub.charAt(iterator));

                        component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one")).color(textColor)
                                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[0])).font(Key.key("space:default")))
                                .append(Component.text(dialogue[1]).font(Key.key("sne:dialogue_two")).color(textColor))
                                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[1])).font(Key.key("space:default")))
                                .append(Component.text(sub).font(Key.key("sne:dialogue_three")).color(textColor))
                                .append(Component.text(Globals.convertWidthToMinecraftCode(initialOffset-offset[2])).font(Key.key("space:default")));
                    }
                    else if(line == 3){

                        if(dialogue[3].isEmpty() || iterator >= dialogue[3].length()){
                            iterator = 0;
                            line = 4;
                            return;
                        }

                        String sub = dialogue[3].substring(0, iterator+1);
                        offset[3] = getOffset(sub.charAt(iterator));

                        component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one")).color(textColor)
                                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[0])).font(Key.key("space:default")))
                                .append(Component.text(dialogue[1]).font(Key.key("sne:dialogue_two")).color(textColor))
                                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[1])).font(Key.key("space:default")))
                                .append(Component.text(dialogue[2]).font(Key.key("sne:dialogue_three")).color(textColor))
                                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[2])).font(Key.key("space:default")))
                                .append(Component.text(sub).font(Key.key("sne:dialogue_four")).color(textColor))
                                .append(Component.text(Globals.convertWidthToMinecraftCode(initialOffset-offset[3])).font(Key.key("space:default")));

                        line = 4;
                    }

                    Component empty = Component.text("");
                    player.showTitle(net.kyori.adventure.title.Title.title(empty, component, Title.Times.times(Duration.ZERO, Duration.ofMinutes(999999), Duration.ZERO)));

                    iterator++;
                }
            }
        }, 0, 1);

    }

    public void skipDialogue(Player player){
        task.cancel();

        int[] offset = new int[4];

        for(int i = 0; i < 4; i++){
            for(char c : dialogue[i].toCharArray()){
                offset[i] += getOffset(c);
            }
        }

        Component component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one")).color(textColor)
                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[0])).font(Key.key("space:default")))
                .append(Component.text(dialogue[1]).font(Key.key("sne:dialogue_two")).color(textColor))
                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[1])).font(Key.key("space:default")))
                .append(Component.text(dialogue[2]).font(Key.key("sne:dialogue_three")).color(textColor))
                .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[2])).font(Key.key("space:default")))
                .append(Component.text(dialogue[3]).font(Key.key("sne:dialogue_four")).color(textColor))
                .append(Component.text(Globals.convertWidthToMinecraftCode(initialOffset-offset[3])).font(Key.key("space:default")));

        Component empty = Component.text("");
        player.showTitle(net.kyori.adventure.title.Title.title(empty, component, Title.Times.times(Duration.ZERO, Duration.ofMinutes(999999), Duration.ZERO)));

        PlayerDialogueHandler handler = PlayerDictionary.get(player).getPlayerDialogueHandler();
        handler.setTextScrolling(false);

        if(advance)
            handler.next();
    }

    private static int getOffset(char c){
        return switch (c) {
            default -> 6;
            case '!', '\'', ',', '.', ':', ';', 'i', '|' -> 2;
            case '`', 'l' -> 3;
            case '"', '(', ')', '*', 'I', '[', ']', 't', '{', '}', ' ' -> 4;
            case 'f' -> 5;
            case '@' -> 7;
        };
    }

}