package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
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
	public List<UserBean> selectUserIdNameNotDelFlag() {

		//DAOの戻り値をuserListで受け取る
			List<UserBean> userList = new ArrayList<>();
			UserDao dao = new UserDao();
			userList = dao.selectUserIdNameNotDelFlag();

			return userList;
	}

	/**
	 * 登録しているユーザ全員を取得する
	 */
	public List<UserBean> selectUserAll(int offset){

		//DAOの戻り値をuserListで受け取る
		List<UserBean> userList = new ArrayList<>();
		UserDao dao = new UserDao();
		userList = dao.selectUserAll(offset);

		return userList;
	}

	/**
	 * キーワードに該当するユーザ全員を表示するメソッド
	 */
	public List<UserBean> selectUserByKeyWord(int offset, String keyWord){

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

}
