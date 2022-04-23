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
import common.CommonUtil;

/**
 * Servlet implementation class DmSendServlet
 */
@WebServlet("/DmSendServlet")
public class DmSendServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;

    public DmSendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void existSession(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {


		//sessionからログインしているユーザのidを受け取る
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");
		String id = sessionUserBean.getId();

		//dm-address.jspから値を受け取る
		String receiveUser = request.getParameter("receiveUser");
		String msg = request.getParameter("msg");

		//入力値(msg)をエスケープ処理
		msg = CommonUtil.replaceEscapeChar(msg);


		//-----------
		//SQLの実行
		//-----------

		//dmBeanに受け取った値を格納する
		DmBean msgBean = new DmBean();

		msgBean.setSendUser(id);
		msgBean.setReceiveUser(receiveUser);
		msgBean.setMsg(msg);

		//戻り値をtalkListで受け取る
		DmBl bl = new DmBl();

		boolean isResult = bl.insertSendMsgByMsgBean(msgBean);

		//SQL失敗
		if (!isResult) {

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/dm-talk.jsp").forward(request, response);
			return;
		}

		//戻り値をtalkListで受け取る
		List<DmBean> talkList = new ArrayList<>();

		talkList = bl.selectTalkByUser(id, receiveUser);

		//返す値を設定
		request.setAttribute("talkList", talkList);
		request.setAttribute("msgToId", receiveUser);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/dm-talk.jsp").forward(request, response);
	}
}