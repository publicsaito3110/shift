package bl;

import java.util.ArrayList;
import java.util.List;

import bean.DmBean;
import dao.DmDao;

public class DmBl {


	/**
	 * ログインしているユーザの最後のメッセージを他のユーザごとに取得するメソッド
	 */
	public List<DmBean> selectLastMsgByLoginId(String id){

		//DAOの戻り値をscheduleListで受け取る
		List<DmBean> msgList = new ArrayList<>();
		DmDao dao = new DmDao();
		msgList = dao.selectLastMsgByLoginId(id);

		return msgList;
	}
}
