package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.quests.Quest;

import java.util.ArrayList;
import java.util.List;

public class QuestDialogueSet extends DialogueSet{

    protected List<Quest> quests = new ArrayList<>();

    public QuestDialogueSet(String id, DialogueType type) {
        super(id, type);
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
