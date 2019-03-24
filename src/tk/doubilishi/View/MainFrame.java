package tk.doubilishi.View;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tk.doubilishi.Model.User;
import tk.doubilishi.Util.DbUtil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

/*
 * 登陆之后的用户id应该是绑定的，因此可以先把所有的所借书籍载入
 */
public class MainFrame extends JFrame{
	private Connection conn;
	private User user;
	
	public MainFrame(Connection conn,User user) {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		Toolkit kit = Toolkit.getDefaultToolkit();
		int DEFAULT_HEIGHT = kit.getScreenSize().height;
		int DEFAULT_WIDTH = kit.getScreenSize().width;
		setSize(DEFAULT_WIDTH /2 ,DEFAULT_HEIGHT/2);
		this.setLocationByPlatform(true);
		panel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1358, 61);
		panel.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u57FA\u672C\u6570\u636E\u64CD\u4F5C");
		mnNewMenu.setFont(new Font("STZhongsong", Font.PLAIN, 44));
		menuBar.add(mnNewMenu);
		
		/***************进入图书类别管理界面*************/
		JMenuItem mntmNewMenuItem = new JMenuItem("\u56FE\u4E66\u7C7B\u522B\u7BA1\u7406");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTypeFrame btF = new BookTypeFrame(conn);
				btF.setVisible(true);
			}
		});
		mntmNewMenuItem.setFont(new Font("STZhongsong", Font.PLAIN, 38));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_2 = new JMenu("\u4E66\u7C4D\u7BA1\u7406");
		mnNewMenu_2.setFont(new Font("STZhongsong", Font.PLAIN, 38));
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u4E66\u7C4D\u7EF4\u62A4");
		mntmNewMenuItem_3.setFont(new Font("STZhongsong", Font.PLAIN, 38));
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\u4E66\u7C4D\u67E5\u8BE2");
		mntmNewMenuItem_4.setFont(new Font("STZhongsong", Font.PLAIN, 38));
		mnNewMenu_2.add(mntmNewMenuItem_4);
		
		/************安全退出*******************/
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5B89\u5168\u9000\u51FA");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conn.close();
					System.exit(0);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmNewMenuItem_1.setFont(new Font("STZhongsong", Font.PLAIN, 38));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		initDatabase(conn,user);
	}
	
	/*
	 * 初始化数据库
	 */
	private void initDatabase(Connection conn,User user) {
		this.conn = conn;
		this.user = user;
	}
	
	public static void main(String...args) throws SQLException {
		MainFrame mf = new MainFrame(new DbUtil().getConn(),new User(1,"红木","123456"));
		mf.setVisible(true);
	}
}
