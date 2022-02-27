package me.javasyntaxerror.methods.others.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.countdowns.InGame_Countdown;
import me.javasyntaxerror.methods.others.PacketScoreboard;
import me.javasyntaxerror.methods.others.team.TeamManager;

public class ScoreboardManager {
	
	public void setLobbyScoreboard(Player player) {
		if (!Cores.getInstance().scoreBoard.containsKey(player.getName())) {
			Cores.getInstance().scoreBoard.put(player.getName(), new PacketScoreboard(player));
			((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).sendSidebar("    §b§lCores    ");
		}
		Bukkit.getScheduler().runTask(Cores.getInstance(), new Runnable() {
			public void run() {
				for (int i = 0; i < 15; i++) {
					((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(i);
				}
				
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(5), " ");
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(4), "§fMap§8:");
			    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(3), "§e" + Cores.getInstance().getConfig().getString("MapName"));
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(2), "  ");
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(1), "§fTeam§8:");				
				if(Cores.getInstance().team.get(player.getName()) != null){
					((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(0), "§7" + Cores.getInstance().teamManager.get(Cores.getInstance().team.get(player.getName())).getPrefix() + Cores.getInstance().team.get(player.getName()));
				} else {
					((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(0), "§4✖");
				}
			}
		});
	}
	
	public void setInGameScoreboard(Player player) {
		if (!Cores.getInstance().scoreBoard.containsKey(player.getName())) {
			Cores.getInstance().scoreBoard.put(player.getName(), new PacketScoreboard(player));
			((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).sendSidebar("   §b§lCores §7| §3" + returnTime(InGame_Countdown.count) + "   ");
		}
		Bukkit.getScheduler().runTask(Cores.getInstance(), new Runnable() {
			public void run() {
				for (int i = 0; i < 15; i++) {
					((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(i);
				}
				TeamManager teamManagerRot = Cores.getInstance().teamManager.get("Rot");
				TeamManager teamManagerBlau = Cores.getInstance().teamManager.get("Blau");

				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(10), " ");
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(9), "§9Team Blau");
				if(teamManagerBlau.isLeftCore()) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(8), "§a✔ §fLeftCore ");
				} else {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(8), "§4✖ §fLeftCore ");
				}
				if(teamManagerBlau.isRightCore()) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(7), "§a✔ §fRightCore ");
				} else {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(7), "§4✖ §fRightCore ");
				}
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(6), "  ");
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(5), "§cTeam Rot");
				if(teamManagerRot.isLeftCore()) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(4), "§a✔ §fLeftCore");
				} else {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(4), "§4✖ §fLeftCore");
				}
				if(teamManagerRot.isRightCore()) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(3), "§a✔ §fRightCore");
				} else {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(3), "§4✖ §fRightCore");
				}
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(2), "   ");
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(1), "§fKills");				
				((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(0), "§e" + Cores.getInstance().playerManager.get(player.getName()).getKills());
			}
		});
	}
	
	public void updateLeftCore(Player player, Integer integer, Integer left) {
		TeamManager teamManagerRot = Cores.getInstance().teamManager.get("Rot");
		TeamManager teamManagerBlau = Cores.getInstance().teamManager.get("Blau");
		
		if(integer == 0) {
			((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(4);
			
			if(teamManagerRot.isLeftCore()) {
				if(left == 0) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(4), "§a✔ §fLeftCore");
				} else if(left == 1) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(4), "§6➤ §cLeftCore");
				}
			} else {
			    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(4), "§4✖ §fLeftCore");
			}
		} else if(integer == 1) {
			((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(8);
			
			if(teamManagerBlau.isLeftCore()) {
				if(left == 0) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(8), "§a✔ §fLeftCore ");
				} else if(left == 1) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(8), "§6➤ §cLeftCore ");
				}
			} else {
			    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(8), "§4✖ §fLeftCore ");
			}
		}
	}
	
	public void updateRightCore(Player player, Integer integer, Integer right) {
		TeamManager teamManagerRot = Cores.getInstance().teamManager.get("Rot");
		TeamManager teamManagerBlau = Cores.getInstance().teamManager.get("Blau");
		
		if(integer == 0) {
			((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(3);
			
			if(teamManagerRot.isRightCore()) {
				if(right == 0) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(3), "§a✔ §fRightCore");
				} else if(right == 1) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(3), "§6➤ §cRightCore");
				}
			} else {
			    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(3), "§4✖ §fRightCore");
			}
		} else if(integer == 1) {
			((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(7);
			
			if(teamManagerBlau.isRightCore()) {
				if(right == 0) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(7), "§a✔ §fRightCore ");
				} else if(right == 1) {
				    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(7), "§6➤ §cRightCore ");
				}
			} else {
			    ((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(7), "§4✖ §fRightCore ");
			}
		}
	}
	
	public void updateInGameKills(Player player) {
		((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(0);
		((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(0), "§e" + Cores.getInstance().playerManager.get(player.getName()).getKills());
	}
	
	public void updateTime(Player player) {
		((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setName("   §b§lCores §7| §3" + returnTime(InGame_Countdown.count) + "   ");
	}
	
	public void updateLobbyTeamScoreboard(Player player) {
		((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).removeLine(0);
		((PacketScoreboard) Cores.getInstance().scoreBoard.get(player.getName())).setLine(Integer.valueOf(0), "§7" + Cores.getInstance().teamManager.get(Cores.getInstance().team.get(player.getName())).getPrefix() + Cores.getInstance().team.get(player.getName()));
	}
	
	@SuppressWarnings("deprecation")
	public void setScoreboardTeamLobby(Player player){
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0000Lobby");
		
		if(team == null){
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("0000Lobby");
			team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0000Lobby");
			team.setPrefix("§7");
		}
		team.setPrefix("§7");
		team.addPlayer(player);
		
		player.setPlayerListName("§7" + player.getName());
	}
	
	@SuppressWarnings("deprecation")
	public void setScoreboardTeamSpec(Player player){
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0009Lobby");
		
		if(team == null){
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("0009Lobby");
			team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0009Lobby");
			team.setPrefix("§7[§4✖§7] §7| §7");
		}
		
		team.setPrefix("§7[§4✖§7] §7| §7");
		team.addPlayer(player);

		player.setPlayerListName("§7[§4✖§7] §7| §7" + player.getName());
	}
	
	@SuppressWarnings("deprecation")
	public void setScoreboardTeamInGame(Player player){
		String teamName = Cores.getInstance().team.get(player.getName());
		TeamManager manager = Cores.getInstance().teamManager.get(teamName);
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(manager.getTeamName());
		
		if(team == null){
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(manager.getTeamName());
			team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(manager.getTeamName());
			team.setPrefix(manager.getPrefix() + teamName + " §7| " + manager.getPrefix());
		}
		
		team.setPrefix(manager.getPrefix() + teamName + " §7| " + manager.getPrefix());
		team.addPlayer(player);

		player.setPlayerListName(manager.getPrefix() + teamName + " §7| " + manager.getPrefix() + player.getName());
	}
	
	public String returnTime(Integer integer){
		Integer minutes = 0;
		
		while(integer >= 60){
			integer -= 60;
			minutes++;
		}
		
		if(minutes == 0 && integer < 10){
			return "00:0" + integer;
		} else if(minutes == 0 && integer > 9){
			return "00:" + integer;
		} else if(minutes < 10 && integer < 10){
			return "0" + minutes + ":0" + integer;
		} else if(minutes < 10 && integer > 9){
			return "0" + minutes + ":" + integer;
		} else if(minutes > 9 && integer < 10){
			return "" + minutes + ":0" + integer;
		} else if(minutes > 9 && integer > 9){
			return "" + minutes + ":" + integer;
		} else {
			return "00:00";
		}
	}

}
