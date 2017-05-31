package bankproject.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager {

	/**
     * Connect to a sample database
     */
	public static void connect(){
		Connection conn = null;
		try{
			String url = "jdbc:sqlite:"+getSQLiteDBPath();
			//create a conexion to the database
			conn = DriverManager.getConnection(url);
			
			System.out.println("Connection to SQLite has been established");
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}finally {
			try{
				if (conn != null) {
					conn.close();
				}
			}catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
	}

	private static String getSQLiteDBPath() {
		return System.getProperty("user.dir") + System.getProperty("file.separator") + "data.sqlite"+ System.getProperty("file.separator") + "data.db";
		
	}
}


