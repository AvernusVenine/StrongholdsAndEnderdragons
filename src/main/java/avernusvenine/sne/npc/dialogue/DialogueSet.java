package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.PlayerDictionary;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DialogueSet implements Cloneable{

    public enum DialogueType{
        QUEST,
        TRAINER,
        SHOP,
        DEFAULT
    }


    protected String id;
    protected DialogueType type;

    protected List<RelationshipDialogue> greeting = new ArrayList<>();

    public DialogueSet(String id) {
        this.id = id;
        type = DialogueType.DEFAULT;
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

    // Getters and Setters

    public DialogueType getType(){
        return type;
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
