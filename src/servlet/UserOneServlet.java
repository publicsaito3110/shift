package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import bl.UserBl;
import common.Const;

/**
 * @author saito
 *
 */
@WebServlet("/UserOneServlet")
public class UserOneServlet extends BaseAdministratorServlet {
	private static final long serialVersionUID = 1L;

    public UserOneServlet() {
        super();
    }


	@Override
	protected void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//更新したいユーザのidをuser-list.jspから受け取る
		String id = request.getParameter("id");


		//-----------
		//SQLの実行
		//-----------

		//idと一致するユーザを取得するuserBeanで受け取る
		UserBl bl = new UserBl();
		UserBean userBean = new UserBean();
		userBean = bl.selectUserOneById(id);

		//dbListの結果を抽出する
		id = userBean.getId();
		String name = userBean.getName();
		String nameKana = userBean.getNameKana();
		String gender = userBean.getGender();
		String delFlag = userBean.getDelFlag();

		// 引き渡す値を設定
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("nameKana", nameKana);
		request.setAttribute("checkGender2", "");
		request.setAttribute("checkDelFlag", "");
		request.setAttribute("valiBean", null);
		request.setAttribute("afterFormFlag", false);

		//genderが女性(2)のとき
		if(Const.GENDER_FEMALE.equals(gender)) {

			//<radio>をチェックする
			request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
		}

		//退職フラグがあるとき
		if(Const.DELETE_FLAG.equals(delFlag)) {

			//<checkbox>をチェックする
			request.setAttribute("checkDelFlag", Const.CHECKBOX_CHECKED);
		}

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-modify.jsp").forward(request, response);
	}
}