package common;

public class CommonUtil {

	private CommonUtil() {
		// インスタンス化できないように設定
	}

	public static String formatZero(String val) {

		String checkDay = "";

		if (val.length() == 2) {
			checkDay = (val);
		} else {
			checkDay = ("0" + val);
		}

		return checkDay;
	}
}
