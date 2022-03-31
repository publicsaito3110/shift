package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import dao.UserDao;


public class UserBl {

	/**
	 * 初回ログイン時、1人のユーザの名前を取得するメソッド
	 */
	public String selectUserLogin(List<UserBean> userInfoList){

		//DAOの戻り値をnameで受け取る
		String name = null;
		UserDao dao = new UserDao();
		name = dao.selectUserLogin(userInfoList);

		return name;
	}


	/**
	 * 登録しているユーザのidと名前を取得するメソッド
	 */
	public List<UserBean> selectUserIdNameNotDelFlag() {

		//DAOの戻り値をscheduleListで受け取る
			List<UserBean> userList = new ArrayList<>();
			UserDao dao = new UserDao();
			userList = dao.selectUserIdNameNotDelFlag();

			return userList;
	}

	/**
	 * 登録しているユーザ全員を取得する
	 */
	public List<UserBean> selectUserAll(int offset){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserBean> userInfoList = new ArrayList<>();
		UserDao dao = new UserDao();
		userInfoList = dao.selectUserAll(offset);

		return userInfoList;
	}

	/**
	 * キーワードに該当するユーザ全員を表示するメソッド
	 */
	public List<UserBean> selectUserByKeyWord(int offset, String keyWord){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserBean> userInfoList = new ArrayList<>();
		UserDao dao = new UserDao();
		userInfoList = dao.selectUserByKeyWord(offset, keyWord);

		return userInfoList;
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
	 * 1人のユーザ詳細を取得するメソッド
	 */
	public List<UserBean> selectUserOne(String id){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserBean> userInfoList = new ArrayList<>();
		UserDao dao = new UserDao();
		userInfoList = dao.selectUserOne(id);

		return userInfoList;
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
