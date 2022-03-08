package common;

public class CommonUtil {

	private CommonUtil() {
		// インスタンス化できないように設定
	}


	public static String ymdFormatEight(String year, String month, String day) {

		if (day.length() != 2) {
			day = "0" + day;
		}

		String ymd = year + month + day;

		return ymd;
	}


	public static String changeNull(String value) {

		String check = "";

		if (value != null && value.equals("") ) {
			check = null;
		} else {
			check = value;
		}

	return check;
	}
}
