package me.javasyntaxerror.listeners;

import org.bukkit.event.player.PlayerQuitEvent;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.mysql.Stats;
import me.javasyntaxerror.methods.others.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		event.setQuitMessage(null);
		
		Cores.getInstance().scoreBoard.remove(event.getPlayer().getName());
		
		if(Cores.getInstance().state == GameState.INGAME){
			String team = Cores.getInstance().team.get(event.getPlayer().getName());
			
			if(!team.equals("Spectator")){
				String prefix = Cores.getInstance().teamManager.get(team).getPrefix();
				
				Cores.getInstance().teamManager.get(team).removePlayer(event.getPlayer());	
				Cores.getInstance().team.remove(event.getPlayer().getName());

				if(Cores.getInstance().mysqlactivated){
					Cores.getInstance().executorService.submit(() -> Stats.addVerloreneSpiele(event.getPlayer().getUniqueId().toString(), 1));
				}
				String left=Cores.translation("event.player.left", "%PLAYER% §7hat das Spiel verlassen. %PREFIX% Team %TEAM% §7hat noch %NUMBER% Spieler.");
				left = Cores.replace(left, "%PLAYER", event.getPlayer().getName());
				left = Cores.replace(left, "%PREFIX%", prefix);
				left = Cores.replace(left, "%TEAM%", team);
				left = Cores.replace(left, "%NUMBER%", String.valueOf(Cores.getInstance().teamManager.get(team).getPlayersSize()));
				
				Cores.getInstance().messages.sendToAllMessage(prefix + left);
			
				if(Cores.getInstance().teamManager.get(team).getPlayersSize() == 0){
					Cores.getInstance().teams.remove(team);
					for(Player all : Bukkit.getOnlinePlayers()) {
						Cores.getInstance().scoreboardManager.setInGameScoreboard(all);
					}
				} else {
					for(Player all : Bukkit.getOnlinePlayers()) {
						Cores.getInstance().scoreboardManager.setInGameScoreboard(all);
					}
				}
			} else {
				if(Cores.getInstance().team.get(event.getPlayer().getName()) != null){
					if(Cores.getInstance().teamManager.get(Cores.getInstance().team.get(event.getPlayer().getName())) != null){
						if(Cores.getInstance().teamManager.get(Cores.getInstance().team.get(event.getPlayer().getName())).getPlayers().contains(event.getPlayer().getName())){
							Cores.getInstance().teamManager.get(Cores.getInstance().team.get(event.getPlayer().getName())).removePlayer(event.getPlayer());
						}
					}
				}
				Cores.getInstance().team.remove(event.getPlayer().getName());
			}
		} else {
			Cores.getInstance().messages.sendToAllMessage(Cores.replace(Cores.translation("event.player.left_notingame", "§b%PLAYER% §7hat das Spiel verlassen"), "%PLAYER%", event.getPlayer().getName()));
			String team = Cores.getInstance().team.get(event.getPlayer().getName());
			
			if(Cores.getInstance().state == GameState.LOBBY){
				if(team != null){
					Cores.getInstance().teamManager.get(team).removePlayer(event.getPlayer());	
					Cores.getInstance().team.remove(event.getPlayer().getName());
				}
			}
		}
	}

}
