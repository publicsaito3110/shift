package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//セッションのデータを取得
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean)session.getAttribute("USER_BEAN") ;


		//セッションがないとき
		if(userBean == null) {

			//返す値を設定
			request.setAttribute("err", "");


			//画面遷移(ログイン画面)
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}


		//セッションを破棄
		session.invalidate();


		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/logout.jsp").forward(request,response);
	}
}