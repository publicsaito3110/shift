package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserInfoBean;
import dao.UserInfoDao;


public class UserInfoBl {

	/**
	 * 初回ログイン時、1人のユーザの名前を取得するメソッド
	 */
	public String selectUserLoginDB(List<UserInfoBean> userInfoList){

		//DAOの戻り値をnameで受け取る
		String name = null;
		UserInfoDao dao = new UserInfoDao();
		name = dao.selectUserLoginDB(userInfoList);

		return name;
	}

	/**
	 * 登録しているユーザ全員を表示する
	 */
	public List<UserInfoBean> selectUserInfoAllDB(){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoDao dao = new UserInfoDao();
		userInfoList = dao.selectUserInfoAllDB();

		return userInfoList;
	}

	/**
	 * キーワードに該当するユーザ全員を表示するメソッド
	 */
	public List<UserInfoBean> selectUserKeyWordDB(String keyWord){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoDao dao = new UserInfoDao();
		userInfoList = dao.selectUserKeyWordDB(keyWord);

		return userInfoList;
	}

	/**
	 * ユーザを新規登録するメソッド
	 */
	public boolean sighnUpUserDB(List<UserInfoBean> userInfoList){

		//DAOの戻り値をbooleanで受け取る
		UserInfoDao dao = new UserInfoDao();
		boolean sighnUpResult = dao.sighnUpUserDB(userInfoList);

		return sighnUpResult;
	}

	/**
	 * 1人のユーザ詳細を取得するメソッド
	 */
	public List<UserInfoBean> selectUserOneDB(String id){
		//DAOの戻り値をuserInfoListで受け取る
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoDao dao = new UserInfoDao();
		userInfoList = dao.selectUserOneDB(id);

		return userInfoList;
	}

	/**
	 * 1人のユーザ情報を更新メソッド
	 */
	public boolean updateUserDB(List<UserInfoBean> userList){
		//DAOの戻り値をUpdateResultで受け取る
		UserInfoDao dao = new UserInfoDao();
		boolean UpdateResult = dao.updateUserDB(userList);

		return UpdateResult;
	}

}
