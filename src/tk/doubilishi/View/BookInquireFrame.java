package tk.doubilishi.View;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tk.doubilishi.Dao.BookDao;
import tk.doubilishi.Model.Book;
import tk.doubilishi.Util.DbUtil;
import tk.doubilishi.Util.StringUtil;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class BookInquireFrame extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	
	private Connection conn;
	private LinkedList<Book> bList;
	private BookDao bDao;
	private String[] column_names = {"id","name","auther","description","price","typeid"};
	private Object[][] data;
	private JTextField idField;
	private JTextField nameField;
	private JLabel label_1;
	private JTextField autherField;
	private JLabel label_2;
	private JTextField priceField;
	private JTextArea descArea;
	private JLabel label_3;
	private JLabel lblid;
	private JTextField typeField;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton nameSearchButton;
	private JButton autherSearchButton;
	private JButton idGetButton;
	private JButton getAllButton;
	
	public BookInquireFrame(Connection conn) {
		setSize();
		
		
		
		
		/***标签****/
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		lblNewLabel.setBounds(67, 542, 53, 49);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("\u540D\u79F0");
		label.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		label.setBounds(521, 542, 69, 49);
		getContentPane().add(label);
		
		label_1 = new JLabel("\u4F5C\u8005");
		label_1.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		label_1.setBounds(51, 637, 69, 49);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("\u4EF7\u683C");
		label_2.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		label_2.setBounds(521, 637, 69, 49);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("\u63CF\u8FF0");
		label_3.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		label_3.setBounds(521, 736, 69, 49);
		getContentPane().add(label_3);
		
		lblid = new JLabel("\u79CD\u7C7BID");
		lblid.setFont(new Font("STZhongsong", Font.PLAIN, 28));
		lblid.setBounds(22, 736, 98, 49);
		getContentPane().add(lblid);
		
		/*****文本框****/
		idField = new JTextField();
		idField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		idField.setColumns(10);
		idField.setBounds(134, 543, 299, 49);
		getContentPane().add(idField);
		
		autherField = new JTextField();
		autherField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		autherField.setColumns(10);
		autherField.setBounds(134, 637, 299, 49);
		getContentPane().add(autherField);
		
		priceField = new JTextField();
		priceField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		priceField.setColumns(10);
		priceField.setBounds(597, 637, 466, 49);
		getContentPane().add(priceField);
		
		descArea = new JTextArea();
		descArea.setLineWrap(true);
		descArea.setFont(new Font("YouYuan", Font.PLAIN, 28));
		descArea.setBounds(597, 736, 466, 402);
		getContentPane().add(descArea);
		
		typeField = new JTextField();
		typeField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		typeField.setColumns(10);
		typeField.setBounds(134, 736, 299, 49);
		getContentPane().add(typeField);
		
		nameField = new JTextField();
		nameField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		nameField.setColumns(10);
		nameField.setBounds(597, 543, 466, 49);
		getContentPane().add(nameField);
		
		
		/***设置按钮***/
		addButton = new JButton("\u6DFB\u52A0\u4E66\u7C4D");
		addButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		addButton.setBounds(275, 816, 233, 61);
		addButton.addActionListener((e)->{
			String id = idField.getText();
			String name = nameField.getText();
			String desc = descArea.getText();
			String auther = autherField.getText();
			String type = typeField.getText();
			String price = priceField.getText();
			
			if(!StringUtil.isNumber(price)||!StringUtil.isInteger(type)) {
				descArea.setText("请检查输入的数值是否完备");
				return;
			}
			else
			{
				try {
					Book book = new Book(name,auther,desc,Float.parseFloat(price),Integer.parseInt(type),id);
					System.out.println(book);
					bDao.addBook(book);
					clearField();
				} catch (SQLException e1) {
					descArea.setText("键值可能发生了重复，请检查");
					e1.printStackTrace();
				}
			}
			
		});
		getContentPane().add(addButton);
		
		updateButton = new JButton("\u66F4\u65B0\u4E66\u7C4D");
		updateButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		updateButton.setBounds(275, 898, 233, 61);
		updateButton.addActionListener((e)->{
			String id = idField.getText();
			String name = nameField.getText();
			String desc = descArea.getText();
			String auther = autherField.getText();
			String type = typeField.getText();
			String price = priceField.getText();
			//先取得相关的图书再更新
			try {
				if(!this.bList.isEmpty()) {
					Book book = this.bList.get(0);
					System.out.println(book);
					if(!name.isEmpty())
						book.setName(name);
					if(!desc.isEmpty())
						book.setDesc(desc);
					if(!auther.isEmpty())
						book.setAuther(auther);
					//判断价格是否符合格式
					if(!price.isEmpty())
						if(StringUtil.isNumber(price))
							book.setPrice(Float.parseFloat(price));
						else {
							descArea.setText("请输入正确的价格");
							return;
						}
					//判断类别是否符合格式
					if(!type.isEmpty()) {
						if(StringUtil.isInteger(type))
							book.setType(Integer.parseInt(type));
						else {
							descArea.setText("请输入正确的类别");
							return;
						}
					}
					bDao.updateBook(book);
					loadBooks();
					updateTable();
				}
			}catch(SQLException e1) {
				e1.printStackTrace();
				descArea.setText("数据库可能出错，请重启");
			}
		});
		getContentPane().add(updateButton);
		
		deleteButton = new JButton("\u5220\u9664\u4E66\u7C4D(id)");
		deleteButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		deleteButton.setBounds(275, 980, 233, 61);
		deleteButton.addActionListener((e)->{
			try {
				int state = bDao.deleteBook(idField.getText());
				if(state==0)
					descArea.setText("找不到对应的id");
				else
					idField.setText("");
			} catch (SQLException e1) {
				descArea.setText("数据库可能出错，请重启");
				e1.printStackTrace();
			}
			
		});
		getContentPane().add(deleteButton);
		
		nameSearchButton = new JButton("\u641C\u7D22\u540D\u79F0");
		nameSearchButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		nameSearchButton.setBounds(21, 816, 233, 61);
		nameSearchButton.addActionListener((e)->{
			try {
				bList = bDao.searchWithName(nameField.getText());
				loadBooks();
				updateTable();
				nameField.setText("");
				descArea.setText("搜索到"+bList.size()+"本相关图书");
			} catch (SQLException e1) {
				descArea.setText("数据库可能出错，请重启");
				e1.printStackTrace();
			}
			
		});
		getContentPane().add(nameSearchButton);
		
		autherSearchButton = new JButton("\u641C\u7D22\u4F5C\u8005");
		autherSearchButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		autherSearchButton.setBounds(22, 898, 233, 61);
		autherSearchButton.addActionListener((e)->{
			String auther = autherField.getText();
			autherField.setText("");
			try{
				this.bList = bDao.searchWithAuther(auther);
				loadBooks();
				updateTable();
				
			}catch(SQLException e1){
				descArea.setText("数据库可能出错，请重启");
				e1.printStackTrace();
			}
			autherField.setText("");
		});
		getContentPane().add(autherSearchButton);
		
		idGetButton = new JButton("\u641C\u7D22ID");
		idGetButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		idGetButton.setBounds(22, 980, 233, 61);
		idGetButton.addActionListener((e)->{
			String id = idField.getText();
			try {
				this.bList = new LinkedList<Book>();
				Book book = bDao.getBook(id);
				if(book!=null)
					bList.add(book);
				loadBooks();
				updateTable();
				
			}catch(SQLException e1) {descArea.setText("数据库可能出错，请重启");e1.printStackTrace();}
		});
		getContentPane().add(idGetButton);
		
		getAllButton = new JButton("\u663E\u793A\u5168\u90E8");
		getAllButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		getAllButton.setBounds(22, 1077, 486, 61);
		getAllButton.addActionListener((e)->{
			try {
				bList = bDao.getAllBooks();
				loadBooks();
				updateTable();
			}catch(SQLException e1) {
				descArea.setText("数据库可能出错，请重启");
				e1.printStackTrace();
			}
		});
		getContentPane().add(getAllButton);
		
		
		/***操作表格***/
		model = new DefaultTableModel(data,column_names);
		table = new JTable(model);
		table.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		table.setRowHeight(35);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(21, 21, 1042, 461);
		getContentPane().add(scrollPane);
		try {
			init(conn);
			updateTable();
		}catch(SQLException e) {descArea.setText("数据库初始化出现错误！");e.printStackTrace();}
		
	}
	
	/******更新表格********/
	private void updateTable() {
		model.setDataVector(data, column_names);
		model.fireTableDataChanged();
		table.repaint();
	}
	
	/****设置合适的大小*****/
	private void setSize() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		int DEFAULT_HEIGHT = kit.getScreenSize().height;
		int DEFAULT_WIDTH = kit.getScreenSize().width;
		setSize((int)(DEFAULT_WIDTH /2.5) ,(int)(DEFAULT_HEIGHT/1.5));
		this.setLocationByPlatform(true);
		getContentPane().setLayout(null);
	}
	
	/***清除文本框的内容***/
	private void clearField() {
		idField.setText("");
		nameField.setText("");
		descArea.setText("");
		autherField.setText("");
		priceField.setText("");
		typeField.setText("");
	}
	
	/*****获取所有图书*****/
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
	/******初始化数据库与显示初始内容
	 * @throws SQLException *****/
	private void init(Connection conn) throws SQLException {
		this.conn = conn;
		this.bDao = new BookDao(conn);
		
		bList = bDao.getAllBooks();
		loadBooks();
	}
	
	public static void main(String...args) throws SQLException {
		BookInquireFrame bif = new BookInquireFrame(new DbUtil().getConn());
		bif.setVisible(true);
	}
}
