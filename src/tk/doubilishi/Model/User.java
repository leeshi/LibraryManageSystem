package tk.doubilishi.Model;

public class User {
	private int u_id;
	private String u_name;
	private String passwd;
	
	public User(int u_id,String u_name,String passwd){
		this.u_name = u_name;
		this.passwd = passwd;
		this.u_id = u_id;
	}
	public User(String u_name) {
		this.u_name = u_name;
		passwd = "123456";    //default password
	}
	
	/*
	 * seter
	 */
	public void setId(int id) {
		this.u_id = id;
	}
	public void resetPwd(String passwd) {
		this.passwd = passwd;
	}
	public void setUserName(String newName) {
		this.u_name = newName;
	}
	
	/*
	 * getter
	 */
	public int getId() {
		return this.u_id;
	}
	public String getName() {
		return this.u_name;
	}
	public String getPasswd() {
		return this.passwd;
	}
	
	@Override
	public String toString() {
		return this.u_id +" : " +this.passwd + " name: "+this.u_name;
	}
}
