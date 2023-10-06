package avernusvenine.sne.npc.traits;

import avernusvenine.sne.NPCDictionary;
import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.DialogueSet;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class DialogueTrait extends Trait {

    protected static String title = "dialogue_trait";

    public DialogueTrait(){
        super(title);
    }

    @EventHandler
    public void onRightClick(net.citizensnpcs.api.event.NPCRightClickEvent event){
        if(event.getNPC() != this.getNPC())
            return;

        NPCDictionary.getByUUID(event.getNPC().getUniqueId().toString()).advanceDialogue(event.getClicker());
    }

}
