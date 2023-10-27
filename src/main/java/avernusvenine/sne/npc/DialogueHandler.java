package avernusvenine.sne.npc;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.gui.ItemRetrievalCompletionGUI;
import avernusvenine.sne.gui.QuestPromptGUI;
import avernusvenine.sne.gui.QuestRewardGUI;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogueHandler {


    public enum Phase {
        GREETING,
        QUEST_PROMPT,
        QUEST_PROMPT_GUI,
        SHOP_GUI,
        RELATIONSHIP,
        QUEST_DENY,
        QUEST_ACCEPT,
        QUEST_COMPLETION,
        QUEST_COMPLETION_GUI,
        QUEST_REWARD_GUI,
        CLOSE
    }

    private Phase phase;
    private Quest currentQuest;

    private String id;
    private List<RelationshipDialogue> greeting = new ArrayList<>();

    private List<Quest> quests = new ArrayList<>();
    private HashMap<Quest, QuestDialogue> questDialogue = new HashMap<>();

    public DialogueHandler(String id){
        phase = Phase.GREETING;
        this.id = id;
    }

    public void advance(Player player){

        String[] dialogue = new String[4];

        PlayerCharacter playerCharacter = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter();

        switch(phase){
            case GREETING:

                RelationshipDialogue currentGreeting = new RelationshipDialogue(0, 0, new ArrayList<>());

                for(RelationshipDialogue relationship : greeting){
                    if(relationship.inRange(playerCharacter.getRelationship(id)))
                        currentGreeting = relationship;
                }

                dialogue = currentGreeting.dialogue.get(0);
                currentGreeting.dialogue.remove(0);

                if(!currentGreeting.dialogue.isEmpty())
                    break;

                if(quests.isEmpty())
                    phase = Phase.CLOSE;

                for(Quest quest : quests){
                    if(playerCharacter.getQuestStatus(quest.getID()) != PlayerCharacter.QuestStatus.Status.COMPLETED
                            && playerCharacter.checkQuestCompletion(quest.getQuestPrerequisites())){
                        currentQuest = quest;
                        phase = Phase.QUEST_PROMPT;
                        break;
                    }
                }
                break;
            case CLOSE:
                PlayerDictionary.get(player.getUniqueId().toString()).removeDialogueHandler();
                close(player);
                return;
            case QUEST_PROMPT:
                dialogue = questDialogue.get(currentQuest).prompt.get(0);
                questDialogue.get(currentQuest).prompt.remove(0);

                if(!questDialogue.get(currentQuest).prompt.isEmpty())
                    break;

                if(playerCharacter.getQuestStatus(currentQuest.getID()) == PlayerCharacter.QuestStatus.Status.ACCEPTED){
                    phase = Phase.QUEST_COMPLETION_GUI;
                    break;
                }

                phase = Phase.QUEST_PROMPT_GUI;
                break;
            case QUEST_PROMPT_GUI:
                promptQuest(player);
                return;
            case QUEST_ACCEPT:
                dialogue = questDialogue.get(currentQuest).accept.get(0);

                questDialogue.get(currentQuest).accept.remove(0);

                if(!questDialogue.get(currentQuest).accept.isEmpty())
                    break;

                phase = Phase.CLOSE;
                break;
            case QUEST_DENY:
                dialogue = questDialogue.get(currentQuest).deny.get(0);

                questDialogue.get(currentQuest).deny.remove(0);

                if(!questDialogue.get(currentQuest).deny.isEmpty())
                    break;

                phase = Phase.CLOSE;
                break;
            case QUEST_COMPLETION:
                dialogue = questDialogue.get(currentQuest).completion.get(0);

                questDialogue.get(currentQuest).completion.remove(0);

                if(!questDialogue.get(currentQuest).completion.isEmpty())
                    break;

                phase = Phase.QUEST_REWARD_GUI;
                break;
            case QUEST_COMPLETION_GUI:
                promptQuestCompletion(player);
                return;
            case QUEST_REWARD_GUI:
                rewardPlayer(player);
                close(player);
                return;
        }

        displayToPlayer(player, dialogue);
    }

    public void advance(Player player, Phase phase){
        this.phase = phase;
        advance(player);
    }

    public void addGreeting(int min, int max, List<String[]> text){
        greeting.add(new RelationshipDialogue(min, max, text));
    }

    public void addGreeting(int min, int max, String[] text){
        List<String[]> temp =  new ArrayList<>();
        temp.add(text);
        greeting.add(new RelationshipDialogue(min, max, temp));
    }

    public void addQuestPrompt(Quest quest, String[] text){
        questDialogue.get(quest).prompt.add(text);
    }

    public void addQuestAccept(Quest quest, String[] text){
        questDialogue.get(quest).accept.add(text);
    }

    public void addQuestDeny(Quest quest, String[] text){
        questDialogue.get(quest).deny.add(text);
    }

    public void addQuestCompletion(Quest quest, String[] text){
        questDialogue.get(quest).completion.add(text);
    }

    public void addQuest(Quest quest){
        quests.add(quest);
        questDialogue.put(quest, new QuestDialogue());
    }

    // Getters and Setters

    public String getCurrentQuestID(){
        return currentQuest.getID();
    }

    public void displayToPlayer(Player player, String[] dialogue){
        PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 3));

        new DialogueTask(dialogue, player);
    }

    public void promptQuest(Player player){
        QuestPromptGUI gui = new QuestPromptGUI();
        player.openInventory(gui.getInventory());
        Bukkit.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
    }

    public void promptQuestCompletion(Player player){
        switch(currentQuest.getType()){
            case ITEM_RETRIEVAL:
                ItemRetrievalCompletionGUI gui = new ItemRetrievalCompletionGUI(player, (ItemRetrievalQuest) currentQuest);
                player.openInventory(gui.getInventory());
                Bukkit.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
                break;
        }
    }

    public void rewardPlayer(Player player){
        QuestRewardGUI gui = new QuestRewardGUI(player, currentQuest);
        player.openInventory(gui.getInventory());
        Bukkit.getServer().getPluginManager().registerEvents(gui, StrongholdsAndEnderdragons.plugin);
    }

    public static void close(Player player){
        PlayerDictionary.get(player.getUniqueId().toString()).setInDialogue(false);
        player.removePotionEffect(PotionEffectType.SLOW);
    }

    private class DialogueTask{

        private int iterator;
        private int line;

        private final BukkitTask task;

        public DialogueTask(String[] dialogue, Player player){
            iterator = 0;
            line = 0;

            int initialOffset = 270;
            TextColor textColor = TextColor.color(130, 96, 69);

            int[] offset = new int[4];

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {

                    if(line == 4)
                        task.cancel();
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
                                    .append(Component.text(Globals.convertWidthToMinecraftCode(-offset[0])).font(Key.key("space:default")))
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

        private int getOffset(char c){

            switch(c){
                default:
                    return 6;
                case '!', '\'', ',', '.', ':', ';', 'i', '|':
                    return 2;
                case '`', 'l':
                    return 3;
                case '"', '(', ')', '*', 'I', '[', ']', 't', '{', '}', ' ':
                    return 4;
                case 'f':
                    return 5;
                case '@':
                    return 7;
            }
        }

    }

    private class QuestDialogue{

        List<String[]> prompt = new ArrayList<>();
        List<String[]> deny = new ArrayList<>();
        List<String[]> accept = new ArrayList<>();
        List<String[]> completion = new ArrayList<>();

    }

    private class RelationshipDialogue{

        int min, max;
        List<String[]> dialogue;

        public RelationshipDialogue(int min, int max, List<String[]> dialogue){
            this.max = max;
            this.min = min;
            this.dialogue = dialogue;
        }

        public boolean inRange(float i){
            return (min <= i && i <= max);
        }

    }
}
