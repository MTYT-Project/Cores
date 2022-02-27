package me.javasyntaxerror.methods.others.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.javasyntaxerror.Cores;

public class Messages {
	
	public void sendMessage(Player player, String message){
		player.sendMessage(Cores.getInstance().prefix + message);
	}
	
	public void sendMessage2(Player player, String message){
		player.sendMessage(message);
	}
	
	public void sendToAllMessage(String message){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.sendMessage(Cores.getInstance().prefix + message);
		}
	}
	
	public void sendToAllWithOutPrefixMessage(String message){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.sendMessage(message);
		}
	}

}
