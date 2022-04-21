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


    /**
     * year, month, day(int) をymd(String8桁)で返す
     * @param int year, int month, int day, Date of Received LocalDate
     * @return String, Change date of state to YYYYMMDD
     */
	public String toStringYmdFormatEightByIntYMD(int year, int month, int day) {

		String ymd = String.valueOf(year) +  String.format("%02d", month) + String.format("%02d", day);

		return ymd;
	}


    /**
     * ymdを画面に表示用の日付(YYYY/MM/DD)で返す
     * @param int year, int month, int day, Date of Received LocalDate
     * @return String, Change date of state to YYYYMMDD
     */
	public String changeDisplayYmdByYMD(String ymd) {

		String displayYmd = ymd.substring(0, 4) + "/" + ymd.substring(4, 6) + "/" + ymd.substring(6, 8);

		return displayYmd;
	}
}
