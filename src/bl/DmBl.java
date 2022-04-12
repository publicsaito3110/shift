package bl;

import java.util.ArrayList;
import java.util.List;

import bean.DmBean;
import dao.DmDao;

public class DmBl {


	/**
	 * ログインしているユーザが最後に送信したメッセージを非ログインユーザごとに取得するメソッド
	 */
	public List<DmBean> selectSendMsgUserIdLastMsg(String id){

		//DAOの戻り値をscheduleListで受け取る
		List<DmBean> sendMsgList = new ArrayList<>();
		DmDao dao = new DmDao();
		sendMsgList = dao.selectSendMsgUserIdLastMsg(id);

		return sendMsgList;
	}


	/**
	 * ログインしているユーザが最後に受信したメッセージを非ログインユーザごとに取得するメソッド
	 */
	public List<DmBean> selectReceiveMsgUserIdLastMsg(String id){

		//DAOの戻り値をscheduleListで受け取る
		List<DmBean> receiveMsgList = new ArrayList<>();
		DmDao dao = new DmDao();
		receiveMsgList = dao.selectReceiveMsgUserIdLastMsg(id);

		return receiveMsgList;
	}
}
