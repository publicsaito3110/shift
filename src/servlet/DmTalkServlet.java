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

/**
 * Servlet implementation class DmTalkServlet
 */
@WebServlet("/DmTalkServlet")
public class DmTalkServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DmTalkServlet() {
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

		//dm-address.jspから値を受け取る
		String receiveUser = request.getParameter("receiveUser");


		//-----------
		//SQLの実行
		//-----------

		//戻り値をmsgListで受け取る
		List<DmBean> talkList = new ArrayList<>();
		DmBl bl = new DmBl();

		talkList = bl.selectTalkByUser(id, receiveUser);

		//返す値を設定
		request.setAttribute("talkList", talkList);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/dm-talk.jsp").forward(request, response);
	}
}
