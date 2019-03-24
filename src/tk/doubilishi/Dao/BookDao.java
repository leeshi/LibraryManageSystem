package tk.doubilishi.Dao;

import java.sql.*;
import java.util.*;

import tk.doubilishi.Model.*;
import tk.doubilishi.Util.*;

public class BookDao {
	private Connection conn;
	
	public BookDao(Connection conn) {
		this.conn = conn;
	}
	
	/*
	 * add a book,only when adding this book can set id for it
	 * @param Book
	 * @reutn int
	 * @throws 
	 */
	
	public int addBook(Book book) throws SQLException {
		String sql = "insert into t_book values (?,?,?,?,?,?)";
		PreparedStatement pstmt=(PreparedStatement) conn.prepareStatement(sql);
		pstmt.setString(2, book.getID());
		pstmt.setString(1, book.getName());
		pstmt.setString(3,book.getAuther());
		pstmt.setString(4, book.getDecs());
		pstmt.setFloat(5, book.getPrice());
		pstmt.setInt(6, book.getType());
		return pstmt.executeUpdate();
	}
	
	/*
	 * updata info of a book,id is const,if return 0,means that the update is fail
	 * @Book object
	 */
	public int updateBook(Book book) throws SQLException {
		String sql = "update t_book set b_name=?,b_auther=?,b_desc=?,b_price=?,b_type=? where b_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, book.getName());
		pstmt.setString(2,book.getAuther());
		pstmt.setString(3, book.getDecs());
		pstmt.setFloat(4, book.getPrice());
		pstmt.setInt(5, book.getType());
		pstmt.setString(6, String.valueOf(2));
		
		return pstmt.executeUpdate();
	}
	
	public Book getBook(String b_id) throws SQLException {
		String sql = "select * from t_book where b_id='"+b_id+"'";
		Statement state = conn.createStatement();
		
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()) 
			return new Book(rs.getString("b_name"),rs.getString("b_auther")
					,rs.getString("b_desc"),rs.getFloat("b_price"),rs.getInt("b_type"),rs.getString("b_id"));
		else
			return null;
	}
	
	/*
	 * update bookinfo with specied data
	 */
	public int updateBook(String b_id,String column_name,String data) throws SQLException {
		String sql = "update t_book set "+column_name+"=?"+"where b_id=?";
		//一定要注意''引号的搭配，在SQL中，字符串需要加上引号，而在preparedStatement中，他会自动地帮你加上''
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, data);
		pstmt.setString(2, b_id);
		
		return pstmt.executeUpdate();
	}
	
	/*
	 * delete a book from database
	 */
	public int deleteBook(String b_id) throws SQLException {
		String sql = "delete from t_book where b_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, b_id);
		
		return pstmt.executeUpdate();
	}
	
	/*
	 * search for books with name
	 * @return LinkedList<Book>
	 */
	public LinkedList<Book> searchWithName(String likeName) throws SQLException{    //通过模糊适配连接
		String sql = "select * from t_book where b_name like '%"+likeName+"%'";
		
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		LinkedList<Book> bList = new LinkedList<>();
		while(rs.next()) {
			bList.add(new Book(rs.getString("b_name"),rs.getString("b_auther")
					,rs.getString("b_desc"),rs.getFloat("b_price"),rs.getInt("b_type"),rs.getString("b_id")));
		}
		return bList;
	}
	
	/*
	 * search for books with auther
	 * @return LinkedList<Book>
	 */
	public LinkedList<Book> searchWithAuther(String likeAuther) throws SQLException{    //通过模糊适配连接
		String sql = "select * from t_book where b_auther like '%"+likeAuther+"%'";
		
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		LinkedList<Book> bList = new LinkedList<>();
		while(rs.next()) {
			bList.add(new Book(rs.getString("b_name"),rs.getString("b_auther")
					,rs.getString("b_desc"),rs.getFloat("b_price"),rs.getInt("b_type"),rs.getString("b_id")));
		}
		return bList;
	}
	
	public LinkedList<Book> searchWithType(int typeID) throws SQLException{
		String sql = "select * from t_book where b_type="+typeID;
		
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		LinkedList<Book> bList = new LinkedList<>();
		while(rs.next()) {
			new Book(rs.getString("b_name"),rs.getString("b_auther")
					,rs.getString("b_desc"),rs.getFloat("b_price"),rs.getInt("b_type"),rs.getString("b_id"));
		}
		return bList;
	}
	
	/*
	 * get all books
	 * @return LinkedList<Book>
	 */
	public LinkedList<Book> getAllBooks() throws SQLException{
		String sql = "select * from t_book";
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		
		LinkedList<Book> bList = new LinkedList<>();
		while(rs.next()) {
			bList.add(new Book(rs.getString("b_name"),rs.getString("b_auther")
					,rs.getString("b_desc"),rs.getFloat("b_price"),rs.getInt("b_type"),rs.getString("b_id")));
		}
		return bList;
	}
	/*
	 * get the number of book
	 * @return int 
	 * @throws SQLException
	 */
	public int countBook() throws SQLException {
		String sql = "select count(bID) from t_book";
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		rs.next();
		return rs.getInt("count(bID)");    //ResultSet是一个集合，在调用get方法之前，一定要先调用next()
	}
	
	public static void main(String...args) throws SQLException {
		DbUtil db = new DbUtil("LM");
		BookDao bdao = new BookDao(db.getConn());
		bdao.updateBook("PIG2019", "b_name", "辣鸡论");
		List<Book> aList = bdao.searchWithAuther("%");
		System.out.println(aList);
	}
}