package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import bean.ValidationBean;
import bl.UserBl;
import common.Const;
import common.ValidationLogic;

/**
 * @author saito
 *
 */
@WebServlet("/UserModifyServlet")
public class UserModifyServlet extends BaseAdministratorServlet {
	private static final long serialVersionUID = 1L;


    public UserModifyServlet() {
        super();
    }


	@Override
	protected void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//user-modify.jspからの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");
		String delFlag = request.getParameter("delFlag");


		//------------------------
		//共通で返す値を設定
		//------------------------
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("nameKana", nameKana);
		request.setAttribute("checkGender2", "");
		request.setAttribute("checkDelFlag", "");
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("popTitle", "ユーザー情報の更新結果");

		//女性にチェックが入っていたとき
		if(Const.GENDER_FEMALE.equals(gender)) {

			//checkboxにチェックを入れる
			request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
		}

		//退職済みにチェックが入っていたとき
		if(Const.DELETE_FLAG.equals(delFlag)) {

			//checkboxにチェックを入れる
			request.setAttribute("checkDelFlag", Const.CHECKBOX_CHECKED);
		}

		//userBeanに入力された値を詰める
		UserBean userBean = new UserBean();
		userBean.setId(id);
		userBean.setName(name);
		userBean.setNameKana(nameKana);
		userBean.setGender(gender);
		userBean.setDelFlag(delFlag);


		//-------------------------
		//バリデーションチェック
		//-------------------------

		//userInfoListを引き渡し、結果とエラーテキストを受け取る
		ValidationBean valiBean = new ValidationBean();
		ValidationLogic validationLogic = new ValidationLogic();
		valiBean = validationLogic.validInputAllStatus(userBean);

		//バリデーションチェックの結果を返す
		request.setAttribute("valiBean", valiBean);

		//バリデーションチェックが1つでもアウトのとき
		if(!valiBean.isValidationAll()) {

			//入力結果の情報を返す
			request.setAttribute("result", false);
			request.setAttribute("resultText", "[エラー] 入力値が不正です");

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-modify.jsp").forward(request, response);
			return;

		}


		//------------
		//SQLの実行
		//------------

		//userInfoListをBLに引き渡し、実行結果を受け取る
		UserBl bl = new UserBl();
		boolean isUpdResult = bl.updateUser(userBean);

		//SQLが実行失敗したとき
		if(!isUpdResult) {

			//入力結果の情報を返す
			request.setAttribute("result", false);
			request.setAttribute("resultText", "[エラー] ユーザー更新に失敗しました");

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-modify.jsp").forward(request, response);
			return;
		}


		//入力結果の情報を返す
		request.setAttribute("result", true);
		request.setAttribute("resultText", "ユーザー更新に成功しました");

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-modify.jsp").forward(request, response);
	}
}