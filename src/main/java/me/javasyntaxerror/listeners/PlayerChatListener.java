package me.javasyntaxerror.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;

@SuppressWarnings("deprecation")
public class PlayerChatListener implements Listener {
	
	@EventHandler
	public void onChat(PlayerChatEvent event){
		event.setCancelled(true);
		
		if(Cores.getInstance().state == GameState.INGAME){
			if(Cores.getInstance().team.get(event.getPlayer().getName()).equals("Spectator")){
				for(Player all : Bukkit.getOnlinePlayers()){
					if(Cores.getInstance().team.get(all.getName()).equals("Spectator")){
						Cores.getInstance().messages.sendMessage2(all, "§c✖ §7| §7" + event.getPlayer().getName() + " §8» §f" + event.getMessage());
					}
				}
			} else {
				if(event.getMessage().startsWith("@a ")){
					String team = Cores.getInstance().team.get(event.getPlayer().getName());
					String prefix = Cores.getInstance().teamManager.get(team).getPrefix();
					String message = event.getMessage();
					message = message.replace("@a ", "");
					
					Cores.getInstance().messages.sendToAllWithOutPrefixMessage(prefix + team + " §7| " + prefix + event.getPlayer().getName() + " §8» §f" + message);
					
				} else if(event.getMessage().startsWith("@a")){
					String team = Cores.getInstance().team.get(event.getPlayer().getName());
					String prefix = Cores.getInstance().teamManager.get(team).getPrefix();
					String message = event.getMessage();
					message = message.replace("@a", "");
					
					Cores.getInstance().messages.sendToAllWithOutPrefixMessage(prefix + team + " §7| " + prefix + event.getPlayer().getName() + " §8» §f" + message);
					
				} else {
					String team = Cores.getInstance().team.get(event.getPlayer().getName());
					String prefix = Cores.getInstance().teamManager.get(team).getPrefix();
					String message = event.getMessage();
					
					for(String strings : Cores.getInstance().teamManager.get(team).getPlayers()){
						Player target = Bukkit.getPlayer(strings);
						target.sendMessage("§7[§aTeam§7] " + prefix + team + " §7| " + prefix + event.getPlayer().getName() + " §8» §f" + message);
					}
						
				}
			}
		} else {
			Cores.getInstance().messages.sendToAllWithOutPrefixMessage("§7" + event.getPlayer().getName() + " §8» §f" + event.getMessage());
		}
	}

}
