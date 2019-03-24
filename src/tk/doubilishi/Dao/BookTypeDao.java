package tk.doubilishi.Dao;

import java.sql.*;
import java.util.LinkedList;

import tk.doubilishi.Model.BookType;
import tk.doubilishi.Util.DbUtil;

public class BookTypeDao {
	private Connection conn;
	
	public BookTypeDao(Connection conn) {
		this.conn = conn;
	}
	
	/*
	 * add a book type
	 */
	public int addType(BookType bt) throws SQLException {
		String sql = "insert into bookType (typeID,typeName,typeCat) values (?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, bt.getTypeId());
		pstmt.setString(2, bt.getTypeName());
		pstmt.setString(3, bt.getTypeCat());

		return pstmt.executeUpdate();
	}
	/*
	 * delete a type
	 */
	public int deleteType(int typeID) throws SQLException {
		String sql = "delete from bookType where typeID="+typeID;
		Statement state = conn.createStatement();
		
		return state.executeUpdate(sql);
	}
	
	/*
	 * update info of a type
	 * @return the count of changed rows
	 */
	public int updateType(BookType bt) throws SQLException {
		String sql = "update bookType set typeName=?,typeCat=? where typeID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, bt.getTypeName());
		pstmt.setString(2, bt.getTypeCat());
		pstmt.setInt(3, bt.getTypeId());
		
		return pstmt.executeUpdate();
	}
	
	public BookType searchType(int typeID) throws SQLException {
		String sql = "select * from bookType where typeID="+typeID;
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next())
			return new BookType(rs.getInt("typeID"),rs.getString("typeName"),rs.getString("typeCat"));
		else
			return null;
	}
	
	/*
	 * getType,if not such type,return null,otherwise return the array
	 */
	public LinkedList<BookType> searchType(String typeName) throws SQLException {
		String sql = "select * from bookType where typeName like '%"+typeName+"%'";
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		
		LinkedList<BookType> btList = new LinkedList<>();
		while(rs.next()) {
			btList.add(new BookType(rs.getInt("typeID"),rs.getString("typeName"),
					rs.getString("typeCat")));
		}
		return btList;
	}
	
	/*
	 * 获取所有的类别
	 */
	public LinkedList<BookType> getAllType() throws SQLException {
		String sql = "select * from bookType";
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		
		LinkedList<BookType> btList = new LinkedList<>();
		while(rs.next()) {
			btList.add(new BookType(rs.getInt("typeID"),rs.getString("typeName"),
					rs.getString("typeCat")));
		}
		return btList;
	}
	
	/*for test*/
	public static void main(String...args) throws SQLException {
		BookTypeDao bDao = new BookTypeDao(new DbUtil().getConn());
		bDao.addType(new BookType(0,"测试用数据","test"));
		System.out.println("删除前");
		for(BookType bt:bDao.searchType(""))
			System.out.println(bt);
		System.out.println("删除后");
		bDao.deleteType(0);
		for(BookType bt:bDao.searchType(""))
			System.out.println(bt);
		System.out.println("用id搜索");
		System.out.println(bDao.searchType(2));
		System.out.println("用名称搜索");
		for(BookType bt:bDao.searchType("计算机"))
			System.out.println(bt);
		System.out.println("错误更新，更新条目\n函数的返回值是");
		System.out.println(bDao.updateType(new BookType(0,"","")));
		System.out.println("正常更新，更新条目\n函数的返回值是");
		System.out.println(bDao.updateType(new BookType(1,"计算机/电脑","介绍有关计算机的技术及原理的书籍")));
	}
}
