package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DmBean;
import bean.UserBean;
import bl.DmBl;
import bl.UserBl;

/**
 * Servlet implementation class DmAddressServlet
 */
@WebServlet("/DmAddressServlet")
public class DmAddressServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DmAddressServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//sessionからログインしているユーザのidを受け取る
		String id = "A001";


		//-----------
		//SQLの実行
		//-----------

		//退職していないユーザ(id,name)をuserListで取得
		List<UserBean> userList = new ArrayList<>();
		UserBl bl = new UserBl();

		userList = bl.selectUserIdNameNotDelFlag();

		//ログインしているユーザが最後にメッセージを送信したメッセージをsendMsgListで取得する
		List<DmBean> sendMsgList = new ArrayList<>();
		DmBl bl2 = new DmBl();

		sendMsgList = bl2.selectSendMsgUserIdLastMsg(id);

		//ログインしているユーザが最後にメッセージを受信したメッセージをreceiveMsgListで取得する
		List<DmBean> receiveMsgList = new ArrayList<>();

		receiveMsgList = bl2.selectReceiveMsgUserIdLastMsg(id);

		//---------------------------------------------------------
		//最後に送信したメッセージと最後に受信したメッセージを判別
		//---------------------------------------------------------

		//sendMsgListとreceiveMsgListから必要な値を抽出し、格納する
		List<DmBean> msgList = new ArrayList<>();
		DmBean bean = new DmBean();

		//メッセージを送受信していないとき
		if (sendMsgList.isEmpty() && receiveMsgList.isEmpty()) {
			bean.setMsg("メッセージはありません");
			msgList.add(bean);

			//画面遷移
		}



//		//msgListの要素(DmBeanオブジェクト)をmsgDateの降順に並び変える
//		Collections.sort(sendMsgList, new compareMsgDateAndSort());


		//引き渡す値を設定
		request.setAttribute("userList", userList);
		request.setAttribute("msgList", msgList);

		//画面遷移
//		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);
	}


	private long toLongDateByMsgDate(String msgDate) {

		//StringBuilderから数字以外を削除する
		StringBuilder sb = new StringBuilder();

		sb.append(msgDate);
		sb.delete(4, 5);
		sb.delete(6, 7);
		sb.delete(8, 9);
		sb.delete(10, 11);
		sb.delete(12, 13);

		//long型へ変換
		long date = Long.parseLong(sb.toString());

		return date;
	}


	private long returnBiggerByMsgDate(long dateSML, long dateRML) {

		//
		if (dateRML < dateSML) {
			return dateSML;
		}

		return dateRML;
	}



	private class compareMsgDateAndSort implements Comparator<DmBean> {

		@Override
		public int compare(DmBean c1, DmBean c2) {


			//msgDateを数字(long)に変換
			long c1Date = this.toLongDateByMsgDate(c1.getMsgDate());
			long c2Date = this.toLongDateByMsgDate(c2.getMsgDate());

			if(c1Date > c2Date) {
				return -1;
			} else if(c1Date < c2Date) {
				return 1;
			} else {
				return c1.getSendUser().compareTo(c2.getSendUser());
			}
		}


		private long toLongDateByMsgDate(String msgDate) {

			//StringBuilderから数字以外を削除する
			StringBuilder sb = new StringBuilder();

			sb.append(msgDate);
			sb.delete(4, 5);
			sb.delete(6, 7);
			sb.delete(8, 9);
			sb.delete(10, 11);
			sb.delete(12, 13);

			//long型へ変換
			long date = Long.parseLong(sb.toString());

			return date;
		}
	}
}