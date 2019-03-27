package tk.doubilishi.Dao;

import java.sql.*;
import java.util.LinkedList;

/*
 * ���ฺ������û���������
 */
public class BrrowDao {
	private Connection conn;
	
	/*
	 * Connection�����Ĺ��������ṩһ�����ݿ����Ӷ����Ա�������ݿ�
	 */
	public BrrowDao(Connection conn) {
		this.conn = conn;
	}
	
	/*
	 * ͨ��u_id,b_id���н���
	 * @param int u_id,String b_id
	 */
	public int brrowBook(int u_id,String b_id) throws SQLException {
		String sql = "insert into t_brrow values ("+u_id+",'"+b_id+"')";
		System.out.println(sql);
		Statement state = conn.createStatement();
		
		return state.executeUpdate(sql);
	}
	
	/*
	 * ͨ��u_id,b_id���л������
	 * @param int u_id,String b_id
	 * @throws SQLException ,���ܻ���Ϊ�������ڼ�¼���׳�����
	 */
	public int handInBook(int u_id,String b_id) throws SQLException {
		String sql = "delete from t_brrow where b_id='"+b_id+"' and "+"u_id="+u_id;
		Statement state = conn.createStatement();
		
		return state.executeUpdate(sql);
	}
	
	/*
	 * �鿴һ���û���������鼮
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
