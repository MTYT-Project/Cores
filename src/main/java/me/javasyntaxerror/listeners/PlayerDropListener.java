package me.javasyntaxerror.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;

public class PlayerDropListener implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		if(Cores.getInstance().state == GameState.INGAME){
			if(Cores.getInstance().team.get(event.getPlayer().getName()) != null){
				String team = Cores.getInstance().team.get(event.getPlayer().getName());
				if(team.equals("Spectator")){
					event.setCancelled(true);
					return;
				}
			}
			event.setCancelled(false);
			return;
		} else {
			event.setCancelled(true);
		}
	}
}