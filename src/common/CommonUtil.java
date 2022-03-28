package common;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	private CommonUtil() {
		// インスタンス化できないように設定
	}

	/**
	 * year, month(int) をym(String6文字)で返すメソッド
	 */
	public static String toStringYmFormatSixByInt(int year, int month) {

		//monthが1桁のとき2桁の文字列に変換する
		String ym = year + String.format("%02d", month);

		return ym;
	}


	/**
	 * year, month, day をymd(String8文字)で返すメソッド
	 */
	public static String ymdFormatEightByString(String year, String month, String day) {

		if(day.length() != 2) {
			day = "0" + day;
		}

		if(month.length() != 2) {
			month = "0" + month;
		}

		String ymd = year + month + day;

		return ymd;
	}

	/**
	 * 引数が ""かつ != null のときnullを返すメソッド
	 */
	public static String changeNullByEmpty(String value) {

		if(value != null && value.equals("")) {
			value = null;
		}

		return value;
	}

	/**
	 * 引数が""でないかつnull のとき""を返すメソッド
	 */
	public static String changeEmptyByNull(String value) {

		if(value != "" && value == null) {
			value = "";
		}

		return value;
	}


	/**
	 * Listの要素が""またはnullときtrueを返すメソッド
	 */
	public static boolean isCheckSizeZeroByList(List value) {

		if(CollectionUtils.isEmpty(value)) {
			return true;
		}

		return false;
	}



	/**
	 * input(引数)が記号やURLのときエスケープするメソッド
	 */
	public static String replaceEscapeChar(String input) {

		String valiXSSUrl = "http://|https://";
		String valiXSSScript = "<>&\"'";

		String pattern1 = valiXSSUrl + ".*";
		String pattern2 = ".*" + valiXSSScript + ".*";

		//inputが""またはnullのとき
		if(StringUtils.isEmpty(input)) {

			return input;
		}

		try {
			//inputにURLが含まれているとき
			if (input.matches(pattern1)) {
				input = input.replace("http://", "\\http://");
				input = input.replace("https://", "\\https://");
			}

			//inputに記号が含まれているとき
			if (input.matches(pattern2)) {
				input = input.replace("&", "&amp;");
				input = input.replace("<", "&lt;");
				input = input.replace(">", "&gt;");
				input = input.replace("\"", "&quot;");
				input = input.replace("'", "&apos;");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return input;
	}
}
