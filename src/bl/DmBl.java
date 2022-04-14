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
		List<DmBean> sendMsgList = new ArrayList<>();
		DmDao dao = new DmDao();
		sendMsgList = dao.selectLastMsgByLoginId(id);

		return sendMsgList;
	}
}
