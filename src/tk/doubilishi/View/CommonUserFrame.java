package tk.doubilishi.View;

import java.awt.Toolkit;
import java.sql.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import tk.doubilishi.Dao.*;
import tk.doubilishi.Model.Book;
import tk.doubilishi.Util.DbUtil;

import java.awt.Font;

public class CommonUserFrame extends JFrame{
	private Connection conn;
	private BrrowDao brrowDao;
	private BookDao bDao;
	private BookTypeDao typeDao;
	
	private LinkedList<Book> bList;
	private int u_id;      //记录当前用户
	private Object[][] data;    //储存数据，方便模型读取
	private String[] column_names= {"id","name","auther","description","price","type"};
	
	private JTable table;
	private JTextField idField;
	private JTextField autherField;
	private JTextField nameField;
	private JTextField typeField;
	private DefaultTableModel model;
	private JScrollPane scrollPane; 
	
	public CommonUserFrame(Connection conn,int u_id) {
		this.conn = conn;
		this.u_id = u_id;
		
		setFrameSize();
		

		getContentPane().setFont(new Font("YouYuan", Font.PLAIN, 30));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1084, 445);
		getContentPane().add(scrollPane);
		
		/*
		 * 各个按钮
		 */
		JButton brrowButton = new JButton("\u501F\u9605\u56FE\u4E66");
		brrowButton.setFont(new Font("YouYuan", Font.PLAIN, 30));
		brrowButton.setBounds(21, 466, 183, 54);
		brrowButton.addActionListener((e)->{
			int selected_row = table.getSelectedRow();
			if(selected_row<0)
				return;    //如果没有选中任何行，就退出
			String b_id = (String) model.getValueAt(selected_row,0);
			try {
				brrowDao.brrowBook(u_id, b_id);
			}catch(SQLException e1) {
				idField.setText("此书已被借阅，请重试");
				e1.printStackTrace();
			}
		});
		getContentPane().add(brrowButton);
		
		JButton inquireButton = new JButton("\u6240\u501F\u4E66\u7C4D");
		inquireButton.setFont(new Font("YouYuan", Font.PLAIN, 30));
		inquireButton.setBounds(21, 590, 183, 54);
		inquireButton.addActionListener((e)->{
			try {
				bList = new LinkedList<Book>();
				for(String b_id:brrowDao.getBrrowedBooks(u_id)) {
					System.out.println(b_id);
					bList.add(bDao.getBook(b_id));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			loadBooks();
			updateTable();
		});
		getContentPane().add(inquireButton);
		
		JButton handButton = new JButton("\u8FD8\u4E66");
		handButton.setFont(new Font("YouYuan", Font.PLAIN, 30));
		handButton.setBounds(254, 466, 183, 54);
		handButton.addActionListener((e)->{
			int selected = table.getSelectedRow();
			if(selected < 0)
				return;
			String b_id = (String) model.getValueAt(selected, 0);
			try {
				brrowDao.handInBook(u_id, b_id);
			} catch (SQLException e1) {
				idField.setText("你并没有借阅此书，无法归还");
				e1.printStackTrace();
			}
			
		});
		getContentPane().add(handButton);
		
		/*
		 * 根据用户输入的参数进行搜索，其中可以搜索多个参数
		 */
		JButton searchButton = new JButton("\u641C\u7D22\u56FE\u4E66");
		searchButton.setFont(new Font("YouYuan", Font.PLAIN, 30));
		searchButton.setBounds(254, 590, 183, 54);
		searchButton.addActionListener((e)->{
			String name = nameField.getText();
			String auther = autherField.getText();
			
			try {
				bList = bDao.searchBook(new String[]{name,auther});
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			loadBooks();
			updateTable();
		});
		getContentPane().add(searchButton);
		
		/*
		 * 文本框
		 */
		idField = new JTextField();
		idField.setFont(new Font("YouYuan", Font.PLAIN, 30));
		idField.setBounds(623, 466, 332, 54);
		getContentPane().add(idField);
		idField.setColumns(10);
		
		autherField = new JTextField();
		autherField.setFont(new Font("YouYuan", Font.PLAIN, 30));
		autherField.setColumns(10);
		autherField.setBounds(623, 590, 332, 54);
		getContentPane().add(autherField);
		
		nameField = new JTextField();
		nameField.setFont(new Font("YouYuan", Font.PLAIN, 30));
		nameField.setColumns(10);
		nameField.setBounds(623, 708, 332, 54);
		getContentPane().add(nameField);
		
		typeField = new JTextField();
		typeField.setFont(new Font("YouYuan", Font.PLAIN, 30));
		typeField.setColumns(10);
		typeField.setBounds(625, 826, 332, 54);
		getContentPane().add(typeField);
		
		/*
		 * 便签
		 */
		JLabel lblNewLabel = new JLabel("\u63D0\u793A\u7A97:");
		lblNewLabel.setFont(new Font("YouYuan", Font.PLAIN, 30));
		lblNewLabel.setBounds(478, 467, 113, 52);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("\u4F5C\u8005:");
		label.setFont(new Font("YouYuan", Font.PLAIN, 30));
		label.setBounds(513, 590, 100, 52);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u9898\u76EE:");
		label_1.setFont(new Font("YouYuan", Font.PLAIN, 30));
		label_1.setBounds(513, 708, 100, 52);
		getContentPane().add(label_1);
		
		
		
		JLabel label_2 = new JLabel("\u5206\u7C7B:");
		label_2.setFont(new Font("YouYuan", Font.PLAIN, 30));
		label_2.setBounds(511, 829, 100, 52);
		getContentPane().add(label_2);
		
		/*
		 * 表格与模型
		 */
		model = new DefaultTableModel(data,column_names);
		table = new JTable(model);
		table.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		table.setRowHeight(35);
		scrollPane.setViewportView(table);
		
		initDatabase();
	}
	
	private void initDatabase() {
		brrowDao = new BrrowDao(conn);
		bDao = new BookDao(conn);
		typeDao = new BookTypeDao(conn);
		bList = new LinkedList<Book>();
		
		try {
			for(String b_id:brrowDao.getBrrowedBooks(u_id)) {
				bList.add(bDao.getBook(b_id));
			loadBooks();
			updateTable();
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/*
	 * 加载当前的列表书籍
	 */
	public void loadBooks() {
		data = new Object[bList.size()][6];
		int i =0;
		for(Book b:bList) {
			data[i][0] = b.getID();
			data[i][1] = b.getName();
			data[i][2] = b.getAuther();
			data[i][3] = b.getDecs();
			data[i][4] = b.getPrice();
			data[i][5] = b.getType();
			i++;
		}
	}
	
	/*
	 * 更新表格
	 */
	private void updateTable() {
		model.setDataVector(data,column_names);
		model.fireTableDataChanged();
		table.repaint();
	}
	
	private void setFrameSize() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		int DEFAULT_HEIGHT = kit.getScreenSize().height;
		int DEFAULT_WIDTH = kit.getScreenSize().width;
		setSize((int)(DEFAULT_WIDTH /2.5) ,(int)(DEFAULT_HEIGHT/1.5));
		this.setLocationByPlatform(true);
		getContentPane().setLayout(null);
	}
	
	public static void main(String[] args) throws SQLException {
		CommonUserFrame cuf = new CommonUserFrame(new DbUtil().getConn(),1);
		cuf.setVisible(true);
	}
}
