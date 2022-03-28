package common;

public class Const {

	private Const() {
		// インスタンス化できないように設定
	}

	public static String optionSelected = "selected";   //半角数字

	//-------------------------
	//正規表現
	//-------------------------
	public static final String valiNum = "0-9";   //半角数字
	public static final String valiAlpha = "a-zA-Z"; //半角英字
	public static final String valiZenkaku = "^-~｡-ﾟ";  //全角全部//TODO
	public static final String valiKana = "ァ-ヶー";  //全角カタカナ
	public static final String valiGender = "1|2";  //1または2
	public static final String valiAllowKigouVer = "-";  //長音
	public static final String valiEmail = "^(([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*)|(\"[^\"]*\"))@[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*$";  //メールの正規表現
	public static final String valiDelFlag = "1";  //1




}
