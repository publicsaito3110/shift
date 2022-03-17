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
	public static final String valiGana = "ぁ-ん";   //全角ひらがな
	public static final String valiKana = "ァ-ヶー";  //全角カタカナ
	public static final String valiKanji = "\\\\u4E00-\\\\u9FFF\\\\u3005-\\\\u3007";  //全角漢字
	public static final String valiAllowKigou = "ー　 ";  //長音, 半角全角スペース
	public static final String valiGender = "1|2";  //1または2


	//------------------
	//エラーテキスト
	//------------------
	public static final String erId = "半角英数字(4文字)で入力してください";
	public static final String erName = "半角英字または全角日本語(20文字以内)で入力してください";
	public static final String erNameKana = "全角カタカナ(40文字以内)で入力してください";
	public static final String erGender = "不正な値です";

}
