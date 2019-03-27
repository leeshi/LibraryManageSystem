package tk.doubilishi.Dao;

import java.sql.*;
import java.util.LinkedList;

/*
 * 该类负责管理用户借书的情况
 */
public class BrrowDao {
	private Connection conn;
	
	/*
	 * Connection变量的构造器，提供一个数据库连接对象以便操作数据库
	 */
	public BrrowDao(Connection conn) {
		this.conn = conn;
	}
	
	/*
	 * 通过u_id,b_id进行借阅
	 * @param int u_id,String b_id
	 */
	public int brrowBook(int u_id,String b_id) throws SQLException {
		String sql = "insert into t_brrow values ("+u_id+",'"+b_id+"')";
		System.out.println(sql);
		Statement state = conn.createStatement();
		
		return state.executeUpdate(sql);
	}
	
	/*
	 * 通过u_id,b_id进行还书操作
	 * @param int u_id,String b_id
	 * @throws SQLException ,可能会因为并不存在记录而抛出错误
	 */
	public int handInBook(int u_id,String b_id) throws SQLException {
		String sql = "delete from t_brrow where b_id='"+b_id+"' and "+"u_id="+u_id;
		Statement state = conn.createStatement();
		
		return state.executeUpdate(sql);
	}
	
	/*
	 * 查看一个用户借的所有书籍
	 * @param int u_id
	 * @return String,ids of books
	 */
	public LinkedList<String> getBrrowedBooks(int u_id) throws SQLException{
		String sql = "select * from t_brrow where u_id="+u_id;
		Statement state = conn.createStatement();
		
		ResultSet rs = state.executeQuery(sql);
		LinkedList<String> bList = new LinkedList<>();
		
		while(rs.next()) {
			bList.add(rs.getString(2));
		}
		
		return bList;
	}	
}
