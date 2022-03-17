package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserInfoBean;
import dao.UserInfoDao;

public class UserInfoBl {

	//---------登録しているユーザ全員を表示するメソッド-------------------------------
	public List<UserInfoBean> userAllSearchDB(){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoDao dao = new UserInfoDao();
		userInfoList = dao.selectUserInfoAllDB();

		return userInfoList;
	}

	//---------ユーザを新規登録するメソッド-------------------------------------------
	public boolean userSighnUp(List<UserInfoBean> userInfoList){

		//DAOの戻り値をbooleanで受け取る
		UserInfoDao dao = new UserInfoDao();
		boolean sighnUpResult = dao.sighnUpUserDB(userInfoList);

		return sighnUpResult;
	}


	//---------1人のユーザ詳細を取得するメソッド-----------------------------------------
	public List<UserInfoBean> userPerSeachDB(String id){
		//DAOの戻り値をuserInfoListで受け取る
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoDao dao = new UserInfoDao();
		userInfoList = dao.selectUserInfoPerDB(id);

		return userInfoList;
	}

	//---------1人のユーザ情報を更新メソッド-----------------------------------------
	public boolean updateUserDB(List<UserInfoBean> userList){
		//DAOの戻り値をUpdateResultで受け取る
		UserInfoDao dao = new UserInfoDao();
		boolean UpdateResult = dao.updateUserDB(userList);

		return UpdateResult;
	}

}
