package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import common.Const;
import dao.UserDao;


public class UserBl {

	/**
	 * 初回ログイン時、1人のユーザの名前を取得するメソッド
	 */
	public UserBean selectUserIdNameAdmFlgLogin(UserBean userBean){

		//DAOの戻り値をdbBeanで受け取る
		UserBean dbBean = new UserBean();
		UserDao dao = new UserDao();
		dbBean = dao.selectUserIdNameAdmFlgLogin(userBean);

		return dbBean;
	}


	/**
	 * 登録しているユーザのidと名前を取得するメソッド
	 */
	public List<UserBean> selectAllUserIdNameNotDelFlag() {

		//DAOの戻り値をuserListで受け取る
			List<UserBean> userList = new ArrayList<>();
			UserDao dao = new UserDao();
			userList = dao.selectAllUserIdNameNotDelFlag();

			return userList;
	}


	/**
	 * 自分以外のユーザのidと名前を取得するメソッド
	 */
	public List<UserBean> selectUserIdNameNotDelFlagLoginUser(String id) {

		//DAOの戻り値をuserListで受け取る
			List<UserBean> userList = new ArrayList<>();
			UserDao dao = new UserDao();
			userList = dao.selectUserIdNameNotDelFlagLoginUser(id);

			return userList;
	}


	/**
	 * キーワードに該当するユーザ全員を表示するメソッド
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
	 * ユーザを新規登録するメソッド
	 */
	public boolean insertUserSignup(UserBean userBean){

		//DAOの戻り値をbooleanで受け取る
		UserDao dao = new UserDao();
		boolean isSignupResult = dao.insertUserSignup(userBean);

		return isSignupResult;
	}


	/**
	 * idと一致する1人のユーザ詳細を取得するメソッド
	 */
	public UserBean selectUserOneById(String id){

		//DAOの戻り値をuserBeanで受け取る
		UserBean userBean = new UserBean();
		UserDao dao = new UserDao();
		userBean = dao.selectUserOneById(id);

		return userBean;
	}


	/**
	 * 1人のユーザ情報を更新メソッド
	 */
	public boolean updateUser(UserBean userBean){

		//DAOの戻り値をUpdateResultで受け取る
		UserDao dao = new UserDao();
		boolean isUpdateResult = dao.updateUserDB(userBean);

		return isUpdateResult;
	}



	/**
	 *指定したページに対応したoffsetを返す
	 *@param String page, got page by user-list.jsp
	 *@return int, calced offset
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
