package tk.doubilishi.Model;

public class Book {
	private String b_id;
	private String b_name;
	
	private String b_auther;
	private String b_desc;
	private int BookTypeId;    //0代表未分类
	private float b_price;
	
	public Book(String BookName,String auther,String BookDecs,float price,int BookTypeId,String bID) {
		this.b_desc = BookDecs;
		this.b_price = price;
		this.b_auther = auther;
		this.b_name = BookName;
		this.BookTypeId = BookTypeId;
		this.b_id = bID;
	}
	
	public Book(String BookName,String auther,String BookDecs,float price,String bID) {
		this.b_desc = BookDecs;
		this.b_price = price;
		this.b_auther = auther;
		this.b_name = BookName;
		this.BookTypeId = 0;
		this.b_id = bID;
	}
	
	/*
	 * seter
	 */
	public void setId(String bID) {
		this.b_id = bID;
	}
	public void setName(String b_name) {
		this.b_name = b_name;
	}
	public void setAuther(String b_auther) {
		this.b_auther = b_auther;
	}
	public void setPrice(float price) {
		this.b_price = price;
	}
	public void setType(int typeId) {
		this.BookTypeId = typeId;
	}
	public void setDesc(String desc) {
		this.b_desc = desc;
	}
	
	/*
	 * geter
	 */
	public String getName() {
		return b_name;
	}
	public String getID() {
		return b_id;
	}
	public String getAuther() {
		return b_auther;
	}
	public String getDecs() {
		return b_desc;
	}
	public int getType() {
		return BookTypeId;
	}
	public float getPrice() {
		return b_price;
	}
	
	@Override
	public String toString() {
		return String.format("ID:%s-NAME:%s-AUTHER:%s-DESCRIPTION:%s-PRICE:%f-type:%d", this.b_id,this.b_name
				,this.b_auther,this.b_desc,this.b_price,this.BookTypeId);
	}
}
