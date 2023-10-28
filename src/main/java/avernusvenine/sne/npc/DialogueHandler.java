package avernusvenine.sne.npc;

import avernusvenine.sne.Globals;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.gui.ItemRetrievalCompletionGUI;
import avernusvenine.sne.gui.QuestPromptGUI;
import avernusvenine.sne.gui.QuestRewardGUI;
import avernusvenine.sne.players.PlayerCharacter;
import avernusvenine.sne.quests.ItemRetrievalQuest;
import avernusvenine.sne.quests.Quest;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.ArrayList;
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
    private int iterator;

    private boolean textScrolling = false;

    private DialogueSet set;
    private DialogueTask dialogueTask;

    public DialogueHandler(){
        phase = Phase.GREETING;
    }

    public void advance(Player player){

        String[] dialogue = new String[4];

        PlayerCharacter playerCharacter = PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter();

        if(textScrolling){
            skipDialogue(player);
            return;
        }

        switch(phase){
            case GREETING:
                List<String[]> greeting = set.getGreeting(player);

                if(iterator < greeting.size()){
                    dialogue = greeting.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                if(set.getQuests().isEmpty())
                    phase = Phase.CLOSE;

                for(Quest quest : set.getQuests()){
                    if(playerCharacter.getQuestStatus(quest.getID()) != PlayerCharacter.QuestStatus.Status.COMPLETED
                            && playerCharacter.checkQuestCompletion(quest.getQuestPrerequisites())){
                        currentQuest = quest;
                        phase = Phase.QUEST_PROMPT;
                        break;
                    }
                }

                if(phase == Phase.GREETING)
                    phase = Phase.CLOSE;

                advance(player);
                return;
            case CLOSE:
                close(player);
                return;
            case QUEST_PROMPT:
                List<String[]> prompt = set.getQuestDialogue(currentQuest).prompt;

                if(iterator < prompt.size()){
                    dialogue = prompt.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                if(playerCharacter.getQuestStatus(currentQuest.getID()) == PlayerCharacter.QuestStatus.Status.ACCEPTED){
                    phase = Phase.QUEST_COMPLETION_GUI;
                    advance(player);
                    return;
                }

                phase = Phase.QUEST_PROMPT_GUI;
                advance(player);
                return;
            case QUEST_PROMPT_GUI:
                promptQuest(player);
                return;
            case QUEST_ACCEPT:
                List<String[]> accept = set.getQuestDialogue(currentQuest).accept;

                if(iterator < accept.size()){
                    dialogue = accept.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                phase = Phase.CLOSE;
                advance(player);
                return;
            case QUEST_DENY:
                List<String[]> deny = set.getQuestDialogue(currentQuest).deny;

                if(iterator < deny.size()){
                    dialogue = deny.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                phase = Phase.CLOSE;
                advance(player);
                return;
            case QUEST_COMPLETION:
                List<String[]> completion = set.getQuestDialogue(currentQuest).completion;

                if(iterator < completion.size()){
                    dialogue = completion.get(iterator);
                    iterator++;
                    break;
                }

                iterator = 0;

                phase = Phase.QUEST_REWARD_GUI;

                playerCharacter.updateQuestStatus(currentQuest.getID(), PlayerCharacter.QuestStatus.Status.COMPLETED);
                advance(player);
                return;
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

    public void displayToPlayer(Player player, String[] dialogue){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 3));
        textScrolling = true;

        dialogueTask = new DialogueTask(dialogue, player);
    }

    public void skipDialogue(Player player){
        textScrolling = false;

        dialogueTask.skipDialogue(player);
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
        PlayerDictionary.get(player.getUniqueId().toString()).closeDialogue();
        player.clearTitle();
        player.removePotionEffect(PotionEffectType.SLOW);
    }

    public void reset(){
        iterator = 0;
        set = null;
        phase = Phase.GREETING;
    }


    public void setDialogueSet(DialogueSet set){
        this.set = set;
    }

    public String getCurrentQuestID(){
        return currentQuest.getID();
    }

    private class DialogueTask{

        private int iterator;
        private int line;
        private final TextColor textColor = TextColor.color(130, 96, 69);
        private final int initialOffset = 270;

        private final String[] dialogue;

        private final BukkitTask task;

        public DialogueTask(String[] dialogue, Player player){
            iterator = 0;
            line = 0;
            this.dialogue = dialogue;

            int[] offset = new int[4];

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {

                    if(line == 4){
                        task.cancel();
                        textScrolling = false;
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

}
