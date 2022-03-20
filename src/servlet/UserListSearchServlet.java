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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		//Form後かどうかの判別
		boolean afterFormFlag = true;

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

			afterFormFlag = false;           //全件表示され、UserListServlet と同様の結果になるため

			// 引き渡す値を設定
			request.setAttribute("afterFormFlag", afterFormFlag);
			request.setAttribute("keyWord", keyWord);
			request.setAttribute("dbList", dbList);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);
			return;
		}



		// 引き渡す値を設定
		request.setAttribute("afterFormFlag", afterFormFlag);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("dbList", dbList);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);

	}

}
