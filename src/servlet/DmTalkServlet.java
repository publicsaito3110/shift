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
@WebServlet("/DmTalkServlet")
public class DmTalkServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;


    public DmTalkServlet() {
        super();
    }


    @Override
	protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//sessionからログインしているユーザのidを受け取る
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");
		String id = sessionUserBean.getId();

		//dm-address.jspから値を受け取る
		String receiveUser = request.getParameter("receiveUser");


		//-----------
		//SQLの実行
		//-----------

		//送信相手の情報をuserBeanで受け取る
		UserBean userBean = new UserBean();
		UserBl bl = new UserBl();

		userBean = bl.selectUserOneById(receiveUser);

		//送信相手の名前を取得する
		String receiveUserName = userBean.getName();

		//メッセージ履歴をtalkListで受け取る
		List<DmBean> talkList = new ArrayList<>();
		DmBl bl2 = new DmBl();

		talkList = bl2.selectTalkByUser(id, receiveUser);

		//返す値を設定
		request.setAttribute("talkList", talkList);
		request.setAttribute("msgToId", receiveUser);
		request.setAttribute("msgToName", receiveUserName);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/dm-talk.jsp").forward(request, response);
	}
}
