package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author saito
 *
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
    }


    @Override
	protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//セッションのデータを取得
		HttpSession session = request.getSession();

		//セッションを破棄
		session.invalidate();

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/logout.jsp").forward(request,response);
	}
}