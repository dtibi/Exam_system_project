package SQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSet;

import logic.*;
import ocsf.server.ServerGlobals;


public class DBMain {
	private String host;
	private String user;
	private String pass;
	private Connection conn;
	PreparedStatement prst;
	ResultSet rs;
	private String tableA = "";
	private String tableB= "";
	private String columnA= "";
	private String columnB= "";
	private String getActiveEams = new String(
			"SELECT * FROM "
			+ "(SELECT * FROM aes.activatedexams WHERE teacherid=?) B "
			+ "INNER JOIN aes.teachers ON teachers.teacherid=B.teacherid") ;
	private String getAllUsers = new String(
			"Select * FROM aes.users"
			);
	private String getAllTeachers = new String(
			"Select * FROM aes.users,aes.teachers WHERE userid=teacherid"
			);
	private String getAllStudents = new String(
			"Select * FROM aes.users,aes.students WHERE userid=teacherid"
			);
	private String getAllPrinciples = new String(
			"Select * FROM aes.users,aes.principles WHERE userid=teacherid"
			);
	private String login;
	/**
	 * creating a Database Class creates a connection to an SQLServer
	 * @param h the host address - could be a domain name or ip address (ex. "localhost/schema" or "192.168.1.15/schema")
	 * @param u username of the user that has access to the database host
	 * @param p password of the username.
	 */
	public DBMain(String h,String u,String p) {
		 try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			Globals.handleException(e);
		} catch (IllegalAccessException e) {
			Globals.handleException(e);
		} catch (ClassNotFoundException e) {
			Globals.handleException(e);
		}
		host=h;
		user=u;
		pass=p;
	    connect();
	}
	
	private void setLogin() {
		login = new String(
				"SELECT * " + 
				"FROM aes."+tableA+" , aes.users " + 
				"WHERE "+columnA+"=userid AND username=?");
	}
	
	/**
	 * try to make a connection to database 
	 * @return true if connection is valid otherwise returns false
	 */
	private boolean connect() {
		// TODO Auto-generated method stub
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+host,user,pass);
			return true;
		} catch (SQLException e) {
			Globals.handleException(e);
		}
		return false;
	}

	/**
	 * get connection 
	 * 
	 * @return Connection type variable that holds the connection to database
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * try to reconnect to database in case there is no valid connection
	 * @return true if connection is valid otherwise returns false
	 */
	public boolean reconnect() {
		return connect();
	}

	public void getTeachersActiveExams(Teacher t) {
		try {
			prst = conn.prepareStatement(getActiveEams);
			prst.setInt(1, t.getID());
			if (prst.execute())
				rs = prst.getResultSet();
			ArrayList<ActiveExam> ae = new ArrayList<ActiveExam>();
			while (rs.next()) {
				int examid = rs.getInt(1);
				int courseid = rs.getInt(2);
				int fieldid = rs.getInt(3);
				String code = rs.getString(4);
				int type = rs.getInt(5);
				int active = rs.getInt(6);
				int studentCount = rs.getInt(7);
				int zerocount = rs.getInt(8);
				ae.add(new ActiveExam(0, getActiveEams, getActiveEams, 0, 0, null));
			}
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		
	}

	public User UserLogIn(User u) {
		String type = null;
		try {
			tableA= "students";
			columnA = "studentid";
			setLogin();
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			System.out.println("About to excecute:"+prst);
			if (prst.execute()) {
				rs = prst.getResultSet();
				if (rs.next()) {
					int userid = rs.getInt(2);
					String username = rs.getString(3);
					String password = rs.getString(4);
					String fullname = rs.getString(5);
					if (!password.equals(u.getPassword())) return null;
					return new Student(userid,username,password,fullname,null);
				}
			}
			tableA= "teachers";
			columnA = "teacherid";
			setLogin();
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			if (prst.execute()) {
				rs = prst.getResultSet();
				if (rs.next()) {
					int userid = rs.getInt(2);
					String username = rs.getString(3);
					String password = rs.getString(4);
					String fullname = rs.getString(5);
					if (!password.equals(u.getPassword())) return null;
					return new Teacher(userid,username,password,fullname,null,null,null);
				}
			}
			tableA= "principles";
			columnA = "principleid";
			setLogin();
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			if (prst.execute()) {
				rs = prst.getResultSet();
				if (rs.next()) {
					int userid = rs.getInt(2);
					String username = rs.getString(3);
					String password = rs.getString(4);
					String fullname = rs.getString(5);
					if (!password.equals(u.getPassword())) return null;
					return new Principle(userid,username,password,fullname);
				}
			}
			System.out.println(type);
			return null;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
}
