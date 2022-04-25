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
 * @author saito
 *
 */
@WebServlet("/BaseLoginServlet")
public abstract class BaseLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseLoginServlet() {
        super();
    }

    @Override
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//--------------------
		//ssesionの有無の判定
		//--------------------

		//セッションのデータをuserBeanで取得
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");

		//セッションが存在していないとき
		if(sessionUserBean == null) {


			//引き渡す値を設定
			request.setAttribute("er", "");

			//画面遷移(ログイン画面)
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}


		//--------------
		//管理者の判定
		//--------------

		//
		boolean isAdministrator = sessionUserBean.isAdministrator();

		//引き渡す値を設定
		request.setAttribute("isAdministrator", isAdministrator);

		existSession(request, response);
	}

	//継承したクラスはexistSessionメソッドのオーバライドを強制
	protected abstract void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
