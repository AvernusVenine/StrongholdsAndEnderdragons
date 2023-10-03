package avernusvenine.sne.npc;

import java.util.HashMap;

public class DialogueSet {

    public enum DialogueType{
        GREETING,
        GOODBYE,
        QUEST,
        QUEST_COMPLETE
    }

    protected HashMap<DialogueType, String> dialogue;

    public DialogueSet(){
        dialogue = new HashMap<>();
    }

    public String getDialgoue(DialogueType type){
        return dialogue.get(type);
    }

    public void setDialogue(DialogueType type, String text){
        dialogue.put(type, text);
    }

}
