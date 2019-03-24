package tk.doubilishi.Model;

public class BookType {
	private int typeId;
	private String typeCat;
	private String typeName;
	
	public BookType(int typeId,String typeName,String typeCat) {
		this.typeId = typeId;
		this.typeCat = typeCat;
		this.typeName = typeName;
	}
	
	/*
	 * setter
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setTypeCat(String typeCat) {
		this.typeCat = typeCat;
	}

	/*
	 * getter
	 */
	public int getTypeId() {
		return typeId;
	}
	public String getTypeCat() {
		return this.typeCat;
	}
	public String getTypeName() {
		return this.typeName;
	}
	
	@Override
	public String toString() {
		return this.typeId + "-"+this.typeName+"-"+this.typeCat;
	}
}
