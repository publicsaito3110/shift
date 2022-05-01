package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DmBean;
import bean.UserBean;
import bl.DmBl;
import bl.UserBl;

/**
 * @author saito
 *
 */
@WebServlet("/DmAddressServlet")
public class DmAddressServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;


	public DmAddressServlet() {
		super();
	}


	@Override
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
		userList = bl.selectUserIdNameNotDelFlagLoginUser(id);

		//ログインしているユーザの最後のメッセージををdbListで取得する
		List<DmBean> chatList = new ArrayList<>();
		DmBl bl2 = new DmBl();
		chatList = bl2.selectLastMsgByLoginId(id);


		//-------------------------
		//最後のメッセージを判別
		//-------------------------

		//ログインしているユーザがメッセージを一度も送受信していないとき
		if (chatList.isEmpty()) {

			//chatListに結果を代入
			DmBean bean = new DmBean();
			bean.setMsg("メッセージはありません");
			chatList.add(bean);

			//引き渡す値を設定
			request.setAttribute("userList", userList);
			request.setAttribute("chatList", chatList);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/dm-address.jsp").forward(request, response);
			return;
		}

		//引き渡す値を設定
		request.setAttribute("userList", userList);
		request.setAttribute("chatList", chatList);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/dm-address.jsp").forward(request, response);
	}
}