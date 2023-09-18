package avernusvenine.sne.scoreboard;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardHandler implements Listener {

    private static final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static final Scoreboard board = manager.getNewScoreboard();

    public static void init(){

    }

    public static void addScore(String objectiveName){

    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(!player.hasPlayedBefore()){

        }

    }


}
