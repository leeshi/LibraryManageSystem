package tk.doubilishi.Dao;

import java.sql.*;
import java.util.LinkedList;

import tk.doubilishi.Util.DbUtil;

public class BrrowDao {
	private Connection conn;
	
	public BrrowDao(Connection conn) {
		this.conn = conn;
	}
	
	public int returnBook(String b_id) throws SQLException {
		String sql = "delete from t_brrow where brrowed_id='"+b_id+"'";
		Statement state = conn.createStatement();
		
		return state.executeUpdate(sql);
	}
	public int brrowBook(int u_id,String b_id) throws SQLException {
		String sql = "insert into t_brrow values ("+u_id+",'"+b_id+"')";
		Statement state = conn.createStatement();
		return state.executeUpdate(sql);
	}
	
	/*
	 * 获取一个用户借的所有书
	 * @param u_id
	 * @return 一个线性表，用户没有借书，就返回空表
	 */
	public LinkedList<String> inquireUser(int u_id) throws SQLException {
		String sql = "select brrowed_id from t_brrow where brrow_id="+u_id;
		Statement state = conn.createStatement();
		LinkedList<String> bList = new LinkedList<>();
		ResultSet rs = state.executeQuery(sql);
		while(rs.next())
			bList.add(rs.getString("brrowed_id"));
		return bList;
	}
	
	/*
	 * 获取一个用户所借的书
	 * @param u_id
	 * @return -1如果没有用户借此书,其余return用户的id
	 */
	public int inquireBook(String b_id) throws SQLException {
		String sql = "select brrow_id from t_brrow where brrowed_id='"+b_id+"'";
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while(rs.next())
		{
			return rs.getInt("brrow_id");
		}
		return -1;
	}
	
	public static void main(String...args) throws SQLException {
		BrrowDao bDao = new BrrowDao(new DbUtil().getConn()); 
		System.out.println("用户开始借书");
		bDao.brrowBook(1, "PIG2019");
		bDao.brrowBook(1, "3");
		System.out.println(bDao.inquireUser(1));
		System.out.println(bDao.inquireBook("PIG2019")+" 借了PIG2019");
		System.out.println("用户开始还书");
		bDao.returnBook("PIG2019");
		bDao.returnBook("3");
		System.out.println(bDao.inquireUser(1));
	}
}
