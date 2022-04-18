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

		//DAOの戻り値をmsgListで受け取る
		List<DmBean> msgList = new ArrayList<>();
		DmDao dao = new DmDao();
		msgList = dao.selectLastMsgByLoginId(id);

		return msgList;
	}


	/**
	 * ユーザ同士のメッセージを取得するメソッド
	 */
	public List<DmBean> selectTalkByUser(String loginUser, String user){

		//DAOの戻り値をmsgListで受け取る
		List<DmBean> msgList = new ArrayList<>();
		DmDao dao = new DmDao();
		msgList = dao.selectTalkByUser(loginUser,user);

		return msgList;
	}


	/**
	 * 送信したメッセージを格納するメソッド
	 */
	public boolean insertSendMsgByMsgBean(DmBean msgBean) {

		//DAOの戻り値をbooleanで受け取る
		DmDao dao = new DmDao();
		boolean isResult = dao.insertSendMsgByMsgBean(msgBean);

		return isResult;
	}
}
