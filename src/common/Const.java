package common;

/**
 * @author saito
 *
 */
public class Const {

	private Const() {
		// インスタンス化できないように設定
	}

	public static final String DO_GET = "GET";   //doGet
	public static final String DO_POST = "POST";   //doPost

	public static final String[] NEWS_CTG_ARRAY = {"お知らせ", "重要", "シフト"};   //お知らせの区分
	public static final String PATTERN_NWS_CTG = "1|2|3";   //[正規表現]お知らせのカテゴリー(1または2または3)
	public static final int NEWS_LIMIT_DAY_BEFORE_NOW = 14;   //お知らせに表示する現在～日までの日数

	public static final String GENDER_MAN = "1";   //性別が男
	public static final String GENDER_FEMALE = "2";   //性別が女
	public static final String DELETE_FLAG = "1";   //deleteFlagがある

	public static final String SQLTYPE_INSERT = "INSERT";   //sqltypeがINSERT
	public static final String SQLTYPE_UPDATE = "UPDATE";   //sqltypeがUPDATE
	public static final String SQLTYPE_DELETE = "DELETE";   //sqltypeがDELETE

	public static final String OPTION_SELECTED = "selected";   //<option>のセレクト
	public static final String CHECKBOX_CHECKED = "checked";   //<radio>のチェック
	public static final String INPUT_DISABLED = "disabled";   //<button>の無効


	public static final int OFFSET_FIRST = 0;   //SQL1-5件目を指定
	public static final int MAX_PAGE = 5;      //JSPに1回で表示する最大ページ数
	public static final String PAGE_LIMIT = "5";   //SQL5件分を取得
	public static final String PERCENT = "%";   //%(SQL用)

	public static final int DISPLAY_LAST_MSG_LIMIT_LENGTH = 20;   //最終メッセージ履歴に表示する最大文字数


	public static final int NEWS_ADD_MAX_MONTH = 3;   //お知らせを登録できる日付の範囲(3ヵ月)


	//-------------------------
	//正規表現
	//-------------------------
	public static final String PATTERN_NUM = "0-9";   //半角数字
	public static final String PATTERN_ALPHA = "a-zA-Z"; //半角英字
	public static final String PATTERN_KIGOU_NOT＿VER = "!-,./:-@\\[-~";  //長音の全ての記号
	public static final String PATTERN_KANA = "ァ-ヶー";  //全角カタカナ
	public static final String PATTERN_GENDER = "1|2";  //性別(1または2)
	public static final String PATTERN_KIGOU_VER = "-";  //記号(長音のみ)
	public static final String PATTERN_EMAIL = "^(([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*)|(\"[^\"]*\"))@[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*$";  //メールの正規表現
	public static final String PATTERN_ADMIN_FLAG = "1";  //adminFlag 1
	public static final String PATTERN_DEL_FLAG = "1";  //delFlag 1



}
