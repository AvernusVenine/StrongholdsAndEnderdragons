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
import java.util.HashMap;
import java.util.List;

public class DialogueSet implements Cloneable{


    private String id;
    private List<RelationshipDialogue> greeting = new ArrayList<>();

    private List<Quest> quests = new ArrayList<>();
    private HashMap<Quest, QuestDialogue> questDialogue = new HashMap<>();

    public DialogueSet(String id) {
        this.id = id;
    }

    public List<String[]> getGreeting(Player player){
        for(RelationshipDialogue dialogue : greeting){
            if(dialogue.inRange(PlayerDictionary.get(player.getUniqueId().toString()).getPlayerCharacter().getRelationship(id)))
                return dialogue.dialogue;
        }

        return new ArrayList<>();
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

    public List<Quest> getQuests(){
        return quests;
    }

    public QuestDialogue getQuestDialogue(Quest quest){
        return questDialogue.get(quest);
    }


    public class QuestDialogue{

        public List<String[]> prompt = new ArrayList<>();
        public List<String[]> deny = new ArrayList<>();
        public List<String[]> accept = new ArrayList<>();
        public List<String[]> completion = new ArrayList<>();

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
