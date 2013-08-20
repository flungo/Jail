package com.matejdro.bukkit.jail;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class JailScoreboardManager {
	
	public ScoreboardManager manager = Bukkit.getScoreboardManager();
	public HashMap<String, Scoreboard> scoreboards = new HashMap<String, Scoreboard>();
	
	public void displayJailTime(){
		for(String name: Jail.prisoners.keySet()){
			scoreboards.put(name, manager.getNewScoreboard());
		}
		for(String name: scoreboards.keySet()){
			Scoreboard board = scoreboards.get(name);
			Objective obj;
			if(!Jail.prisoners.containsKey(name)){
				scoreboards.remove(name);
				if(Bukkit.getPlayer(name) != null){
					Bukkit.getPlayer(name).setScoreboard(manager.getNewScoreboard());
				}
			}
			if(board.getObjective("test") == null){
				obj = board.registerNewObjective("test", "dummy");
			}else{
				obj = board.getObjective("test");
			}
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("Jail Stats");
			Score score = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Time:"));
			if(scoreboards.containsKey(name)){
				score.setScore((int) Math.ceil(Jail.prisoners.get(name).getRemainingTimeMinutes()));
			}
			if(Bukkit.getPlayer(name) != null && scoreboards.containsKey(name)){
				Bukkit.getPlayer(name).setScoreboard(scoreboards.get(name));
			}
		}
	}
}
