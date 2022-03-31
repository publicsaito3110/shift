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

import bean.UserBean;
import bl.UserBl;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("err", "");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html;charset=UTF-8");     //文字コードをUTF-8で設定
		request.setCharacterEncoding("UTF-8");


		//リクエストパラメータを取得
		String id = request.getParameter("id");
		String password = request.getParameter("password");


		//DBから受け取る値
		String name = "";

		//セッションのデータを取得
		HttpSession session = request.getSession();
		String sessionName = (String)session.getAttribute("USERINFO") ;

		//--------------------
		//セッションの判別
		//--------------------
		//セッションが存在し、ログインしているとき
		if(sessionName != null) {

			name = (String)session.getAttribute("USERINFO");

			//引き渡す値を設定
			request.setAttribute("name", name);

			request.getRequestDispatcher("/WEB-INF/jsp/menu.jsp").forward(request, response);
			return;
		}


		//-----------------
		// 未入力チェック
		//-----------------
		//idまたはpasswordが未入力のとき
		if(id.isEmpty() || password.isEmpty()) {

			request.setAttribute("userId", id);
			request.setAttribute("err", "IDまたはパスワードが未入力です");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}


		//------------------
		// 入力情報をDB検索
		//------------------
		List <UserBean> userInfoList = new ArrayList<>();
		UserBean bean = new UserBean();

		bean.setId(id);
		bean.setPassword(password);
		userInfoList.add(bean);

		//名前の検索結果をnameを受け取る
		UserBl bl = new UserBl();
		name = bl.selectUserLogin(userInfoList);


		//--------------------
		//初回ログインの判定
		//--------------------
		//DBに名前が存在しない
		if(name == null) {

			//引き渡す値を設定
			request.setAttribute("userId", id);
			request.setAttribute("err", "IDまたはパスワードが違います");

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}


		//初回ログイン時sessionに"USERINFO"を作成し、nameを格納
		session.setAttribute("USERINFO", name) ;

		//引き渡す値を設定
		request.setAttribute("name", name);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/menu.jsp").forward(request, response);
	}
}
