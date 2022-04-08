package common;

public class CommonLogic {


	/**
	 * year, month, day をymd(String8文字)で返す
	 * @param String year, String month, String day, Date of year, month, day
	 * @return String, Change state to YYYYMMDD
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
}
