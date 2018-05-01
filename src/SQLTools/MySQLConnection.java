package SQLTools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class MySQLConnection {
	
	static final String DB_URL = "jdbc:mysql://localhost/test";
	   //  Database credentials
	static final String USER = "root";
	static final String PASS = "1234";
	
	
	public static void main(String[] args) 
	{
		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("Error connectig to driver");
        }
        
        try 
        {
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
            System.out.println("SQL connection succeed");
            //createTableCourses(conn);
            printCourses(conn);
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	
	public static void printCourses(Connection con)
	{
		Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses;");
	 		while(rs.next())
	 		{
				 // Print out the values
				 System.out.println(rs.getString(1)+"  " +rs.getString(2));
			} 
			rs.close();
			//stmt.executeUpdate("UPDATE course SET semestr=\"W08\" WHERE num=61309");
		} catch (SQLException e) {e.printStackTrace();}
	}

	
	public static void createTableCourses(Connection con1){
		Statement stmt;
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate("create table courses(num int, name VARCHAR(40), semestr VARCHAR(10));");
			stmt.executeUpdate("load data local infile \"courses.txt\" into table courses");
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
}