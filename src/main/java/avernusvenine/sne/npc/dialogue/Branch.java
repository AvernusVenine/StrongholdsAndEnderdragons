package avernusvenine.sne.npc.dialogue;

import org.bukkit.entity.Player;

import java.util.*;

public abstract class Branch {

    protected List<Branch> nextBranches = new ArrayList<>();

    public abstract DialogueTask run(Player player);
    public Branch next(int input){
        return nextBranches.get(input);
    }

    public Branch append(int index, Branch branch){
        nextBranches.add(index, branch);
        return this;
    }

    public Branch append(Branch branch){
        nextBranches.add(branch);
        return this;
    }

    public boolean hasNext(){
        return !nextBranches.isEmpty();
    }

}
