package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import bl.UserBl;
import common.CommonUtil;
import common.Const;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends BaseNoLoginServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet(){
        super();
        //Auto-generated constructor stub
    }


	@Override
	protected void executeNoSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Postで受け取ったとき
		if (Const.DO_POST.equals(request.getMethod())) {

		//login.jsp からの値を受け取る
		String inputId = request.getParameter("id");
		String inputPassword = request.getParameter("password");


		//-----------------
		// 未入力チェック
		//-----------------

		//idまたはpasswordが未入力のとき
		if(inputId.isEmpty() || inputPassword.isEmpty()) {

			request.setAttribute("userId", inputId);
			request.setAttribute("err", "IDまたはパスワードが未入力です");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}


		//--------------------
		//セッションの判別
		//--------------------

		//セッションのデータをuserBeanで取得
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");


		//セッションが存在し、ログインしているとき
		if(sessionUserBean != null) {


			//引き渡す値を設定
			request.setAttribute("firstLoginText", "");


			//画面遷移
			request.getRequestDispatcher("/HomeServlet").forward(request, response);
			return;
		}



		//------------------
		// 入力情報をDB検索
		//------------------

		//入力されたidとpasswordをinputUserBeanに格納
		UserBean inputUserBean = new UserBean();

		inputUserBean.setId(inputId);
		inputUserBean.setPassword(inputPassword);

		//ログイン結果をuserBeanを受け取る
		UserBean userBean = new UserBean();
		UserBl bl = new UserBl();
		userBean = bl.selectUserIdNameAdmFlgLogin(inputUserBean);



		//--------------------
		//初回ログインの判定
		//--------------------

		//ログインできなかったとき
		if(userBean.getId() == null) {//TODO

			//引き渡す値を設定
			request.setAttribute("userId", inputId);
			request.setAttribute("err", "IDまたはパスワードが違います");

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}



		//管理者かどうかを判別し、結果をセットする
		boolean isAdministrator = CommonUtil.isCheckAdministratorByAdminFlag(userBean.getAdminFlag());
		userBean.setAdministrator(isAdministrator);


		//sessionに"USER_BEAN"を作成し、userBean(id,name,adminFlag)を格納
		session.setAttribute("USER_BEAN", userBean) ;

		//引き渡す値を設定
		request.setAttribute("firstLoginText", "ようこそ");


		//画面遷移
		request.getRequestDispatcher("/HomeServlet").forward(request, response);
		}


		//-----
		//Get
		//-----
		if (Const.DO_GET.equals(request.getMethod())) {

			//返す値を設定
			request.setAttribute("err", "");

			HttpSession session = request.getSession();
			session.invalidate();

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}
}
