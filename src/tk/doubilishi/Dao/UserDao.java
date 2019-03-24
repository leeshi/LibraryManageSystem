package tk.doubilishi.Dao;

import java.sql.*;

import tk.doubilishi.Model.User;
import tk.doubilishi.Util.DbUtil;

public class UserDao {
	private Connection conn;
	
	public UserDao(Connection conn) {
		this.conn = conn;
	}
	/*
	 * update passed for a user
	 */
	public int setPasswd(User user) throws SQLException {
		String sql = "update t_user set passwd=? where u_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, user.getPasswd());
		pstmt.setInt(2, user.getId());
		
		return pstmt.executeUpdate();
	}
	
	/*
	 * update name of a user
	 */
	public int setName(int u_id,String u_name) throws SQLException {
		String sql = "update t_user set u_name=? where u_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, u_name);
		pstmt.setInt(2, u_id);
		
		return pstmt.executeUpdate();
	}
	/*
	 * 
	 */
	public int addUser(User user) throws SQLException {
		String sql = "insert into t_user (u_id,u_name) values (?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		pstmt.setString(2, user.getName());
		pstmt.setInt(1, user.getId());
		
		return pstmt.executeUpdate();
	}
	
	/*
	 * get a user info by id
	 */
	public User getUser(int u_id) throws SQLException {
		String sql = "select u_id,u_name,passwd from t_user where u_id="+u_id;
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		//only one
		while(rs.next()) {
			return new User(u_id,rs.getString("u_name"),rs.getString("passwd"));
		}
		return null;
	}
	
	
	public static void main(String...args) throws SQLException {
		UserDao uDao = new UserDao(new DbUtil().getConn());
		uDao.addUser(new User(3,"ºÚÄ¾","DEFAULT"));
		uDao.addUser(new User(4,"°×Ä¾","DEFAULT"));
		uDao.getUser(100);
	}
}
