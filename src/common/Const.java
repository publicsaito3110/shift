package common;

public class Const {

	private Const() {
		// インスタンス化できないように設定
	}

	public static String GENDER_MAN = "1";   //性別が男
	public static String GENDER_FEMALE = "2";   //性別が女
	public static String DELETE_FLAG = "1";   //deleteFlagがある


	public static String OPTION_SELECTED = "selected";   //<option>のセレクト
	public static String CHECKBOX_CHECKED = "checked";   //<radio>のチェック


	public static int OFFSET_FIRST = 0;   //SQL1-5件目を指定
	public static int MAX_PAGE = 5;      //JSPに1回で表示する最大ページ数
	public static String PAGE_LIMIT = "5";   //SQL5件分を取得
	public static String PERCENT = "%";   //%(SQL用)



	//-------------------------
	//正規表現
	//-------------------------
	public static final String PATTERN_NUM = "0-9";   //半角数字
	public static final String PATTERN_ALPHA = "a-zA-Z"; //半角英字
	public static final String PATTERN_KIGOU_NOT＿VER = "!-,./:-@\\[-~";  //長音の全ての記号
	public static final String PATTERN_KANA = "ァ-ヶー";  //全角カタカナ
	public static final String PATTERN_GENDER = "1|2";  //1または2
	public static final String PATTERN_KIGOU_VER = "-";  //記号(長音のみ)
	public static final String PATTERN_EMAIL = "^(([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*)|(\"[^\"]*\"))@[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*$";  //メールの正規表現
	public static final String PATTERN_ADMIN_FLAG = "1";  //adminFlag 1
	public static final String PATTERN_DEL_FLAG = "1";  //delFlag 1



}
