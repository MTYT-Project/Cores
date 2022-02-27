package me.javasyntaxerror.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.countdowns.Lobby_Countdown;
import me.javasyntaxerror.methods.mysql.Stats;
import me.javasyntaxerror.methods.others.GameState;
import me.javasyntaxerror.methods.others.manager.PlayerManager;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().setArmorContents(null);
		player.setExp(0);
		player.setLevel(60);
		
		event.setJoinMessage(null);

		Cores.getInstance().executorService.submit(() ->{
			if(Cores.getInstance().mysqlactivated){
				if(Stats.playerExists(player.getUniqueId().toString())){
					Stats.createPlayer(player.getUniqueId().toString());
				}
			}
			
			Lobby_Countdown.startLobbyCountdown();
		});
		
		Cores.getInstance().playerManager.put(player.getName(), new PlayerManager(player));
		if(Cores.getInstance().state == GameState.LOBBY){
			
			if(Bukkit.getOnlinePlayers().size() == Cores.getInstance().minPlayers) {
				Lobby_Countdown.lobbyCountdown = 61;
			}

			player.setFlying(false);
			player.setAllowFlight(false);
			Cores.getInstance().messages.sendToAllMessage(Cores.replace(Cores.translation("event.player.join", "§b%PLAYER% §7hat das Spiel betreten."), "%PLAYER%", player.getName()));
			Cores.getInstance().scoreboardManager.setScoreboardTeamLobby(player);

			Cores.getInstance().inventoryManager.setJoinInventory(player);
			Cores.getInstance().executorService.submit(() ->{
				Cores.getInstance().scoreboardManager.setLobbyScoreboard(player);
			});
			
			player.teleport(Cores.getInstance().locationManager.getLobbyLocation(player));
			
		} else if(Cores.getInstance().state == GameState.INGAME){
			
			Cores.getInstance().messages.sendMessage(player, Cores.translation("event.player.spectator", "Du bist dem Team §8Spectator §7beigetreten."));
			Cores.getInstance().team.put(player.getName(), "Spectator");
			Cores.getInstance().scoreboardManager.setScoreboardTeamSpec(player);
			
			player.setGameMode(GameMode.SPECTATOR);
			player.teleport(Cores.getInstance().locationManager.getSpecLocation(player));
			
			for(Player all : Bukkit.getOnlinePlayers()){
				if(!Cores.getInstance().team.get(all.getName()).equals("Spectator")){
					all.hidePlayer(player);
				}
			}
			
			Cores.getInstance().executorService.submit(() ->{
				Cores.getInstance().scoreboardManager.setInGameScoreboard(player);
			});
		} else {

			player.teleport(Cores.getInstance().locationManager.getLobbyLocation(player));
			
		}
	}

}
