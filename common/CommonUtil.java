package common;

public class CommonUtil {

	private CommonUtil() {
		// インスタンス化できないように設定
	}


	public static String dayFormatTwo(String day) {   //day を(String2文字)で返す

		if(day.length() != 2) {
			day = "0" + day;
		}

		return day;
	}


	public static String ymdFormatEight(String year, String month, String day) {   //year, month, day をymd(String8文字)で返す

		if(day.length() != 2) {
			day = "0" + day;
		}

		if(month.length() != 2) {
			month = "0" + month;
		}

		String ymd = year + month + day;

		return ymd;
	}


	public static String changeNull(String value) {           //引数が ""かつ != null のときnullを返すメソッド

		if(value != null && value.equals("")) {
			value = null;
		}

		return value;
	}


	public static boolean typeSQLCheck(String sqlType) {    //typeSQLが INSERT,UPDATE,DELETE のときtrueを返すメソッド

		boolean result = false;

		if(sqlType == "INSERT" || sqlType == "UPDATE" || sqlType == "DELETE") {
			result = true;
		}

		return result;
	}

	//---------------------------------------------------------
	//入力値のバリデーションチェック(patternと一致していればtrueを返す)
	//---------------------------------------------------------

	public static boolean validIdStatus(String id) {                //idをチェック

		boolean result = false;

		//半角英数字
		String   pattern = "^[" + Const.valiAlpha + Const.valiNum + "]+$";

		try {
			if(id.length() == 4) {                   //idが4文字であればバリデーションチェック
				result = id.matches(pattern);
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean validNameStatus(String name) {              //nameをチェック

		boolean result = false;

		//半角英字,全角かなカナ漢字,半角スペース
		String pattern = "^[" + Const.valiAlpha + Const.valiGana + Const.valiKana + Const.valiKanji + Const.valiAllowKigou + Const.valiNum + "]+$";

		try {
			if(name.length() <= 20) {                    //nameが20文字未満であればバリデーションチェック
				result = name.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean validNameKanaStatus(String nameKana) {       //nameKanaをチェック

		boolean result = false;

		//全角カタカナ
		String pattern = "^[" + Const.valiKana + "]+$";

		try {
			if(nameKana.isEmpty()) {                      //nameKanaが""であればtrue(任意のため)
				result = true;
				return result;
			}

			if(nameKana.length() <= 40) {          //nameKanaが40文字未満であればバリデーションチェック
				result = nameKana.matches(pattern);
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean validGenderStatus(String gender) {            //genderをチェック

		boolean result = false;

		//1または2
		String pattern = Const.valiGender;

		try {
			if(gender.length() == 1) {                     //genderが1文字であればバリデーションチェック
				result = gender.matches(pattern);
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
