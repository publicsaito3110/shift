package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BaseNoLoginServlet
 */
@WebServlet("/BaseNoLoginServlet")
public abstract class BaseNoLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseNoLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//---------------
		//sessionの判定
		//---------------

		//セッションを取得
		HttpSession session = request.getSession();

		//セッションが存在しているとき
		if(session != null) {

			//セッションを破棄
			session.invalidate();
		}


		executeNoSession(request, response);
	}


	//継承したクラスはexcuteNoSessionメソッドのオーバライドを強制
	protected abstract void executeNoSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}