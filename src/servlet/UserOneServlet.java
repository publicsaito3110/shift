package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import bl.UserBl;

/**
 * Servlet implementation class UserOneServlet
 */
@WebServlet("/UserOneServlet")
public class UserOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserOneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//更新したいユーザのidをuser-list.jspから受け取る
		String id = request.getParameter("id");

		//BLの戻り値をdbListで受け取る
		List<UserBean> dbList = new ArrayList<>();
		UserBl bl = new UserBl();
		dbList = bl.selectUserOne(id);


		//dbListの結果を抽出する
		id = dbList.get(0).getId();
		String name = dbList.get(0).getName();
		String nameKana = dbList.get(0).getNameKana();
		String gender = dbList.get(0).getGender();
		String delFlag = dbList.get(0).getDelFlag();


		//genderが女性かの判別
		String checkGender2 = "";

		//genderが女性(2)のとき<radio>をチェックする
		if(gender == "2") {
			checkGender2 = "checked";
		}

		//<checkbox>退職フラグ(checkDelFlag)があるかの判定
		String checkDelFlag = "";

		//退職フラグがあるとき
		if(delFlag == "1") {
			checkDelFlag = "checked";
		}



		// 引き渡す値を設定
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("nameKana", nameKana);
		request.setAttribute("checkGender2", checkGender2);
		request.setAttribute("checkDelFlag", checkDelFlag);

		request.setAttribute("erId", "");
		request.setAttribute("erName", "");
		request.setAttribute("erNameKana", "");
		request.setAttribute("erGender", "");
		request.setAttribute("erDelFlag", "");
		request.setAttribute("afterFormFlag", false);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-modify.jsp").forward(request, response);
	}

}
