package me.javasyntaxerror.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;

public class PlayerPingListener implements Listener {
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
		event.setMaxPlayers(Cores.getInstance().maxPlayersInTeam * Cores.getInstance().teams.size());
		
		if(Cores.getInstance().state == GameState.LOBBY){
			event.setMotd("§aLobby");
		} else if(Cores.getInstance().state == GameState.INGAME){
			event.setMotd("§6Lobby");
		} else if(Cores.getInstance().state == GameState.RESTART){
			event.setMotd("§4Restart");
		}
	}

}
