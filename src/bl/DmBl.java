package bl;

import java.util.ArrayList;
import java.util.List;

import bean.DmBean;
import dao.DmDao;

/**
 * @author saito
 *
 */
public class DmBl extends BaseBl {


	/**
	 * 最終メッセージを取得する処理
	 *
	 * <p>ログインしているユーザがメッセージを送受信している場合、最後のメッセージを非ログインユーザごとに取得する</p>
	 *
	 * @param loginUser セッションから取得したログインユーザーのID
	 * @return List<DmBean> 送信日(msgDate), 送信されたメッセージ(msg), 送信したユーザ(sendUser), 受信したユーザ(erceiveUser)
	 */
	public List<DmBean> selectLastMsgByLoginId(String id){

		//DAOの戻り値をmsgListで受け取る
		List<DmBean> msgList = new ArrayList<>();
		DmDao dao = new DmDao();
		msgList = dao.selectLastMsgByLoginId(id);

		return msgList;
	}


	/**
	 * 二者間のメッセージを取得処理
	 *
	 * <p>ログインユーザと非ログインユーザとのメッセージを現在の日付に近い順で取得する<br>
	 *ただしメッセージがないときは何も取得しない</p>
	 *
	 * @param loginUser セッションから取得したログインユーザのID
	 * @param user 非ログインユーザのID
	 * @return List<DmBean> 送信した日付を表示用に変換MM/DD hh:mm(msgDate), 送信者がログインユーザ:LOGIN_USER 非ログインユーザ:NOT_LOGIN_USER(sendUser)
	 */
	public List<DmBean> selectTalkByUser(String loginUser, String user){

		//DAOの戻り値をmsgListで受け取る
		List<DmBean> msgList = new ArrayList<>();
		DmDao dao = new DmDao();
		msgList = dao.selectTalkByUser(loginUser,user);

		return msgList;
	}


	/**
	 * 送信メッセージを追加処理
	 *
	 * <p>ログインユーザから送信されたメッセージと送信した日時を追加する</p>
	 *
	 * @param msgBean 送信したメッセージ(msg), 送信したユーザ(sendUser), 受信したユーザ(receiveUser)
	 * @return boolean true:メッセージの追加が成功したとき false:メッセージの追加が失敗したとき
	 */
	public boolean insertSendMsgByMsgBean(DmBean msgBean) {

		//DAOの戻り値をbooleanで受け取る
		DmDao dao = new DmDao();
		boolean isResult = dao.insertSendMsgByMsgBean(msgBean);

		return isResult;
	}
}
