package avernusvenine.sne.players;

import avernusvenine.sne.*;

import avernusvenine.sne.status.StatusEffect;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PlayerProfile {

    protected PlayerCharacter playerCharacter;
    protected Player player;

    // Dialogue variables
    protected ActionBarTask actionBarTask;
    protected PlayerDialogueHandler playerDialogueHandler;
    protected CooldownHandler cooldownHandler;

    protected List<StatusEffect> statusEffects = new ArrayList<>();

    public PlayerProfile(Player player){
        this.player = player;

        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToEmpty();
        cooldownHandler = new CooldownHandler(player);
        playerDialogueHandler = new PlayerDialogueHandler(player);
    }

    public void onCharacterSelect(){
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void onPlayerQuit(){
        actionBarTask.cancelTask();
        cooldownHandler.removeAll();
    }

    public void addCooldown(String id, int cooldown){
        cooldownHandler.add(id, cooldown);
    }

    public void removeCooldown(String id){
        cooldownHandler.remove(id);
    }

    public boolean checkCooldown(String id){
        return cooldownHandler.check(id);
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

    public void openDialogue(){
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToDialogueBox();
    }

    public void closeDialogue(){
        actionBarTask.cancelTask();
        actionBarTask = new ActionBarTask(player);
        actionBarTask.changeToOverlay();
    }

    public void addStatusEffect(StatusEffect effect){
        statusEffects.add(effect);
    }

    public void removeStatusEffect(StatusEffect effect){
        statusEffects.remove(effect);
    }

    public void removeStatusEffect(Class<? extends StatusEffect> type){

        for(StatusEffect effect : statusEffects){
            if(effect.getClass() == type){
                effect.cancel();
                statusEffects.remove(effect);
            }
        }
    }

    public boolean hasStatusEffect(Class<? extends StatusEffect> type){
        for(StatusEffect effect : statusEffects)
            if(effect.getClass() == type)
                return true;

        return false;
    }

    // Getters and Setters

    public PlayerDialogueHandler getPlayerDialogueHandler(){
        return playerDialogueHandler;
    }

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
    }

    public String getUUID(){
        return player.getUniqueId().toString();
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

            String[] icons = switch (playerCharacter.getClassType()) {
                case ARTIFICER -> new String[]{"\ue243", "\ue244", "\ue245"};
                case BARBARIAN -> new String[]{"\ue246", "\ue247", "\ue248"};
                case BARD, WIZARD, SORCERER, WARLOCK, DRUID, SHAMAN -> new String[]{"\ue240", "\ue241", "\ue242"};
                case ROGUE -> new String[]{"\ue249", "\ue250", "\ue251"};
                case RANGER, FIGHTER -> new String[]{"\ue258", "\ue259", "\ue260"};
                case MONK -> new String[]{"\ue252", "\ue253", "\ue254"};
                case PALADIN, CLERIC -> new String[]{"\ue255", "\ue256", "\ue257"};
                default -> new String[3];
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
