package avernusvenine.sne.npc.dialogue;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class NextBranch extends ChoiceBranch{

    public NextBranch(Branch branch){
        append(0, branch, "[NEXT]");
        build();
    }
}
