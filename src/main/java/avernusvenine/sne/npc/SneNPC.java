package avernusvenine.sne.npc;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import net.citizensnpcs.api.CitizensAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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

    public void showDialogue(Player player, String dialogue){
        PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 3));

        // Max characters per line currently ~35
        dialogue = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBB";

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

        private BukkitTask task;

        public DialogueTask(String dialogue, Player player){

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    if(iterator > dialogue.length())
                        //TODO: Add another task here that keeps the action bar up until the player right clicks again
                        task.cancel();
                    else {
                        Component component = Component.text("");

                        if(iterator <= 45){
                            String sub = dialogue.substring(0, iterator);
                            component = Component.text(sub).font(Key.key("sne:dialogue_one"))
                                    .append(Component.text(convertWidthToMinecraftCode(-160+iterator*8)));
                            /*component = Component.text(convertWidthToMinecraftCode(-290+iterator*6)).font(Key.key("space:default"))
                                    .append(Component.text("\ue239").font(Key.key("minecraft:default")))
                                    .append(Component.text(convertWidthToMinecraftCode(-290)).font(Key.key("space:default")))
                                    .append(Component.text(sub).font(Key.key("sne:dialogue_one")));*/
                        }
                        if(iterator > 45 && iterator <= 90){
                            String subOne = dialogue.substring(0, 45);
                            String subTwo = dialogue.substring(46, iterator);

                            component = Component.text(subOne).font(Key.key("sne:dialogue_one"))
                                    .append(Component.text(convertWidthToMinecraftCode(-300)).font(Key.key("space:default")))
                                    .append(Component.text(subTwo).font(Key.key("sne:dialogue_two")));

                            /*component = Component.text(convertWidthToMinecraftCode(-500+iterator*6)).font(Key.key("space:default"))
                                    .append(Component.text("\ue239").font(Key.key("minecraft:default")))
                                    .append(Component.text(convertWidthToMinecraftCode(-300)).font(Key.key("space:default")))
                                    .append(Component.text(subOne).font(Key.key("sne:dialogue_one")))
                                    .append(Component.text(convertWidthToMinecraftCode(-200)).font(Key.key("space:default")))
                                    .append(Component.text(subTwo).font(Key.key("sne:dialogue_two")));*/

                        }
                        if(iterator > 90){
                            String subOne = dialogue.substring(0, 35);
                            String subTwo = dialogue.substring(36, 70);
                            String subThree = dialogue.substring(71, iterator);

                            component = Component.text(subOne).font(Key.key("sne:dialogue_one"))
                                    .append(Component.text(convertWidthToMinecraftCode(-35*7)).font(Key.key("space:default")))
                                    .append(Component.text(subTwo).font(Key.key("sne:dialogue_two")))
                                    .append(Component.text(convertWidthToMinecraftCode((-iterator/2)*7)).font(Key.key("space:default")))
                                    .append(Component.text(subThree).font(Key.key("sne:dialogue_three")));
                        }

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
