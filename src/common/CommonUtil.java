package common;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	private CommonUtil() {
		// インスタンス化できないように設定
	}


	/**
	 * 引数が ""かつ != null のときnullを返す
	 * @param String value, All of String
	 * @return String, Return null in param is Empty
	 */
	public static String changeNullByEmpty(String value) {

		if (value != null && value.equals("")) {
			value = null;
		}

		return value;
	}

	/**
	 * 引数がnull のとき""を返す
	 * @param String value, All of String
	 * @return String, Return Empty in param is null
	 */
	public static String changeEmptyByNull(String value) {

		if (value == null) {
			value = "";
		}

		return value;
	}


	/**
	 * input(引数)が記号やURLのときエスケープする
	 * @param String value, All of String
	 * @return String, Return escaped in param has symbol or URL
	 */
	public static String replaceEscapeChar(String input) {

		String valiXSSUrl = "http://|https://";
		String valiXSSScript = "<>&\"'";

		String pattern1 = valiXSSUrl + ".*";
		String pattern2 = ".*" + valiXSSScript + ".*";

		//inputが""またはnullのとき
		if (StringUtils.isEmpty(input)) {

			return input;
		}

		try {
			//inputにURLが含まれているとき
			if (input.matches(pattern1)) {

				input = input.replaceAll("http://", "\\http://");
				input = input.replaceAll("https://", "\\https://");
			}

			//inputに記号が含まれているとき
			if (input.matches(pattern2)) {

				input = input.replaceAll("&", "&amp;");
				input = input.replaceAll("<", "&lt;");
				input = input.replaceAll(">", "&gt;");
				input = input.replaceAll("\"", "&quot;");
				input = input.replaceAll("'", "&apos;");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}
}
