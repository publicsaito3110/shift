package bl;

import java.util.ArrayList;
import java.util.List;

import bean.UserInfoBean;
import dao.UserInfoDao;

public class UserInfoBl {

	//---------登録しているユーザを表示するメソッド-------------------------------
	public List<UserInfoBean> userSearchDB(){

		//DAOの戻り値をuserInfoListで受け取る
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoDao dao = new UserInfoDao();
		userInfoList = dao.selectUserInfoDB();

		return userInfoList;
	}

	//---------ユーザを新規登録するメソッド-------------------------------------------
	public boolean userSighnUp(List<UserInfoBean> userInfoList){

		//DAOの戻り値をbooleanで受け取る
		UserInfoDao dao = new UserInfoDao();
		boolean sighnUpResult = dao.sighnUpUserDB(userInfoList);

		return sighnUpResult;
	}


}
