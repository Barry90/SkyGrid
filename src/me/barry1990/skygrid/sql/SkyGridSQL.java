package me.barry1990.skygrid.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import me.barry1990.skygrid.SkyGrid;
import me.barry1990.utils.BarrysLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SkyGridSQL {
	
	public static final String SPAWN_POINT = "spawn point";
	
	private static SkyGridSQL sharedinstance;
	private static Connection connection;
	private static final String DB_NAME = "skygrid.db";
	
	
	////////////////////////////////////////
	// MESSAGES
	////////////////////////////////////////
	
	private static final String HOMENAME_TOO_LONG = "§4Your homename is too long.";
	private static final String HOME_NOT_FOUND = "§4You have no home named §f%s§6.";
	private static final String PLAYER_HOME_NOT_FOUND = "§4%s has no home named §f%s§6.";
	private static final String WELCOME = "§6Welcome to your new home §f%s.";
	private static final String MOVED_HOME = "§6You have moved your home §f%s§6.";
	private static final String MOVED_SPAWN = "§6You have moved your spawn point.";
	private static final String TOO_MANY_HOMES = "§4You cannot have more than 3 homes.";
	private static final String DELETED_HOME = "§6You §4deleted §6your home §f%s§6.";
	private static final String NO_HOMES = "§6You dont have any homes yet. Use /sethome to set a home.";
	private static final String HOMES_LIST = "§6Your homes are: %s§6.";
	private static final String PLAYER_NOT_FOUND = "§4The player %s doesn't play SkyGrid yet.";
	private static final String ALREADY_INVITED = "§6The player §f%s§6 is already invited to your home §f%s§6.";
	private static final String INVITED = "§6You invited §f%s§6 to your home §f%s§6.";
	private static final String WAS_INVITED = "§6You have been invited to §f%s§6's home §f%s§6.";
	private static final String NOT_INVITED = "§4You are not invited to this home.";
	
	
	////////////////////////////////////////
	// STATIC DATABASE VALUES
	////////////////////////////////////////
	
	//query	
	
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final String DROP_TABLE = "DROP TABLE ";
	private static final String INSERT_INTO = "INSERT INTO ";
	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String GET_ALL = "SELECT * FROM ";
	private static final String COUNT = "SELECT COUNT(*) AS count FROM ";
	private static final String SELECT = "SELECT ";
	private static final String FROM = " FROM ";
	private static final String WHERE = " WHERE ";
	private static final String AND = " AND ";
	
	//helper
	private static final String Q = "\'";
	private static final String IS = " = ";
	
	//player-table
	private static final String PLAYER_TABLE = "player";	
	private static final String P_NAME = "name";
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
	private static final String I_IID = "iid";
	private static final String I_HOMES_HID = "homes_hid";
	private static final String I_PLAYER_PID = "player_pid";
	
	
	
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
			String dbpath = SkyGrid.sharedInstance().getDataFolder() + File.separator + DB_NAME;
			if (new File(dbpath).getParentFile().mkdirs())				
				BarrysLogger.info(this,"Directory created for Database.");
			
			BarrysLogger.info(this,"Creating Connection to Database...");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
			if (!connection.isClosed())
				BarrysLogger.info(this,"...Connection established");
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		//prepare database
		this.createDatabaseTables();

	}
	
	public void close() {
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

	////////////////////////////////////////
	// DATABASE
	////////////////////////////////////////
	
	private void createDatabaseTables() {
		try {
			Statement stmt = connection.createStatement();
			
			// player table
			stmt.executeUpdate(CREATE_TABLE + PLAYER_TABLE 
					+ "(" + P_PID + " integer PRIMARY KEY AUTOINCREMENT, "
					+ P_NAME + " VARCHAR(16), "
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
					+ H_PLAYER_PID + " integer REFERENCES " + PLAYER_TABLE + " ON DELETE RESTRICT,"
					+ "UNIQUE(" + H_NAME +"," + H_PLAYER_PID + ") ON CONFLICT REPLACE);"
					);
			BarrysLogger.info(this, HOMES_TABLE + "-table check.");
			
			// invite table			
			stmt.executeUpdate(CREATE_TABLE + INVITE_TABLE 
					+ "(" + I_IID + " integer PRIMARY KEY AUTOINCREMENT, "
					+ I_HOMES_HID + " integer REFERENCES " + HOMES_TABLE + " ON DELETE RESTRICT,"
					+ I_PLAYER_PID + " integer REFERENCES " + PLAYER_TABLE + " ON DELETE RESTRICT);"
					);
			BarrysLogger.info(this, INVITE_TABLE + "-table check.");			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resetDatabaseTables() {
		try {
			Statement stmt = connection.createStatement();
			
			// player table
			stmt.executeUpdate(DROP_TABLE + PLAYER_TABLE);
			BarrysLogger.info(this, PLAYER_TABLE + "-table dropped.");
			
			// homes table
			stmt.executeUpdate(DROP_TABLE + HOMES_TABLE);
			BarrysLogger.info(this, HOMES_TABLE + "-table dropped.");
			
			// invite table			
			stmt.executeUpdate(DROP_TABLE + INVITE_TABLE);
			BarrysLogger.info(this, INVITE_TABLE + "-table dropped.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.createDatabaseTables();
	}
	
	////////////////////////////////////////
	// PLAYER TABLE METHODS
	////////////////////////////////////////
	
	/**
	 * Adds a player to the player-table
	 * @param p The Player
	 */
	public void addPlayer(Player p) {
		try {
			
			PreparedStatement ps = connection.prepareStatement(INSERT_INTO + PLAYER_TABLE + "(" + P_UUID + "," + P_NAME + ") VALUES (?,?);");
			ps.setString(1, p.getUniqueId().toString());			
			ps.setString(2, p.getName());			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns a SELECT query to get the PRIMARY KEY value of the player in the player-table by a given playername
	 * @param p The Player
	 * @return SELECT query to get the PRIMARY KEY value of the player. Use for INNER SELECT.
	 * @throws SQLException
	 */
	private String getPID(Player p) throws SQLException {
		return "(" + SELECT + P_PID + FROM + PLAYER_TABLE + WHERE + P_UUID + IS + Q + p.getUniqueId().toString() + Q  + ")";
	}
	
	/**
	 * Returns a SELECT query to get the PRIMARY KEY value of the player in the player-table by a given playername
	 * @param name The name of the player
	 * @return SELECT query to get the PRIMARY KEY value of the player. Use for INNER SELECT.
	 * @throws SQLException
	 */
	private String getPID(String playername) throws SQLException {
		return "(" + SELECT + P_PID + FROM + PLAYER_TABLE + WHERE + P_NAME + IS + Q + playername + Q  + ")";
	}
	
	/**
	 * Returns the pid of a player
	 * @param playername The name of the player.
	 * @return The pid of the player or 0 if the player does not exists in the player-table
	 * @throws SQLException
	 */
	private int getPIDasInt(String playername) throws SQLException {
		int ret = 0;
		ResultSet rs = this.executeQuery(SELECT + P_PID + FROM + PLAYER_TABLE + WHERE + P_NAME + IS + Q + playername + Q + ";");
		if (rs.next()) {
			ret = rs.getInt(P_PID);
		}
		rs.close();
		return ret;
	}
	
	////////////////////////////////////////
	// HOMES TABLE METHODS
	////////////////////////////////////////
	
	/**
	 * Adds a home
	 * @param p The player the home belongs to.
	 * @param loc The location of the new home.
	 * @param homename The name of the home.
	 */
	public void addHome(Player p, Location loc, String homename) {
		try {
			
			if (homename.length() > 128) {
				p.sendMessage(HOMENAME_TOO_LONG);
				return;
			}
			
			String name = this.saveString(homename);
						
			boolean home_exists = this.homeExists(p, name);
			
			if (!home_exists) {
				
				if (this.getHomesCount(p) >= 4) {
					p.sendMessage(TOO_MANY_HOMES);
					return;
				}
				
			}
			
			this.executeUpdate(String.format(Locale.US, INSERT_INTO + HOMES_TABLE + "(" + H_NAME + "," + H_X + "," + H_Y + "," + H_Z + "," + H_YAW + "," + H_PITCH + "," + H_PLAYER_PID + ") "
					+ "VALUES ("+Q+"%s"+Q+",%f,%f,%f,%f,%f,%s);", 
					name, loc.getX(), loc.getY() , loc.getZ(),loc.getYaw(), loc.getPitch(), this.getPID(p)));
			
			if (!home_exists && homename.equals(SPAWN_POINT))
				return;
			
			p.sendMessage(String.format(home_exists ? (homename.equals(SPAWN_POINT) ? MOVED_SPAWN : MOVED_HOME) : WELCOME, homename));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the location of a home for a player 
	 * @param p The player.
	 * @param homename The players home name.
	 * @return The location of the home or null if no home was found.
	 */
	public Location getHome(Player p, String homename) {
		Location ret = null;
		try {
			String name = this.saveString(homename);
			ResultSet rs = this.getResultSetForHome(this.getPID(p), name);
			
			if (rs.next()) {				
				ret = new Location(Bukkit.getWorld("world") /*p.getWorld()*/, rs.getDouble(H_X), rs.getDouble(H_Y), rs.getDouble(H_Z), rs.getFloat(H_YAW), rs.getFloat(H_PITCH));
				rs.close();
			} else {
				rs.close();
				if (homename.equals("home") && getHomesCount(p) == 1) {
					p.sendMessage(NO_HOMES);
				} else {
					p.sendMessage(String.format(HOME_NOT_FOUND, homename));
				}
			}
			
		} catch (SQLException e) {
			BarrysLogger.error(this,"Couldn't handle DB-Query");
			e.printStackTrace();
		}
		return ret;
	}
		
	/**
	 * Count the homes of a player
	 * @param p The player.
	 * @return The number of homes of the player.
	 */
	public int getHomesCount(Player p) {
		int result = 0;
		try {
			ResultSet rs = this.executeQuery(COUNT + HOMES_TABLE + WHERE + H_PLAYER_PID + IS + this.getPID(p) + ";");
			result = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			BarrysLogger.error(this,"Couldn't handle DB-Query");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Deletes a home of a player and all invites to that home.
	 * @param p The Player.
	 * @param name The name of the home.
	 */
	public void deleteHome(Player p, String homename) {
		try {
			String name = this.saveString(homename);
			if (this.homeExists(p, name)) {
				//delete all invites
				PreparedStatement ps =  connection.prepareStatement(DELETE_FROM + INVITE_TABLE + WHERE + I_HOMES_HID + IS + this.getHID(p, name) + ";");
				ps.executeUpdate();
				
				//delete the home
				ps =  connection.prepareStatement(DELETE_FROM + HOMES_TABLE + WHERE + H_NAME + IS + Q + name + Q + AND + H_PLAYER_PID + IS + this.getPID(p) +";");
				ps.executeUpdate();
				
				p.sendMessage(String.format(DELETED_HOME, homename));

			} else {
				p.sendMessage(String.format(HOME_NOT_FOUND, homename));
			}
			
		} catch (SQLException e) {
			BarrysLogger.error(this,"Couldn't handle DB-Query");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a list of all homes of a player
	 * @param p The player.
	 */
	public void getHomesList(Player p) {
		try {
			ResultSet rs = this.executeQuery(SELECT + H_NAME + FROM + HOMES_TABLE + WHERE + H_PLAYER_PID + IS + this.getPID(p) + ";");
			
			int count = 0;
			String homes = "§f";
			
			while (rs.next()) {
				if (!rs.getString(H_NAME).equals(SPAWN_POINT)) {
					if (count != 0) {
						homes = homes +"§6, §f";
					}
					homes = homes + rs.getString(H_NAME);
					count++;
				}
			}
			
			if (count > 0) {
				p.sendMessage(String.format(HOMES_LIST, homes));
			} else {
				p.sendMessage(NO_HOMES);
			}
			
		} catch (SQLException e) {
			BarrysLogger.error(this,"Couldn't handle DB-Query");
			e.printStackTrace();
		}
	}
	
		
	/**
	 * Checks if a specific home exists.
	 * @param p The player the home belongs to.
	 * @param name The name of that home.
	 * @return true if the home exists.
	 * @throws SQLException
	 */
	private boolean homeExists(Player p, String homename) throws SQLException {
		return executehomeExists(this.getPID(p), homename);		
	}
	
	/**
	 * Checks if a specific home exists.
	 * @param p The name of the player the home belongs to.
	 * @param name The name of that home.
	 * @return true if the home exists.
	 * @throws SQLException
	 */
	private boolean homeExists(String playername, String homename) throws SQLException {
		return executehomeExists(this.getPID(playername), homename);
	}
	
	/**
	 * Checks if a specific home exists
	 * @param pid_query INNER SELECT query to get a player pid
	 * @param homename The name of the home
	 * @return true if the home exists.
	 * @throws SQLException
	 */
	private boolean executehomeExists(String pid_query, String homename) throws SQLException {
		boolean result = false;
		ResultSet rs = this.executeQuery(SELECT + H_HID + FROM + HOMES_TABLE 
				+ WHERE + H_NAME + IS + Q + homename + Q 
				+ AND + H_PLAYER_PID + IS + pid_query + ";");
		
		result = rs.next();
		rs.close();
		
		return result;
	}
	
	/**
	 * Gets a ResultSet which contains a row of the homelist
	 * @param pid_query INNER SELECT query to get a player pid
	 * @param homename The name of the home
	 * @return a ResultSet
	 * @throws SQLException
	 */
	private ResultSet getResultSetForHome(String pid_query, String homename) throws SQLException {
		return this.executeQuery(GET_ALL + HOMES_TABLE + WHERE + H_NAME + IS + Q + homename + Q + AND + H_PLAYER_PID + IS + pid_query + ";");
	}
	
	/**
	 * Returns a SELECT query to get the PRIMARY KEY value of the home in the homes-table by a given player and homename
	 * @param p The player the home belongs to.
	 * @param homename The name of the home
	 * @return SELECT query to get the PRIMARY KEY value of the home. Use for INNER SELECT.
	 * @throws SQLException
	 */
	private String getHID(Player p, String homename) throws SQLException {
		return "(" + SELECT + H_HID + FROM + HOMES_TABLE + WHERE 
				+ H_NAME + IS + Q + homename + Q 
				+ AND + H_PLAYER_PID + IS + this.getPID(p) + ")"; 
	}
	
	/**
	 * Returns a SELECT query to get the PRIMARY KEY value of the home in the homes-table by a given playername and homename
	 * @param playername The name of the player the home belongs to.
	 * @param homename The name of the home
	 * @return SELECT query to get the PRIMARY KEY value of the home. Use for INNER SELECT.
	 * @throws SQLException
	 */
	private String getHID(String playername, String homename) throws SQLException {
		return "(" + SELECT + H_HID + FROM + HOMES_TABLE + WHERE 
				+ H_NAME + IS + Q + homename + Q 
				+ AND + H_PLAYER_PID + IS + this.getPID(playername) + ")"; 
	}
	
	////////////////////////////////////////
	// INVITES TABLE METHODS
	////////////////////////////////////////

	/**
	 * Adds an invite for a home
	 * @param p The player the home belongs to.
	 * @param playername The name of the player that is invited
	 * @param homename The name of the home
	 */
	public void addInvite(Player p, String playername, String homename) {

		try {
			// check if the player has that home
			if (!this.homeExists(p, homename)) {
				p.sendMessage(String.format(HOME_NOT_FOUND, homename));
				return;
			}

			// check if the playername exists
			if (this.getPIDasInt(playername) == 0) {
				p.sendMessage(String.format(PLAYER_NOT_FOUND, playername));
				return;
			}

			// check if already invited
			if (this.isInvitedToHome(p, homename, playername)) {
				p.sendMessage(String.format(ALREADY_INVITED, playername, homename));
				return;
			}

			//add the invite
			this.executeUpdate(String.format(INSERT_INTO + INVITE_TABLE + " (" + I_HOMES_HID + "," + I_PLAYER_PID + ") VALUES (%s,%s);", this.getHID(p, homename), this.getPIDasInt(playername)));

			p.sendMessage(String.format(INVITED, playername, homename));
			Player invitedPlayer = Bukkit.getPlayer(playername);
			if (invitedPlayer != null) {
				invitedPlayer.sendMessage(String.format(WAS_INVITED, p.getName(), homename));
			}

		} catch (SQLException e) {
			BarrysLogger.error(this, "Couldn't handle DB-Query");
			e.printStackTrace();
		}

	}

	/**
	 * Will return a Location for another players home if the player was invited to that home
	 * @param p The player that wants to teleport to the home
	 * @param playername The name of the player the home belongs to
	 * @param homename The name of the home
	 * @return The Location if the player (p) was invited to the home, otherwiese it will return null;
	 */
	public Location getInvitedHome(Player p, String playername, String homename) {

		Location ret = null;
		try {
			// check if the player has that home
			if (!this.homeExists(playername, homename)) {
				p.sendMessage(String.format(PLAYER_HOME_NOT_FOUND, playername, homename));
				return ret;
			}
			BarrysLogger.info("home exists");

			// check if invited
			if (!this.isInvitedToHome(p, homename, playername)) {
				p.sendMessage(NOT_INVITED);
				return ret;
			}
			BarrysLogger.info("invite okay");

			ResultSet rs = this.getResultSetForHome(this.getPID(playername), homename);

			if (rs.next()) {
				ret = new Location(Bukkit.getWorld("world") /* p.getWorld() */, rs.getDouble(H_X), rs.getDouble(H_Y), rs.getDouble(H_Z), rs.getFloat(H_YAW), rs.getFloat(H_PITCH));
			}
			rs.close();

		} catch (SQLException e) {
			BarrysLogger.error(this, "Couldn't handle DB-Query");
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Checks if a player is invited to a specific home
	 * @param p The player to test
	 * @param homename The homename
	 * @param playername The name of the player the home belongs to
	 * @return true if the player was invited
	 * @throws SQLException
	 */
	private boolean isInvitedToHome(Player p, String homename, String playername) throws SQLException {

		boolean result = false;
		ResultSet rs = this.executeQuery(SELECT + I_IID + FROM + INVITE_TABLE 
				+ WHERE + I_HOMES_HID + IS + this.getHID(playername, homename) 
				+ AND + I_PLAYER_PID + IS + this.getPID(p) + ";");

		result = rs.next();
		rs.close();

		return result;
	}

	////////////////////////////////////////
	// DATABASE PRIVATE HELPER
	////////////////////////////////////////

	private ResultSet executeQuery(String query) throws SQLException {
		Statement stmt = connection.createStatement();
		BarrysLogger.info(this, "query", query);
		return stmt.executeQuery(query);
	}
	private int executeUpdate(String query) throws SQLException {
		Statement stmt = connection.createStatement();
		BarrysLogger.info(this, "query", query);
		return stmt.executeUpdate(query);
	}
	private String saveString(String s) {
		s.replace("\\", "\\\\");
		s.replace("'", "\'");
		s.replace("\"", "\\\"");
		return s;
	}

	@SuppressWarnings("unused")
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
