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
 * Servlet implementation class UserListServlet
 */
@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		//Form後かどうかの判別
		boolean afterFormFlag = false;

		//------------
		//SQLの実行
		//------------
		//BLの戻り値をdbListで受け取る
		List<UserInfoBean> dbList = new ArrayList<>();
		UserInfoBl bl = new UserInfoBl();
		dbList = bl.selectUserInfoAllDB();




		// 引き渡す値を設定
		request.setAttribute("afterFormFlag", afterFormFlag);
		request.setAttribute("dbList", dbList);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
