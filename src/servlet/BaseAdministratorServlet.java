package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import common.Const;

/**
 * @author saito
 *
 */
@WebServlet("/BaseAdministratorServlet")
public abstract class BaseAdministratorServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseAdministratorServlet() {
        super();
    }


    @Override
    protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	isAdministoratorMain(request, response);
    }


	protected void isAdministoratorMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//----------------------
		//管理者かどうかの判別
		//----------------------

		//セッションのデータをuserBeanで取得
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");

		//sessionUserBeanからadminFlagを取得
		String adminFlag = sessionUserBean.getAdminFlag();

		//adminFlagが管理者パターンと一致しないとき
		if (!Const.PATTERN_ADMIN_FLAG.equals(adminFlag)) {

			//画面遷移(ホーム画面)エラーの処理も追加
			request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
			return;
		}

		isAdministorator(request, response);
	}

	//継承したクラスはisAdministoratorメソッドのオーバライドを強制
	protected abstract void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
