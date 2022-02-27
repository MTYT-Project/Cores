package me.javasyntaxerror.listeners;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;
import me.javasyntaxerror.methods.others.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockChangeListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(Cores.getInstance().state == GameState.INGAME) {
			return;
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(Cores.getInstance().state != GameState.INGAME) {
			event.setCancelled(true);
			return;
		}
		
		if(event.getBlock().getType() == Material.BEACON) {
			String playerTeam = Cores.getInstance().team.get(event.getPlayer().getName());
			
			if(playerTeam.equalsIgnoreCase(Cores.translation("team.red.shortname", "Rot"))) {
				if(event.getBlock().getLocation().equals(Cores.getInstance().locationManager.getLocation(event.getPlayer(), "blauleftcore"))) {
					Cores.getInstance().teamManager.get(Cores.translation("team.blue.shortname", "Blau")).setLeftCore(false);
					
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						Cores.getInstance().scoreboardManager.updateLeftCore(all, 1, 0);
						all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
						all.sendMessage(Cores.getInstance().prefix + "§b" + event.getPlayer().getName() + Cores.translation("info.core_break", " §7has broken down a §bCore §7 of the opponents!"));
					}

					TeamManager teamManager = Cores.getInstance().teamManager.get(Cores.translation("team.blue.shortname", "Blau"));
					if(!teamManager.isLeftCore() && !teamManager.isRightCore()){
						Cores.getInstance().teams.remove(Cores.translation("team.blue.shortname", "Blau"));
					}
				} else if(event.getBlock().getLocation().equals(Cores.getInstance().locationManager.getLocation(event.getPlayer(), "blaurightcore"))) {
					Cores.getInstance().teamManager.get(Cores.translation("team.blue.shortname", "Blau")).setRightCore(false);
					
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						Cores.getInstance().scoreboardManager.updateRightCore(all, 1, 0);
						all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
						all.sendMessage(Cores.getInstance().prefix + "§b" + event.getPlayer().getName() + Cores.translation("info.core_break", " §7has broken down a §bCore §7 of the opponents!"));
					}

					TeamManager teamManager = Cores.getInstance().teamManager.get(Cores.translation("team.blue.shortname", "Blau"));
					if(!teamManager.isLeftCore() && !teamManager.isRightCore()){
						Cores.getInstance().teams.remove(Cores.translation("team.blue.shortname", "Blau"));
					}
				} else {
					event.getPlayer().sendMessage(Cores.getInstance().prefix + Cores.translation("warning.breaking_wrong_crystal", "§7Du kannst den §bCore §7deines §bTeams §7nicht abbauen."));
					event.setCancelled(true);
				}
			} else if(playerTeam.equalsIgnoreCase(Cores.translation("team.blue.shortname", "Blau"))) {
				if(event.getBlock().getLocation().equals(Cores.getInstance().locationManager.getLocation(event.getPlayer(), "rotleftcore"))) {
					Cores.getInstance().teamManager.get(Cores.translation("team.red.shortname", "Rot")).setLeftCore(false);
					
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						Cores.getInstance().scoreboardManager.updateLeftCore(all, 0, 0);
						all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
						all.sendMessage(Cores.getInstance().prefix + "§b" + event.getPlayer().getName() + Cores.translation("info.core_break", " §7has broken down a §bCore §7 of the opponents!"));
					}

					TeamManager teamManager = Cores.getInstance().teamManager.get(Cores.translation("team.red.shortname", "Rot"));
					if(!teamManager.isLeftCore() && !teamManager.isRightCore()){
						Cores.getInstance().teams.remove(Cores.translation("team.red.shortname", "Rot"));
					}
				} else if(event.getBlock().getLocation().equals(Cores.getInstance().locationManager.getLocation(event.getPlayer(), "rotrightcore"))) {
					Cores.getInstance().teamManager.get(Cores.translation("team.red.shortname", "Rot")).setRightCore(false);
					
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						Cores.getInstance().scoreboardManager.updateRightCore(all, 0, 0);
						all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
						all.sendMessage(Cores.getInstance().prefix + "§b" + event.getPlayer().getName() + Cores.translation("info.core_break", " §7has broken down a §bCore §7 of the opponents!"));
					}

					TeamManager teamManager = Cores.getInstance().teamManager.get(Cores.translation("team.red.shortname", "Rot"));
					if(!teamManager.isLeftCore() && !teamManager.isRightCore()){
						Cores.getInstance().teams.remove(Cores.translation("team.red.shortname", "Rot"));
					}
				} else {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Cores.getInstance().prefix + Cores.translation("warning.breaking_wrong_crystal", "§7Du kannst den §bCore §7deines §bTeams §7nicht abbauen."));
				}
			}
		}
	}

}
