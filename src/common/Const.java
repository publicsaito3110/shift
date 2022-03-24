package common;

public class Const {

	private Const() {
		// インスタンス化できないように設定
	}



	//-------------------------
	//正規表現
	//-------------------------
	public static final String valiNum = "0-9";   //半角数字
	public static final String valiAlpha = "a-zA-Z"; //半角英字
	public static final String valiZenkaku = "^-~｡-ﾟ";  //全角全部
	public static final String valiKana = "ァ-ヶー";  //全角カタカナ
	public static final String valiGender = "1|2";  //1または2
	public static final String valiAllowKigouVer = "-";  //長音
	public static final String valiEmail = "^(([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*)|(\"[^\"]*\"))@[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*$";  //メールの正規表現
	public static final String valiDelFlag = "1";  //1



	//------------------
	//エラーテキスト
	//------------------
	public static final String erId = "半角英数字(4文字)で入力してください";
	public static final String erName = "全角または半角英数字(20文字以内)で入力してください";
	public static final String erNameKana = "全角カタカナ(40文字以内)で入力してください";
	public static final String erGender = "不正な値です";
	public static final String erPassword = "半角英数字(8文字未満)で入力してください";
	public static final String erAddress = "使用できない文字が含まれています";
	public static final String erTel = "半角数字(15文字以内)で入力してください";
	public static final String erEmail = "使用できない文字が含まれています";
	public static final String erNote = "400文字以内で入力してください";
	public static final String erDelFlag = "不正な値です";


}
