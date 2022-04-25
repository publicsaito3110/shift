package common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author saito
 *
 */
public class CommonUtil {

	private CommonUtil() {
		// インスタンス化できないように設定
	}


	/**
	 * null変換処理
	 *
	 * <p>nullでないかつ空文字のときnullに変換する</p>
	 *
	 * @param value 全てのString型の変数
	 * @return String 空文字のときnullに変換し、空文字以外のときは何もしない
	 */
	public static String changeNullByEmpty(String value) {

		if (value != null && value.equals("")) {
			value = null;
		}

		return value;
	}

	/**
	 * 空文字変換処理
	 *
	 * <p>null のとき空文字に変換する</p>
	 *
	 * @param value 全てのString型の変数
	 * @return String nullのとき空文字に変換し、null以外のときは何もしない
	 */
	public static String changeEmptyByNull(String value) {

		if (value == null) {
			value = "";
		}

		return value;
	}


	/**
	 * エスケープ処理
	 *
	 * <p>記号やURLのときエスケープする</p>
	 *
	 * @param input 全てのString型の変数
	 * @return String エスケープ対象の記号やURLが存在するときエスケープし、それ以外のときは何もしない
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
