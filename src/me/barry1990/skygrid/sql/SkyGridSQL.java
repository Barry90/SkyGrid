package me.barry1990.skygrid.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.barry1990.utils.BarrysLogger;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SkyGridSQL {
	


	private static SkyGridSQL sharedinstance;
	private static Connection connection;
	private static final String DB_PATH = "plugins/skygrid/skygrid.db";
	
	
	////////////////////////////////////////
	// MESSAGES
	////////////////////////////////////////
	
	private static final String HOME_NOT_FOUND = "You have no home named %s.";
	private static final String WELCOME = "Welcome to your new home %s.";
	private static final String MOVED_HOME = "You have moved your home %s.";
	
	
	////////////////////////////////////////
	// STATIC DATABASE VALUES
	////////////////////////////////////////
	
	//query	
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final String INSERT_INTO = "INSERT INTO ";
	private static final String GET_ALL = "SELECT * FROM ";
	private static final String SELECT = "SELECT ";
	private static final String FROM = " FROM ";
	private static final String WHERE = " WHERE ";
	private static final String AND = " AND ";
	
	//helper
	private static final String Q = "\'";
	private static final String IS = " = ";
	
	//player-table
	private static final String PLAYER_TABLE = "player";	
	private static final String P_PID = "pid";
	private static final String P_UUID = "uuid";
	
	//homes-table
	private static final String HOMES_TABLE = "homes";
	private static final String H_HID = "hid";
	private static final String H_NAME = "name";
	private static final String H_X = "x";
	private static final String H_Y = "y";
	private static final String H_Z = "z";
	private static final String H_YAW = "yaw";
	private static final String H_PITCH = "pitch";
	private static final String H_PLAYER_PID = "player_pid";	
	
	//invite-table
	private static final String INVITE_TABLE = "invite";
	
	
	////////////////////////////////////////
	// DRIVER CHECK
	////////////////////////////////////////
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			BarrysLogger.error("Fehler beim Laden des JDBC-Treibers");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////
	// SHARED INSTANCE
	////////////////////////////////////////
	
	public static SkyGridSQL sharedInstance() {		
		return (sharedinstance != null) ? sharedinstance : (sharedinstance = new SkyGridSQL());
	}
	
	private SkyGridSQL() {
		
		//create and open connection to the database		
		try {

			BarrysLogger.info(this,"Creating Connection to Database...");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
			if (!connection.isClosed())
				BarrysLogger.info(this,"...Connection established");
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		//add shutdownhook to close connection when server stops
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {

				try {
					if (!connection.isClosed() && connection != null) {
						connection.close();
						if (connection.isClosed())
							BarrysLogger.info(this,"Connection to Database closed");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		//prepare database
		this.prepareDatabase();

	}

	////////////////////////////////////////
	// DATABASE
	////////////////////////////////////////
	
	public void prepareDatabase() {

		this.createDatabaseTables();
		
	}
	
	private void createDatabaseTables() {
		try {
			Statement stmt = connection.createStatement();
			
			// player table
			stmt.executeUpdate(CREATE_TABLE + PLAYER_TABLE 
					+ "(" + P_PID + " integer PRIMARY KEY AUTOINCREMENT, "
					+ P_UUID + " VARCHAR(40), "
					+ "UNIQUE(" + P_UUID + ") ON CONFLICT IGNORE);"
					);
			BarrysLogger.info(this, PLAYER_TABLE + "-table check.");
			
			// homes table
			stmt.executeUpdate(CREATE_TABLE + HOMES_TABLE
					+ "(" + H_HID + " integer PRIMARY KEY AUTOINCREMENT, "
					+ H_NAME + " VARCHAR(128), "
					+ H_X + " double, "
					+ H_Y + " double, "
					+ H_Z + " double, "
					+ H_YAW + " float, "
					+ H_PITCH + " float, "
					+ H_PLAYER_PID + " integer REFERENCES player ON DELETE RESTRICT,"
					+ "UNIQUE(" + H_NAME +"," + H_PLAYER_PID + ") ON CONFLICT REPLACE);"
					);
			BarrysLogger.info(this, HOMES_TABLE + "-table check.");
			
			// invite table			
			stmt.executeUpdate(CREATE_TABLE + INVITE_TABLE 
					+ "(iid integer PRIMARY KEY AUTOINCREMENT, "
					+ "homes_hid integer REFERENCES homes ON DELETE RESTRICT,"
					+ "player_pid integer REFERENCES player ON DELETE RESTRICT);"
					);
			BarrysLogger.info(this, INVITE_TABLE + "-table check.");			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////
	// DATABASE GLOBAL ACCESS
	////////////////////////////////////////
	
	
	//player-table
	
	public void addPlayer(Player p) {
		try {
			
			PreparedStatement ps = connection.prepareStatement(INSERT_INTO + PLAYER_TABLE + "(" + P_UUID + ") VALUES (?);");
			ps.setString(1, p.getUniqueId().toString());
			ps.addBatch();
			
			connection.setAutoCommit(false);
			ps.executeBatch();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getPKfromPlayer(Player p) throws SQLException {
		int ret = 0;
		String uuid = p.getUniqueId().toString();
		
		ResultSet rs = this.executeQuery(SELECT + P_PID + FROM + PLAYER_TABLE + WHERE + P_UUID + IS + Q + uuid + Q +";");			
		if (rs.next()) {
			ret = rs.getInt(P_PID);
		}			
		rs.close();	
		
		return ret;
	}
	
	//homes-table
	
	public void addHome(Player p, Location loc, String name) {
		try {
			
			boolean home_exists = this.homeExists(p, name);
			
			PreparedStatement ps = connection.prepareStatement(INSERT_INTO + HOMES_TABLE 
					+ "(" + H_NAME + "," 
					+ H_X + "," 
					+ H_Y + "," 
					+ H_Z + "," 
					+ H_YAW + "," 
					+ H_PITCH + "," 
					+ H_PLAYER_PID 
					+ ") VALUES (?,?,?,?,?,?,?);");
			
			ps.setString(1, name);
			ps.setDouble(2, loc.getX());
			ps.setDouble(3, loc.getY());
			ps.setDouble(4, loc.getZ());
			ps.setFloat(5, loc.getYaw());
			ps.setFloat(6, loc.getPitch());
			ps.setInt(7, this.getPKfromPlayer(p));
			
			ps.addBatch();
			
			connection.setAutoCommit(false);
			ps.executeBatch();
			connection.setAutoCommit(true);
			
			p.sendMessage(String.format(home_exists ? MOVED_HOME : WELCOME, name));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Location getHome(Player p, String name) {
		Location ret = null;
		try {
			ResultSet rs = this.executeQuery(GET_ALL + HOMES_TABLE + WHERE + H_NAME + IS + Q + name + Q + AND + H_PLAYER_PID + IS + String.valueOf(this.getPKfromPlayer(p)) + ";");
			
			if (rs.next()) {				
				ret = new Location(p.getWorld(), rs.getDouble(H_X), rs.getDouble(H_Y), rs.getDouble(H_Z), rs.getFloat(H_YAW), rs.getFloat(H_PITCH));
			} else {
				p.sendMessage(String.format(HOME_NOT_FOUND, name));
			}
			rs.close();
		} catch (SQLException e) {
			BarrysLogger.error(this,"Couldn't handle DB-Query");
			e.printStackTrace();
		}
		return ret;
	}
	
	private boolean homeExists(Player p, String name) throws SQLException {
		
		boolean result = false;
		ResultSet rs = this.executeQuery(SELECT + H_HID + FROM + HOMES_TABLE 
				+ WHERE + H_NAME + IS + Q + name + Q 
				+ AND + H_PLAYER_PID + IS + String.valueOf(this.getPKfromPlayer(p)) + ";");
		
		result = rs.next();
		rs.close();
		
		return result;
		
	}
	
	////////////////////////////////////////
	// DATABASE PRIVATE HELPER
	////////////////////////////////////////
	
	private ResultSet executeQuery(String query) throws SQLException {
		Statement stmt = connection.createStatement();
		BarrysLogger.info(this,"query",query);
		return stmt.executeQuery(query);
	}
		
	private boolean tableExists(String tablename) {

		boolean result = false;

		try {

			DatabaseMetaData meta = connection.getMetaData();
			ResultSet res = meta.getTables(null, null, tablename, null);
			result = res.next();
			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
}
