package tk.doubilishi.Util;

import java.sql.*;

/*
 * This class is for the convenience of accessing the provided database
 */

public class DbUtil {
	private final String UserName = "root";
	private final String passwd = "LHmysql";
	private final String jdbcName = "com.mysql.cj.jdbc.Driver";
	private String DbUrl;
	private Connection conn;
	
	public DbUtil(String DbName) {  
		DbUrl = "jdbc:mysql://localhost:3306/"+DbName+"?serverTimezone=GMT%2B8";
	}
	public DbUtil(Connection conn) {
		this.conn = conn;
	}
	/*
	 * 连接默认的数据库
	 */
	public DbUtil() {
		DbUrl = "jdbc:mysql://localhost:3306/LM?serverTimezone=GMT%2B8";
	}
	
	/*
	 * connecting to the database
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConn() throws SQLException {
		try {
			Class.forName(jdbcName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(DbUrl, UserName, passwd);
	}
	
	/*
	 *check the state of the connection with integer 0~3 
	 * @return 0,1,2
	 * 1=correct,0=closed,2=null,3=exception
	 * @
	 */
	public int ConnectionState(){
		try {
			if(conn==null)
				return 2;
			else if(conn.isClosed())
				return 0;
			else
				return 1;
		}catch(SQLException e) {e.printStackTrace();}
		return 3;
	}
	
	/*
	 * close the Connection
	 */
	public  void closeConn() throws SQLException {
		if(conn!=null) {
			conn.close();
			System.out.println("You haven't connected to the database,yet!");
		}
		else
			System.out.println("Database has been closed!");
	}
	
	/*
	 * main:for cell test
	 */
	public static void main(String...args) {
		
	}
}
