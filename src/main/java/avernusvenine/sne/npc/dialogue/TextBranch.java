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
        this.text = text;
        advanceOnFinish = advance;
    }

    public TextBranch(String text, boolean advance){
        this.text = new String[]{text, "", "", ""};
        advanceOnFinish = advance;
    }


    @Override
    public DialogueTask run(Player player) {
        return new DialogueTask(text, player, advanceOnFinish);
    }
}
