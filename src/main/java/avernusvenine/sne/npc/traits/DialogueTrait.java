package avernusvenine.sne.npc.traits;

import avernusvenine.sne.StrongholdsAndEnderdragons;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DialogueTrait extends Trait {

    protected static String title = "dialogue_trait";

    protected List<String> dialogue;

    public DialogueTrait(String dialogue){
        super(title);
        this.dialogue = new ArrayList<>();
        this.dialogue.add(dialogue);
    }

    protected DialogueTrait(List<String> dialogue){
        super(title);
        this.dialogue = dialogue;
    }

    @EventHandler
    public void onRightClick(net.citizensnpcs.api.event.NPCRightClickEvent event){
           if(event.getNPC() != this.getNPC())
               return;
           event.getClicker().sendMessage("You clicked on " + event.getNPC().getName());
    }

}
