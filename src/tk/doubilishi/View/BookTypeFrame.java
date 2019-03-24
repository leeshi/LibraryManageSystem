package tk.doubilishi.View;

import tk.doubilishi.Dao.BookTypeDao;
import tk.doubilishi.Model.BookType;
import tk.doubilishi.Util.DbUtil;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class BookTypeFrame extends JFrame{
	private JTextField searchField;
	private JButton btnNewButton_1;
	private JTextField idField;
	private JTextField nameField;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JTable table;
	
	/*数据变量*/
	private Connection conn;
	private BookTypeDao btDao;
	private Object[][] data;
	private LinkedList<BookType> typeList;
	private String[] column_names = {"ID","name","decription"};
	
	public BookTypeFrame(Connection conn) {
		setSize();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(15, 499, 1322, 513);
		getContentPane().add(panel);
		panel.setLayout(null);
		/*文本框*/
		searchField = new JTextField();
		searchField.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		searchField.setBounds(21, 429, 1074, 49);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(510, 82, 791, 351);
		panel.add(textArea);
		textArea.setFont(new Font("YouYuan", Font.PLAIN, 28));
		
		idField = new JTextField();
		idField.setBounds(110, 88, 379, 49);
		panel.add(idField);
		idField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(110, 169, 379, 49);
		panel.add(nameField);
		nameField.setFont(new Font("YouYuan", Font.PLAIN, 28));
		nameField.setColumns(10);
		
		/*按钮*/
		//搜索的按钮
		JButton btnNewButton = new JButton("\u641C\u7D22");
		btnNewButton.addActionListener((e)-> {
				try {
					typeList = btDao.searchType(searchField.getText());
					searchField.setText("");  //清除文本框
					updateTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		});
		btnNewButton.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		btnNewButton.setBounds(1116, 429, 221, 49);
		getContentPane().add(btnNewButton);
		
		//添加新类别的按钮
		JButton btnNewButton_2 = new JButton("\u6DFB\u52A0\u7C7B\u522B");
		btnNewButton_2.addActionListener((e)-> {
				String name = nameField.getText();
				int id = Integer.parseInt(idField.getText());
				String description = textArea.getText();
				
				nameField.setText("");
				idField.setText("");
				textArea.setText("");
				
				try {
					btDao.addType(new BookType(id,name,description));
				}catch(SQLException x) {x.printStackTrace();}
			}
		);
		btnNewButton_2.setBounds(230, 239, 259, 57);
		panel.add(btnNewButton_2);
		btnNewButton_2.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		
		//删除类别的按钮
		JButton btnid = new JButton("\u6839\u636EID\u5220\u9664");
		btnid.setBounds(230, 336, 259, 57);
		btnid.addActionListener((e)->{
			try {
				btDao.deleteType(Integer.parseInt(idField.getText()));
				idField.setText("");
			} catch (NumberFormatException | SQLException e1) {
				idField.setText("请输入纯数字");
				e1.printStackTrace();
			}
		});
		panel.add(btnid);
		btnid.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		
		//获取所有类别的按钮
		btnNewButton_1 = new JButton("\u91CD\u65B0\u663E\u793A\u6240\u6709\u7C7B\u522B");
		btnNewButton_1.addActionListener((e) -> {
				try {
					typeList = btDao.getAllType();
					updateTable();
				}catch(Exception e1) {e1.printStackTrace();}
		});
		btnNewButton_1.setBounds(1018, 449, 283, 43);
		panel.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		
		/*标签*/
		JLabel label_1 = new JLabel("\u63CF\u8FF0");
		label_1.setBounds(510, 33, 194, 43);
		panel.add(label_1);
		label_1.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		
		JLabel label = new JLabel("\u540D\u79F0");
		label.setBounds(21, 158, 69, 43);
		panel.add(label);
		label.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(36, 89, 53, 43);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		
		
		model = new DefaultTableModel(data,column_names);
		table = new JTable(model);
		table.setFont(new Font("STZhongsong", Font.PLAIN, 30));
		table.setRowHeight(35);
		
		try {
			init(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(21, 21, 1316, 380);
		getContentPane().add(scrollPane);
		
	}
	
	/*
	 * 设置窗口大小
	 */
	private void setSize() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		int DEFAULT_HEIGHT = kit.getScreenSize().height;
		int DEFAULT_WIDTH = kit.getScreenSize().width;
		setSize(DEFAULT_WIDTH /2 ,(int)(DEFAULT_HEIGHT/1.5));
		this.setLocationByPlatform(true);
		getContentPane().setLayout(null);
	}
	
	/*
	 * 重新载入需要的数据，然后更新model中的向量，最后重绘table
	 */
	private void updateTable() throws SQLException {
		data = new Object[typeList.size()][3];
		
		Iterator<BookType> iterator = typeList.iterator();
		int i =0;
		while(iterator.hasNext()){
			BookType type = iterator.next();
			data[i][0]=type.getTypeId();
			data[i][1]=type.getTypeName();
			data[i][2]=type.getTypeCat();
			i++;
		}
		
		model.setDataVector(data, column_names);
		model.fireTableDataChanged();
		table.repaint();
	}
	
	/*
	 * 初始化数据库并显示所有的类别
	 */
	private void init(Connection conn) throws SQLException {
		//初始化数据库相关
		this.conn = conn;
		this.btDao = new BookTypeDao(conn);
		//先显示所有的类别
		typeList = btDao.getAllType();
		updateTable();
	}
	
	public static void main(String...args) throws SQLException {
		BookTypeFrame btf = new BookTypeFrame(new DbUtil().getConn());
		btf.setVisible(true);
	}
}
