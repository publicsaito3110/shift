package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserInfoBean;
import bl.UserInfoBl;

/**
 * Servlet implementation class UserListSearchServlet
 */
@WebServlet("/UserListSearchServlet")
public class UserListSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserListSearchServlet() {
        super();
        //  Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//------------
		//SQLの実行
		//------------

		//登録している全てのユーザ情報をdbListで受け取る
		List<UserInfoBean> dbList = new ArrayList<>();
		UserInfoBl bl = new UserInfoBl();
		dbList = bl.selectUserInfoAllDB();


		// 引き渡す値を設定
		request.setAttribute("afterFormFlag", false);
		request.setAttribute("dbList", dbList);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//sighn-up.jsp からの値を受け取る
		String keyWord = request.getParameter("keyWord");


		//------------
		//SQLの実行
		//------------
		//keyWordをBLに引き渡し、AllayListを戻り値として受け取る
		List <UserInfoBean> dbList = new ArrayList<>();

		UserInfoBl bl = new UserInfoBl();
		dbList = bl.selectUserKeyWordDB(keyWord);


		//---------------------
		//keyWordが""のとき
		//---------------------
		if(keyWord.isEmpty()) {

			// 引き渡す値を設定
			request.setAttribute("afterFormFlag", false);   //全件表示され、doGetと同様の結果になるため
			request.setAttribute("keyWord", keyWord);
			request.setAttribute("dbList", dbList);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);
			return;
		}


		// 引き渡す値を設定
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("dbList", dbList);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);

	}

}
