package tk.doubilishi.Util;

public class StringUtil {
	public static boolean isNumber(String str) {
		if(str.equals(""))
			return false;
		String reg = "^[0-9]+(.[0-9]+)?$";
		return str.matches(reg);
	}
	public static boolean isInteger(String str) {
		if(str.equals(""))
			return false;
		String reg = "^[0-9]";
		return str.matches(reg);
	}
}
