package avernusvenine.sne.players;

import avernusvenine.sne.PlayerDictionary;
import avernusvenine.sne.npc.dialogue.Branch;
import avernusvenine.sne.npc.dialogue.DialogueTask;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDialogueHandler {

    private final Player owner;

    private boolean inDialogue;
    private boolean textScrolling;
    private boolean choosing;

    private Branch branch;
    private DialogueTask task;

    public PlayerDialogueHandler(Player player){
        owner = player;
    }

    public void start(Branch branch){
        this.branch = branch;
        setInDialogue(true);
        task = branch.run(owner);
    }

    public void next(){
        next(0);
    }

    public void next(int input){

        if(input == -1 || !branch.hasNext()){
            setInDialogue(false);
            return;
        }

        branch = branch.next(input);
        task = branch.run(owner);
    }

    public void skipDialogue(){
        if(task == null)
            return;

        task.skipDialogue(owner);
    }

    // Getters and Setters

    public void setBranch(Branch branch){
        this.branch = branch;
    }

    public void setInDialogue(boolean bool){
        inDialogue = bool;

        if(bool){
            PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 9999999, 4);
            owner.addPotionEffect(effect);
        }
        else {
            owner.removePotionEffect(PotionEffectType.SLOW);
            owner.clearTitle();
            PlayerDictionary.get(owner).closeDialogue();
        }
    }

    public void setTextScrolling(boolean bool){
        textScrolling = bool;
    }

    public void setChoosing(boolean bool){
        choosing = bool;
    }

    public boolean isInDialogue(){
        return inDialogue;
    }

    public boolean isTextScrolling(){
        return textScrolling;
    }

    public boolean isChoosing(){
        return choosing;
    }

}
