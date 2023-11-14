package avernusvenine.sne.players;

import avernusvenine.sne.Globals;
import avernusvenine.sne.StrongholdsAndEnderdragons;
import avernusvenine.sne.npc.dialogue.*;
import avernusvenine.sne.npc.dialogue.DialogueHandler.Phase;
import avernusvenine.sne.players.PlayerCharacter.QuestStatus.Status;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PlayerProfile {

    protected PlayerCharacter playerCharacter;
    protected Player player;

    protected boolean inDialogue = false;
    protected ActionBarTask actionBarTask;
    protected DialogueHandler dialogueHandler;

    public PlayerProfile(Player player){
        this.player = player;

        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToEmpty();

        dialogueHandler = new DialogueHandler();
    }

    public void onCharacterSelect(){
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void onPlayerQuit(){
        actionBarTask.cancelTask();
        dialogueHandler.reset();
    }

    public void addResource(int amount){
        playerCharacter.addResource(amount);

        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void removeResource(int amount){
        playerCharacter.removeResource(amount);

        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void setResource(int amount){
        playerCharacter.setResource(amount);

        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public boolean hasEnoughResource(int cost){
        return cost <= playerCharacter.getCurrentResource();
    }

    public void openDialogue(DialogueSet set){

        switch(set.getType()){
            case QUEST:
                dialogueHandler = new QuestDialogueHandler();
                break;
            case TRAINER:
                dialogueHandler = new TrainerDialogueHandler();
                break;
            case DEFAULT:
                dialogueHandler = new DialogueHandler();
                break;
        }

        dialogueHandler.setDialogueSet(set);
        inDialogue = true;
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToDialogueBox();
    }

    public void closeDialogue(){
        inDialogue = false;
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
        dialogueHandler.reset();
    }

    public void closeQuestCompletion(){
        dialogueHandler.advance(player, Phase.CLOSE);
        closeDialogue();
    }

    public void advanceDialogue(){
        dialogueHandler.advance(player);
    }

    public void onQuestAccept(){
        dialogueHandler.advance(player, Phase.QUEST_ACCEPT);
        QuestDialogueHandler handler = (QuestDialogueHandler) dialogueHandler;
        playerCharacter.updateQuestStatus(handler.getCurrentQuestID(), Status.ACCEPTED);
    }

    public void onQuestDeny(){
        dialogueHandler.advance(player, Phase.QUEST_DENY);
    }

    public void onQuestCompletion(){
        dialogueHandler.advance(player, Phase.QUEST_COMPLETION);
    }

    public void onProfessionAccept(){
        dialogueHandler.advance(player, Phase.TRAINER_PROFESSION_ACCEPT);
        TrainerDialogueHandler handler = (TrainerDialogueHandler) dialogueHandler;
        playerCharacter.setProfession(1, 0, handler.getProfessionType());
    }

    public void onProfessionDeny(){
        dialogueHandler.advance(player, Phase.TRAINER_PROFESSION_DENY);
    }

    // Getters and Setters

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
    }

    public String getUUID(){
        return player.getUniqueId().toString();
    }

    public boolean isInDialogue(){
        return inDialogue;
    }

    public class ActionBarTask{

        private final BukkitTask task;

        private Component currentActionBar;

        public ActionBarTask(Player player){

            currentActionBar = Component.empty();

            task = Bukkit.getScheduler().runTaskTimer(StrongholdsAndEnderdragons.plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendActionBar(currentActionBar);
                }
            }, 1, 40);

        }

        public void changeToDialogueBox(){
            currentActionBar = Component.text('\ue239').font(Key.key("sne:images"));
        }

        public void changeToEmpty(){
            currentActionBar = Component.empty();
        }

        public void changeToOverlay(){
            Component comp = Component.text("");

            char[] icons = switch (playerCharacter.getClassType()) {
                case ARTIFICER -> new char[]{'\ue243', '\ue244', '\ue245'};
                case BARBARIAN -> new char[]{'\ue246', '\ue247', '\ue248'};
                case BARD, WIZARD, SORCERER, WARLOCK, DRUID, SHAMAN -> new char[]{'\ue240', '\ue241', '\ue242'};
                case ROGUE -> new char[]{'\ue249', '\ue250', '\ue251'};
                case RANGER, FIGHTER -> new char[]{'\ue258', '\ue259', '\ue260'};
                case MONK -> new char[]{'\ue252', '\ue253', '\ue254'};
                case PALADIN, CLERIC -> new char[]{'\ue255', '\ue256', '\ue257'};
                default -> new char[3];
            };

            int max = playerCharacter.getClassType().getSneClass().getMaxResource(playerCharacter.getLevel());
            int current = playerCharacter.getCurrentResource();

            int full = Math.floorDiv(current, max/10);

            for(int i = 0; i < full; i++) {
                comp = comp.append(Component.text(icons[0])).font(Key.key("sne:images")).color(Globals.shadowless)
                        .append(Component.text(Globals.convertWidthToMinecraftCode(-2)).font(Key.key("space:default")));
            }

            if(current%(max/10) != 0){
                comp = comp.append(Component.text(icons[2])).font(Key.key("sne:images")).color(Globals.shadowless)
                        .append(Component.text(Globals.convertWidthToMinecraftCode(-2)).font(Key.key("space:default")));;
                full++;
            }

            for(int i = 0; i < 10-full; i++) {
                comp = comp.append(Component.text(icons[1])).font(Key.key("sne:images")).color(Globals.shadowless)
                        .append(Component.text(Globals.convertWidthToMinecraftCode(-2)).font(Key.key("space:default")));
            }

            comp = comp.append(Component.text(Globals.convertWidthToMinecraftCode(-98)).font(Key.key("space:default")));

            currentActionBar = comp;
        }

        public void cancelTask(){
            task.cancel();
        }

    }

}
