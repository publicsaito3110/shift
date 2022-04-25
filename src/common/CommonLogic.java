package common;

/**
 * @author saito
 *
 */
public class CommonLogic {


	/**
	 * String日付変換処理
	 *
	 * <p>String型の8文字の日付(YYYYMMDD)に変換する<p>
	 *
	 * @param year 日付(年)
	 * @param month 日付(月)
	 * @param day 日付(日)
	 * @return String 日付(YYYYMMDD)
	 */
	public String ymdFormatEightByString(String year, String month, String day) {

		//dayが2桁でないとき
		if (day.length() != 2) {
			day = "0" + day;
		}

		//monthが2桁でないとき
		if (month.length() != 2) {
			month = "0" + month;
		}

		String ymd = year + month + day;

		return ymd;
	}


    /**
	 * int日付変換処理
	 *
	 * <p>String型の8文字の日付(YYYYMMDD)に変換する<p>
	 *
	 * @param year 日付(年)
	 * @param month 日付(月)
	 * @param day 日付(日)
	 * @return String 日付(YYYYMMDD)
     */
	public String toStringYmdFormatEightByIntYMD(int year, int month, int day) {

		String ymd = String.valueOf(year) +  String.format("%02d", month) + String.format("%02d", day);

		return ymd;
	}


    /**
	 * String日付変換処理
	 *
	 * <p>String型の8文字の日付(YYYYMMDD)に変換する<p>
	 *
	 * @param ymd 日付(YYYYMMDD)
	 * @return String 表示用の日付(YYYY/MM/DD)
     */
	public String changeDisplayYmdByYMD(String ymd) {

		String displayYmd = ymd.substring(0, 4) + "/" + ymd.substring(4, 6) + "/" + ymd.substring(6, 8);

		return displayYmd;
	}
}
