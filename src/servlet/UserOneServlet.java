package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import bl.UserBl;
import common.CommonUtil;
import common.Const;

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



		//idと一致するユーザを取得するuserBeanで受け取る
		UserBean userBean = new UserBean();
		UserBl bl = new UserBl();

		userBean = bl.selectUserOneById(id);


		//dbListの結果を抽出する
		id = userBean.getId();
		String name = userBean.getName();
		String nameKana = userBean.getNameKana();
		String gender = userBean.getGender();
		String delFlag = userBean.getDelFlag();


		//genderが女性かの判別
		String checkGender2 = "";


		//genderが女性(2)のとき<radio>をチェックする
		if(gender.equals(Const.GENDER_FEMALE)) {

			checkGender2 = Const.RADIO_CHECKED;
		}


		//delFlagがあるかの判定
		String checkDelFlag = "";

		//delFlagがnullのとき空文字を返す
		delFlag = CommonUtil.changeEmptyByNull(delFlag);


		//退職フラグがあるとき
		if(delFlag.equals(Const.DELETE_FLAG)) {

			checkDelFlag = Const.RADIO_CHECKED;
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
