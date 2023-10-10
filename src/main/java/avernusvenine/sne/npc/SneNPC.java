package avernusvenine.sne.npc;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import net.citizensnpcs.api.CitizensAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.K;

import java.time.Duration;
import java.util.UUID;

public class SneNPC {

    protected net.citizensnpcs.api.npc.NPC npc;

    protected String id;
    protected String name;

    public SneNPC(){
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Default NPC");
        id = "default_npc";
    }

    public void spawnNPC(Location location){
        npc.spawn(location);
    }


    public void advanceDialogue(Player player){

    }

    public void showDialogue(Player player, String[] dialogue){
        PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 3));

        // Max characters per line currently ~35
        dialogue = new String[4];
        dialogue[0] = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        dialogue[1] = "BBBBBBBBBBBBBBBBBBBBBB";
        dialogue[2] = "";
        dialogue[3] = "";

        new DialogueTask(dialogue, player);
    }

    public void closeDialogue(Player player){
        player.clearTitle();
        PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(false);
        player.removePotionEffect(PotionEffectType.SLOW);
        PlayerDictionary.get(player.getUniqueId().toString()).setDialoguePhase(0);
    }

    public static String convertWidthToMinecraftCode(int width){

        int code = 0xD0000 + width;
        int highSurrogate = 0xD800 + ((code - 0x10000) >> 10);
        int lowSurrogate = 0xDC00 + ((code - 0x10000) & 0x3FF);

        return new String(new int[]{highSurrogate, lowSurrogate}, 0, 2);
    }


    //Getters and setters
    public String getID(){
        return id;
    }

    public String getUUID(){
        return npc.getUniqueId().toString();
    }

    public class DialogueTask{

        private int iterator;
        private int line;

        private BukkitTask task;

        public DialogueTask(String[] dialogue, Player player){
            iterator = 0;
            line = 0;

            int offset = 280;
            TextColor color = TextColor.color(0, 0, 0);

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    if(line == 4)
                        //TODO: Add another task here that keeps the action bar up until the player right clicks again
                        task.cancel();
                    else {
                        Component component = Component.text("");

                        if(line == 0){

                            if(iterator >= dialogue[0].length()){
                                iterator = 0;
                                line = 1;
                                return;
                            }

                            String sub = dialogue[0].substring(0, iterator);
                            int spacer = offset-(6*iterator);

                            component = Component.text(sub).font(Key.key("sne:dialogue_one")).color(TextColor.color(0, 0, 0))
                                    .append(Component.text(convertWidthToMinecraftCode(spacer)).font(Key.key("space:default")));
                        }
                        else if(line == 1){

                            if(iterator >= dialogue[1].length()){
                                iterator = 0;
                                line = 2;
                                return;
                            }

                            String sub = dialogue[1].substring(0, iterator);

                            component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one"))
                                    .append(Component.text(convertWidthToMinecraftCode(-6*dialogue[0].length())).font(Key.key("space:default")))
                                    .append(Component.text(sub).font(Key.key("sne:dialogue_two")))
                                    .append(Component.text(convertWidthToMinecraftCode(offset-6*iterator)).font(Key.key("space:default")));

                        }
                        else if(line == 2){

                            if(iterator >= dialogue[2].length()){
                                iterator = 0;
                                line = 3;
                                return;
                            }

                            String sub = dialogue[2].substring(0, iterator);

                            component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one"))
                                    .append(Component.text(convertWidthToMinecraftCode(-6*dialogue[0].length())).font(Key.key("space:default")))
                                    .append(Component.text(dialogue[1]).font(Key.key("sne:dialogue_two")))
                                    .append(Component.text(convertWidthToMinecraftCode(-6*dialogue[1].length())).font(Key.key("space:default")))
                                    .append(Component.text(sub).font(Key.key("sne:dialogue_three")))
                                    .append(Component.text(convertWidthToMinecraftCode(offset-6*iterator)).font(Key.key("space:default")));
                        }
                        else if(line == 3){

                            if(iterator >= dialogue[3].length()){
                                iterator = 0;
                                line = 4;
                                return;
                            }

                            String sub = dialogue[3].substring(0, iterator);

                            component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one"))
                                    .append(Component.text(convertWidthToMinecraftCode(-6*dialogue[0].length())).font(Key.key("space:default")))
                                    .append(Component.text(dialogue[1]).font(Key.key("sne:dialogue_two")))
                                    .append(Component.text(convertWidthToMinecraftCode(-6*dialogue[1].length())).font(Key.key("space:default")))
                                    .append(Component.text(dialogue[2]).font(Key.key("sne:dialogue_three")))
                                    .append(Component.text(convertWidthToMinecraftCode(-6*dialogue[2].length())).font(Key.key("space:default")))
                                    .append(Component.text(sub).font(Key.key("sne:dialogue_four")))
                                    .append(Component.text(convertWidthToMinecraftCode(offset-6*iterator)).font(Key.key("space:default")));

                            line = 4;
                        }



                        /*if(iterator <= dialogue[0].length()){
                            String sub = dialogue[0].substring(0, iterator);

                            int spacerOne = 280 - (6*iterator);

                            component = Component.text(sub).font(Key.key("sne:dialogue_one")).color(TextColor.color(0, 0, 0))
                                    .append(Component.text(convertWidthToMinecraftCode(spacerOne)).font(Key.key("space:default")));
                        }
                        if(iterator > dialogue[0].length() && iterator <= dialogue[0].length() + dialogue[1].length()) {
                            String sub = dialogue[1].substring(0, iterator - dialogue[0].length());

                            int spacerOne = 280 - (6 * (dialogue[0].length() - (iterator - dialogue[0].length())));
                            int spacerTwo = 280 - (6 * ((iterator - dialogue[0].length()) + dialogue[0].length()));

                            component = Component.text(dialogue[0]).font(Key.key("sne:dialogue_one")).color(TextColor.color(0, 0, 0))
                                    .append(Component.text(convertWidthToMinecraftCode(spacerOne)).font(Key.key("space:default")))
                                    .append(Component.text(sub).font(Key.key("sne:dialogue_two")).color(TextColor.color(0, 0, 0)))
                                    .append(Component.text(convertWidthToMinecraftCode(spacerTwo)).font(Key.key("space:default")));
                        }*/


                        Component empty = Component.text("");
                        Component speechBubble = Component.text("\ue239").font(Key.key("minecraft:default"));

                        player.sendActionBar(speechBubble);
                        player.showTitle(net.kyori.adventure.title.Title.title(empty, component, Title.Times.times(Duration.ZERO, Duration.ofMinutes(999999), Duration.ZERO)));

                        iterator++;
                    }
                }
            }, 0, 1);

        }

    }

}
