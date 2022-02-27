package me.javasyntaxerror.methods.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

	private final String HOST;
	private final String DATABASE;
	private final String USER;
	private final String PASSWORD;

	private Connection con;

	public MySQL(String host, String database, String user, String password) {
		this.HOST = host;
		this.DATABASE = database;
		this.USER = user;
		this.PASSWORD = password;

		connect();
	}

	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true",
					USER, PASSWORD);
			System.out.println("[MySQL] Connected to MySQL");
		} catch (SQLException e) {
			System.out.println("[MySQL] Connection to MySQL failed! Error:" + e.getMessage());
		}
	}

	public void close() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("[MySQL] Error terminating connection to MySQL! Error: " + e.getMessage());
		}
	}

	public void update(String qry) {
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(qry);
			st.executeUpdate(qry);
		} catch (Exception e) {
			connect();
			System.err.println(e);
		}
		closeStatement(st);
	}

	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			PreparedStatement st = con.prepareStatement(qry);
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
		return rs;
	}

	public static void closeStatement(PreparedStatement st) {
		if (st != null)
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void closeResultset(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
