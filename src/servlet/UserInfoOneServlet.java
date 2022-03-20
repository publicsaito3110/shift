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
 * Servlet implementation class UserInfoOneServlet
 */
@WebServlet("/UserInfoOneServlet")
public class UserInfoOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoOneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//更新したいユーザのidをuser-list.jspから受け取る
		String id = request.getParameter("id");

		//BLの戻り値をdbListで受け取る
		List<UserInfoBean> dbList = new ArrayList<>();
		UserInfoBl bl = new UserInfoBl();
		dbList = bl.userOneSeachDB(id);


		// 引き渡す値を設定
		request.setAttribute("id", dbList.get(0).getId());
		request.setAttribute("name", dbList.get(0).getName());
		request.setAttribute("nameKana", dbList.get(0).getNameKana());
		request.setAttribute("gender", dbList.get(0).getGender());
		request.setAttribute("delFlag", dbList.get(0).getDelFlag());

		//Form後かどうかの判別
		boolean afterFormFlag = false;
		request.setAttribute("afterFormFlag", afterFormFlag);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-info-change.jsp").forward(request, response);
	}

}
