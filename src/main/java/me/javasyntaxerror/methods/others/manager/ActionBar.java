package me.javasyntaxerror.methods.others.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionBar {
	
	public void sendActionBar(Player player, String message){
		String s = ChatColor.translateAlternateColorCodes('&', message);
		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
	}
	
	public void sendAllActionBar(String message){
		if(Bukkit.getOnlinePlayers().size() == 0){
			return;
		}
		String s = ChatColor.translateAlternateColorCodes('&', message);
		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
		}
	}

}
