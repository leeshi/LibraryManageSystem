package tk.doubilishi.View;

import javax.swing.*;

import tk.doubilishi.Dao.*;
import tk.doubilishi.Model.User;
import tk.doubilishi.Util.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LoginFrame extends JFrame{
	private JTextField textField;
	private JLabel lblNewLabel;
	
	private UserDao uDao;
	private Connection conn;
	private User user;
	private JPasswordField passwordField;
	
	public LoginFrame() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		Toolkit kit = Toolkit.getDefaultToolkit();
		int DEFAULT_HEIGHT = kit.getScreenSize().height;
		int DEFAULT_WIDTH = kit.getScreenSize().width;
		setSize(DEFAULT_WIDTH /3 ,DEFAULT_HEIGHT/3);
		this.setLocationByPlatform(true);
		panel.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("SimSun", Font.PLAIN, 55));
		lblId.setBounds(91, 94, 108, 69);
		panel.add(lblId);
		
		textField = new JTextField();
		textField.setFont(new Font("SimSun", Font.PLAIN, 55));
		textField.setBounds(330, 94, 389, 64);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("SimSun", Font.PLAIN, 55));
		lblPassword.setBounds(35, 169, 242, 74);
		panel.add(lblPassword);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setFont(new Font("SimSun", Font.PLAIN, 55));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String passwd = new String(passwordField.getPassword());
				int u_id = Integer.parseInt(textField.getText());
				
				try {
					if(loginCheck(u_id,passwd)) {
						lblNewLabel.setText("登陆成功");
						dispose();    //取消显示
						MainFrame mf = new MainFrame(conn,user);
						mf.setVisible(true);
					}
					else
						lblNewLabel.setText("用户名或密码错误(默认密码：123321)");
				} catch (SQLException e1) {
					e1.printStackTrace();
					try {
						lblNewLabel.setText("数据库访问异常，请重启");
						Thread.sleep(1000);
						System.exit(-1);
					} catch (InterruptedException e2) {e2.printStackTrace();}
				}
			}
		});
		btnLogin.setBounds(73, 323, 242, 107);
		panel.add(btnLogin);
		
		JButton btnExit = new JButton("exit");
		btnExit.setFont(new Font("SimSun", Font.PLAIN, 55));
		btnExit.setBounds(495, 321, 224, 111);
		btnExit.addActionListener((e)->
			{System.exit(0);});
		panel.add(btnExit);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("SimSun", Font.PLAIN, 55));
		passwordField.setBounds(330, 179, 389, 64);
		panel.add(passwordField);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("SimSun", Font.ITALIC, 44));
		lblNewLabel.setBounds(35, 244, 846, 57);
		panel.add(lblNewLabel);
		
		initDatabase();
	}
	
	/*
	 * 初始化数据库连接
	 */
	private void initDatabase() {
		try {
			conn = new DbUtil().getConn();
			uDao = new UserDao(conn);
		}
		catch(SQLException e) {
			System.out.println("数据库连接失败");
			System.exit(-1);
		}
	}
		
	/*
	 * 检查密码是否正确
	 */
	private boolean loginCheck(int u_id,String passwd) throws SQLException {
		System.out.println("u_id:"+u_id+"  password:" +passwd);
		if(passwd.length()!=6) {
			return false;
		}
		else {
			user = uDao.getUser(u_id);
			System.out.println(user);
			if(user!=null)
				return user.getPasswd().equals(passwd);
			else
				return false;
		}
	}
	
	public static void main(String...args) {
		JFrame loginFrm = new LoginFrame();
		loginFrm.setVisible(true);
	}
}
