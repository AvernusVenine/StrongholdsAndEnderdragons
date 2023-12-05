package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.PlayerDictionary;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.*;

public class ChoiceBranch extends Branch{

    protected final static TextColor GREEN = TextColor.color(91,200,49);
    protected final static TextColor RED = TextColor.color(200, 0, 0);

    protected final List<String> choices = new ArrayList<>();
    protected final List<TextComponent> buttons = new ArrayList<>();
    protected final List<ConditionalInterface> conditionals = new ArrayList<>();

    @Override
    public DialogueTask run(Player player) {

        for(int i = 0; i < 100; i++)
            player.sendMessage(" ");

        for(TextComponent button : buttons)
            if(conditionals.get(buttons.indexOf(button)).run(player))
                player.sendMessage(button);

        PlayerDictionary.get(player).getPlayerDialogueHandler().setChoosing(true);
        return null;
    }

    public ChoiceBranch append(int index, Branch branch, String text){
        nextBranches.add(index, branch);
        choices.add(index, text);
        conditionals.add(index, (Player player) -> true);
        return this;
    }

    public ChoiceBranch append(int index, Branch branch, String text, ConditionalInterface conditional){
        nextBranches.add(index, branch);
        choices.add(index, text);
        conditionals.add(index, conditional);
        return this;
    }

    public ChoiceBranch append(Branch branch, String text){
        nextBranches.add(branch);
        choices.add(text);
        conditionals.add((Player player) -> true);
        return this;
    }

    public ChoiceBranch append(Branch branch, String text, ConditionalInterface conditional){
        nextBranches.add(branch);
        choices.add(text);
        conditionals.add(conditional);
        return this;
    }

    public void build(){
        for(String text : choices){
            int index = choices.indexOf(text);
            TextComponent comp = Component.text(text).color(TextColor.color(91,200,49));
            comp = comp.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/npcdialoguechoice " + index));

            buttons.add(comp);
        }

        TextComponent comp = Component.text("[EXIT]");
        comp = comp.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/npcdialoguechoice " + -1)).color(TextColor.color(200, 0, 0));

        buttons.add(comp);
        conditionals.add((Player player) -> true);
    }
}
