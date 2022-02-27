package me.javasyntaxerror.methods.others;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.Maps;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class PacketScoreboard {
	
	private Player player;
	private HashMap<Integer, String> lines;
	private boolean displayed;

	public PacketScoreboard(Player player) {
		this.player = player;
		this.lines = Maps.newHashMap();
		this.displayed = false;
	}

	public void sendSidebar(String displayedName) {
		if (this.displayed) {
			return;
		}
		PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
		scoreboard.getStrings().write(0, "sideboard").write(1, displayedName);
		scoreboard.getIntegers().write(0, Integer.valueOf(0));
		scoreboard.getSpecificModifier(IScoreboardCriteria.EnumScoreboardHealthDisplay.class).write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);

		PacketContainer playOut = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
		playOut.getIntegers().write(0, Integer.valueOf(1));
		playOut.getStrings().write(0, "sideboard");
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, playOut);
			this.displayed = true;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			this.displayed = false;
		}
	}

	public void remove() {
		if (!this.displayed) {
			return;
		}
		PacketContainer unsend = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
		unsend.getStrings().write(0, "sideboard").write(1, "");
		unsend.getIntegers().write(0, Integer.valueOf(1));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, unsend);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		this.displayed = false;
	}

	public void setLine(Integer line, String text) {
		if (!this.displayed) {
			return;
		}
		if (text.length() > 40) {
			text = text.substring(0, 40);
		}
		PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
		score.getStrings().write(0, text).write(1, "sideboard");
		score.getIntegers().write(0, line);
		score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class).write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
			this.lines.put(line, text);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void removeLine(Integer line) {
		if (!this.displayed) {
			return;
		}
		if (!this.lines.containsKey(line)) {
			return;
		}
		String text = (String) this.lines.get(line);

		PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
		score.getStrings().write(0, text).write(1, "sideboard");
		score.getIntegers().write(0, line);
		score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class).write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE);
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
			this.lines.remove(line);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void setName(String displayedName) {
		if (!this.displayed) {
			return;
		}
		PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
		scoreboard.getStrings().write(0, "sideboard").write(1, displayedName);
		scoreboard.getIntegers().write(0, Integer.valueOf(2));
		scoreboard.getSpecificModifier(IScoreboardCriteria.EnumScoreboardHealthDisplay.class).write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);

		PacketContainer playOut = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
		playOut.getIntegers().write(0, Integer.valueOf(1));
		playOut.getStrings().write(0, "sideboard");
		
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, playOut);
			this.displayed = true;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			this.displayed = false;
		}
	}

	public String getLine(Integer line) {
		return (String) this.lines.get(line);
	}

}
