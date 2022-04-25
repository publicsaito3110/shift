package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import common.Const;
import dao.UserDao;

/**
 * @author saito
 *
 */
public class UserBl extends BaseBl {

	/**
	 * 未退職ユーザ取得処理
	 *
	 * <p>delFlgがないユーザ全員を取得する</p>
	 *
	 * @param void
	 * @return List<UserBean> ユーザID(id), ユーザ名(name)
	 */
	public UserBean selectUserIdNameAdmFlgLogin(UserBean userBean){

		//DAOの戻り値をdbBeanで受け取る
		UserBean dbBean = new UserBean();
		UserDao dao = new UserDao();
		dbBean = dao.selectUserIdNameAdmFlgLogin(userBean);

		return dbBean;
	}


	/**
	 * 未退職非ログインユーザ取得処理
	 *
	 * <p>delFlgがないユーザかつログインユーザ以外のユーザ全員を取得する</p>
	 *
	 * @param loginUser ログインユーザのID
	 * @return List<UserBean> ユーザID(id), ユーザ名(name)
	 */
	public List<UserBean> selectAllUserIdNameNotDelFlag() {

		//DAOの戻り値をuserListで受け取る
			List<UserBean> userList = new ArrayList<>();
			UserDao dao = new UserDao();
			userList = dao.selectAllUserIdNameNotDelFlag();

			return userList;
	}


	/**
	 * ログイン情報検索処理
	 *
	 * <p>IDとパスワードから登録済みのユーザか検索する<br>
	 * ただし、IDとパスワードが一致したユーザがいなければ取得しない</p>
	 *
	 * @param userBean ユーザID(id), パスワード(password)
	 * @return UserBean ユーザID(id), 名前(name), 管理者フラグ(adminFlag)
	 */
	public List<UserBean> selectUserIdNameNotDelFlagLoginUser(String id) {

		//DAOの戻り値をuserListで受け取る
			List<UserBean> userList = new ArrayList<>();
			UserDao dao = new UserDao();
			userList = dao.selectUserIdNameNotDelFlagLoginUser(id);

			return userList;
	}


	/**
	 * 該当ユーザ取得処理
	 *
	 * <p>キーワードに該当するユーザ全員を取得する<br>
	 * ただし、現在のページ(page)から何件目～(offset)を計算する<br>
	 * offsetの件数目から5件分を取得する<br>
	 * 該当結果がなければ取得しない</p>
	 *
	 * @param page 現在のページ(受け取った後、ページ数からoffsetを計算)
	 * @param keyWord ユーザID,名前,フリガナにを対象にしたキーワード
	 * @return List<UserBean> ユーザID(id), 名前(name), フリガナ(kana), 性別(gender), 検索結果の一致件数(countAll)
	 */
	public List<UserBean> selectUserByKeyWord(String page, String keyWord){


		//受け取ったページに対応したoffsetを取得する
		int offset = this.toIntReturnOffsetByPage(page);

		//DAOの戻り値をuserListで受け取る
		List<UserBean> userList = new ArrayList<>();
		UserDao dao = new UserDao();
		userList = dao.selectUserByKeyWord(offset, keyWord);

		return userList;
	}


	/**
	 * ID一致ユーザ取得処理
	 *
	 * <p>idと一致する1人のユーザを取得する</p>
	 *
	 * @param String id, A user id
	 * @return UserBean, a user of muching id
	 */
	public UserBean selectUserOneById(String id){

		//DAOの戻り値をuserBeanで受け取る
		UserBean userBean = new UserBean();
		UserDao dao = new UserDao();
		userBean = dao.selectUserOneById(id);

		return userBean;
	}


	/**
	 * ユーザ新規登録処理
	 *
	 * <p>ユーザを新規登録する</p>
	 *
	 * @param userBean ユーザの新規追加情報(ID, NAME, NAME_KANA, GENDER, PASSWORD, ADDRESS, TEL, EMAIL, ADMIN_FLG, NOTE)
	 * @return boolean true:ユーザの新規登録が成功したとき false:ユーザの新規登録が失敗したとき
	 */
	public boolean insertUserSignup(UserBean userBean){

		//DAOの戻り値をbooleanで受け取る
		UserDao dao = new UserDao();
		boolean isSignupResult = dao.insertUserSignup(userBean);

		return isSignupResult;
	}


	/**
	 * ユーザ更新処理
	 *
	 * <p>登録済みのユーザを更新する</p>
	 *
	 * @param userBean ユーザの更新情報(ID, NAME, NAME_KANA, GENDER, ADMIN_FLG, DEL_FLAG)
	 * @return boolean true:ユーザの更新が成功したとき false:ユーザの更新が失敗したとき
	 */
	public boolean updateUser(UserBean userBean){

		//DAOの戻り値をUpdateResultで受け取る
		UserDao dao = new UserDao();
		boolean isUpdateResult = dao.updateUserDB(userBean);

		return isUpdateResult;
	}



	/**
	 * ページから件目を計算処理
	 *
	 * <p>指定したページに対応した件目(offset)を計算する</p>
	 *
	 * @param page 現在のページ
	 * @return int 何件目か
	 */
	public int toIntReturnOffsetByPage(String page) {

		//1回のSQLで表示する件数
		int pageLimitPer = Integer.parseInt(Const.PAGE_LIMIT);


		//ユーザ一覧に表示するためページに対応した(データn件目～)に初期値0を代入する
		int offset = Const.OFFSET_FIRST;


		//ページ数を指定されたとき
		if (page != null) {

			//ユーザ一覧するため指定したページに対応した(データn件目～)をoffsetに代入する
			int nowPage = Integer.parseInt(page);
			nowPage = (nowPage - 1) * pageLimitPer;

			offset = nowPage;
		}

		return offset;
	}
}
