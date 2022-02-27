package me.javasyntaxerror.methods.mysql;

import me.javasyntaxerror.Cores;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {
	
	public static boolean playerExists(String uuid) {
		ResultSet rs = Cores.getInstance().mysql.query("select * from Cores where UUID= '" + uuid + "'");

		try {
			if (rs.next()) {
				if (rs.getString("UUID") != null) {
					MySQL.closeResultset(rs);
					return false;
				}
			}
		} catch (SQLException ignored) {
		}

		MySQL.closeResultset(rs);
		return true;
	}

	public static void createPlayer(String uuid) {
		Cores.getInstance().mysql.update("INSERT INTO Cores(UUID, GSPIELE, VSPIELE, GESPIELE) VALUES ('" + uuid + "', '0', '0', '0');");
	}
	
	public static Integer getVerloreneSpiele(String uuid) {
		ResultSet rs = Cores.getInstance().mysql.query("select * from Cores where UUID= '" + uuid + "'");
		try {
			if (rs.next()) {
				Integer i = rs.getInt("VSPIELE");
				MySQL.closeResultset(rs);
				return i;
			}
		} catch (SQLException ignored) {
		}
		MySQL.closeResultset(rs);
		return 0;
	}

	public static Integer getWonneneSpiele(String uuid) {
		ResultSet rs = Cores.getInstance().mysql.query("select * from Cores where UUID= '" + uuid + "'");
		try {
			if (rs.next()) {
				Integer i = rs.getInt("GESPIELE");
				MySQL.closeResultset(rs);
				return i;
			}
		} catch (SQLException ignored) {
		}
		MySQL.closeResultset(rs);
		return 0;
	}

	public static Integer getGespielteSpiele(String uuid) {
		ResultSet rs = Cores.getInstance().mysql.query("select * from Cores where UUID= '" + uuid + "'");
		try {
			if (rs.next()) {
				Integer i = rs.getInt("GSPIELE");
				MySQL.closeResultset(rs);
				return i;
			}
		} catch (SQLException ignored) {
		}
		MySQL.closeResultset(rs);
		return 0;
	}

	public static void setGespielteSpiele(String uuid, Integer kills) {
		Cores.getInstance().mysql.update("UPDATE Cores SET GSPIELE= '" + kills + "' WHERE UUID= '" + uuid + "';");
	}

	public static void setVerloreneSpiele(String uuid, Integer kills) {
		Cores.getInstance().mysql.update("UPDATE Cores SET VSPIELE= '" + kills + "' WHERE UUID= '" + uuid + "';");
	}

	public static void setGewonneSpiele(String uuid, Integer kills) {
		Cores.getInstance().mysql.update("UPDATE Cores SET GESPIELE= '" + kills + "' WHERE UUID= '" + uuid + "';");
	}

	public static void addGespielteSpiele(String uuid, Integer kills) {
		setGespielteSpiele(uuid, getGespielteSpiele(uuid) + kills);
	}

	public static void addGewonneSpiele(String uuid, Integer kills) {
		setGewonneSpiele(uuid, getWonneneSpiele(uuid) + kills);
	}

	public static void addVerloreneSpiele(String uuid, Integer kills) {
		setVerloreneSpiele(uuid, getVerloreneSpiele(uuid) + kills);
	}

}
