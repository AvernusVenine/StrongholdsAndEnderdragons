package avernusvenine.sne.npc.dialogue;

import avernusvenine.sne.professions.Profession.ProfessionType;

import java.util.ArrayList;
import java.util.List;

public class TrainerDialogueSet extends DialogueSet{

    private List<String[]> levelZero;
    private List<String[]> professionsFull;
    private List<String[]> professionAccept;
    private List<String[]> professionDeny;

    private ProfessionType professionType;

    public TrainerDialogueSet(String id){
        super(id);
        type = DialogueType.TRAINER;
    }

    public void setProfessionType(ProfessionType type){
        professionType = type;
    }

    public ProfessionType getProfessionType(){
        return  professionType;
    }

    public void addLevelZero(List<String[]> text){
        levelZero = text;
    }

    public void addLevelZero(String[] text){
        List<String[]> temp =  new ArrayList<>();
        temp.add(text);
        levelZero = temp;
    }

    public List<String[]> getLevelZero(){
        return levelZero;
    }

    public void addProfessionsFull(List<String[]> text){
        professionsFull = text;
    }

    public void addProfessionsFull(String[] text){
        List<String[]> temp =  new ArrayList<>();
        temp.add(text);
        professionsFull = temp;
    }

    public List<String[]> getProfessionsFull(){
        return professionsFull;
    }

    public void addProfessionAccept(List<String[]> text){
        professionAccept = text;
    }

    public void addProfessionAccept(String[] text){
        List<String[]> temp =  new ArrayList<>();
        temp.add(text);
        professionAccept = temp;
    }

    public List<String[]> getProfessionAccept(){
        return professionAccept;
    }

    public void addProfessionDeny(List<String[]> text){
        professionDeny = text;
    }

    public void addProfessionDeny(String[] text){
        List<String[]> temp =  new ArrayList<>();
        temp.add(text);
        professionDeny = temp;
    }

    public List<String[]> getProfessionDeny(){
        return professionDeny;
    }
}
