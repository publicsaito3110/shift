package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DmBean;
import bean.UserBean;
import bl.DmBl;
import bl.UserBl;

/**
 * Servlet implementation class DmAddressServlet
 */
@WebServlet("/DmAddressServlet")
public class DmAddressServlet extends BaseLoginServlet {
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
	protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//sessionからログインしているユーザのidを受け取る
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");
		String id = sessionUserBean.getId();


		//-----------
		//SQLの実行
		//-----------

		//退職していないユーザ(id,name)をuserListで取得
		List<UserBean> userList = new ArrayList<>();
		UserBl bl = new UserBl();

		userList = bl.selectUserIdNameNotDelFlag();

		//ログインしているユーザの最後のメッセージををdbListで取得する
		List<DmBean> dbList = new ArrayList<>();
		DmBl bl2 = new DmBl();

		dbList = bl2.selectLastMsgByLoginId(id);


		//---------------------------------------------------------
		//最後に送信したメッセージと最後のメッセージを判別
		//---------------------------------------------------------

		//dbListから必要な値を抽出し、格納する
		List<DmBean> chatList = new ArrayList<>();
		DmBean bean = new DmBean();

		//ログインしているユーザがメッセージを一度も送受信していないとき
		if (dbList.isEmpty()) {

			//chatListに結果を代入
			bean.setMsg("メッセージはありません");
			chatList.add(bean);

			//引き渡す値を設定
			request.setAttribute("userList", userList);
			request.setAttribute("chatList", chatList);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/dm-address.jsp").forward(request, response);
			return;
		}

		//dbListをchatListに移し変える
		chatList = dbList;

		//引き渡す値を設定
		request.setAttribute("userList", userList);
		request.setAttribute("chatList", chatList);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/dm-address.jsp").forward(request, response);
	}
}