package avernusvenine.sne.npc.dialogue;

import org.bukkit.entity.Player;

public class TextBranch extends Branch{

    private final String[] text;
    private final boolean advanceOnFinish;

    private DialogueTask task;

    public TextBranch(String[] text){
        String[] temp = new String[]{"", "", "", ""};
        for(int i = 0; i < text.length && i < 4; i++)
            temp[i] = text[i];

        this.text = temp;
        advanceOnFinish = false;
    }

    public TextBranch(String text){
        this.text = new String[]{text, "", "", ""};
        advanceOnFinish = false;
    }

    public TextBranch(String[] text, boolean advance){
        String[] temp = new String[]{"", "", "", ""};
        for(int i = 0; i < text.length && i < 4; i++)
            temp[i] = text[i];

        this.text = temp;
        advanceOnFinish = advance;
    }

    public TextBranch(String text, boolean advance){
        this.text = new String[]{text, "", "", ""};
        advanceOnFinish = advance;
    }

    public TextBranch(Object text){
        if(text instanceof String string)
            this.text = new String[]{string, "", "", ""};
        else if(text instanceof String[] array){
            String[] temp = new String[]{"", "", "", ""};
            for(int i = 0; i < array.length && i < 4; i++)
                temp[i] = array[i];
            this.text = temp;
        }
        else
            this.text = new String[4];
        advanceOnFinish = false;
    }

    public TextBranch(Object text, boolean advance){
        if(text instanceof String string)
            this.text = new String[]{string, "", "", ""};
        else if(text instanceof String[] array){
            String[] temp = new String[]{"", "", "", ""};
            for(int i = 0; i < array.length && i < 4; i++)
                temp[i] = array[i];
            this.text = temp;
        }
        else
            this.text = new String[4];
        advanceOnFinish = advance;
    }

    @Override
    public DialogueTask run(Player player) {
        return new DialogueTask(text, player, advanceOnFinish);
    }
}
