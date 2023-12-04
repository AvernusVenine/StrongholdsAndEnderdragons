package avernusvenine.sne.npc.dialogue;

import org.bukkit.entity.Player;

public class FunctionBranch extends Branch{

    private final BranchInterface func;

    public FunctionBranch(BranchInterface func){
        this.func = func;
    }

    @Override
    public DialogueTask run(Player player) {
        func.run(player);
        return null;
    }
}
