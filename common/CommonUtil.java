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


	//----------バリデーションチェック--------------------------------------------------------
	public static boolean validIdStatus(String id) {       //idをチェック

		boolean result = false;
		String match = "^[0-9a-zA-Z]$"; //半角英数字ならtrue

		if (id.matches(match) && id.length() == 4) {   //match + 4文字であればtrueを返す
			result = true;
		}

		return result;
	}

	public static boolean validNameStatus(String name) {       //nameをチェック

		boolean result = false;
		String match = "^[a-zA-Zぁ-んァ-ヶ亜-熙]*$"; //半角英字,全角かなカナ漢字であればtrue

		if (name.matches(match)) {   //match + 20文字未満であればtrueを返す
			result = true;
		}

		return result;
	}

	public static boolean validNameKanaStatus(String nameKana) {       //nameKanaをチェック

		boolean result = false;
		String match = "^[ァ-ヶー]$"; //全角カタカナであればtrue

		if (nameKana.matches(match) && nameKana.length() <= 40) {   //match + 40文字未満であればtrueを返す
			result = true;
		}else if(nameKana.equals("")) {                            //nameKanaが""であればtrue(任意のため)を返す
			result = true;
		}

		return result;
	}

	public static boolean validGenderStatus(String gender) {       //genderをチェック

		boolean result = false;
		String match = "1|2"; //1または2 + 1文字ならtrue

		if (gender.matches(match) && gender.length() == 1) {        //match + 1文字であればtrueを返す
			result = true;
		}

		return result;
	}
}
