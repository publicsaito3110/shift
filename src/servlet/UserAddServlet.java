package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import bean.ValidationBean;
import bl.UserBl;
import common.CommonUtil;
import common.Const;
import common.ValidationLogic;

/**
 * Servlet implementation class UserAddServlet
 */
@WebServlet("/UserAddServlet")
public class UserAddServlet extends BaseAdministratorServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAddServlet() {
        super();
        // Auto-generated constructor stub
    }


	@Override
	protected void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//Postで受け取ったとき
		if (Const.DO_POST.equals(request.getMethod())) {

			//sighn-up.jsp から値を受け取る
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String nameKana = request.getParameter("nameKana");
			String gender = request.getParameter("gender");
			String password = request.getParameter("password");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String adminFlag = request.getParameter("adminFlag");
			String note = request.getParameter("note");

			//入力された値をuserBeanに値を格納する
			UserBean userBean = new UserBean();

			userBean.setId(id);
			userBean.setName(name);
			userBean.setNameKana(nameKana);
			userBean.setGender(gender);
			userBean.setPassword(password);
			userBean.setAddress(address);
			userBean.setTel(tel);
			userBean.setEmail(email);
			userBean.setAdminFlag(adminFlag);
			userBean.setNote(note);

			//女性にチェックがされているかの判別
			boolean isCheckedFemale = Const.GENDER_FEMALE.equals(gender);

			//管理者にチェックがされているかの判別
			boolean isCheckedAdminFlag = Const.PATTERN_ADMIN_FLAG.equals(adminFlag);

			//共通で返す値を設定
			request.setAttribute("afterFormFlag", true);
			request.setAttribute("popTitle", "ユーザー新規登録結果");


			//--------------------------
			//バリデーションチェック
			//--------------------------

			//userInfoを引き渡し、結果とエラーテキストを受け取る
			ValidationBean valiBean = new ValidationBean();
			ValidationLogic validationLogic = new ValidationLogic();

			valiBean = validationLogic.validInputAllStatus(userBean);

			//バリデーションチェックの結果を返す
			request.setAttribute("valiBean", valiBean);

			//バリデーションチェックが1つでもアウトのとき
			if (!valiBean.isValidationAll()) {

				//エラーの情報と入力された値を返す
				request.setAttribute("id", id);
				request.setAttribute("name", name);
				request.setAttribute("nameKana", nameKana);
				request.setAttribute("checkGender2", "");
				request.setAttribute("password", password);
				request.setAttribute("address", address);
				request.setAttribute("tel", tel);
				request.setAttribute("email", email);
				request.setAttribute("checkAdminFlag", "");
				request.setAttribute("note", note);
				request.setAttribute("result", false);
				request.setAttribute("resultText", "[エラー] 入力値が不正です");

				//女性にチェックが入っていたとき
				if (isCheckedFemale) {

					//checkboxにチェックを入れる
					request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
				}

				//管理者にチェックが入っていたとき
				if (isCheckedAdminFlag) {

					//checkboxにチェックを入れる
					request.setAttribute("checkAdminFlag", Const.CHECKBOX_CHECKED);
				}

				//画面遷移
				request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
				return;
			}


			//------------
			//SQLの実行
			//------------

			//エスケープ処理
			note = CommonUtil.replaceEscapeChar(note);

			//userInfoListをBLに引き渡し、実行結果を受け取る
			UserBl bl = new UserBl();
			boolean isSignupResult = bl.insertUserSignup(userBean);

			//SQLが実行失敗したとき
			if (!isSignupResult) {

				//入力された値を返す
				request.setAttribute("id", id);
				request.setAttribute("name", name);
				request.setAttribute("nameKana", nameKana);
				request.setAttribute("checkGender2", "");
				request.setAttribute("password", password);
				request.setAttribute("address", address);
				request.setAttribute("tel", tel);
				request.setAttribute("email", email);
				request.setAttribute("checkAdminFlag", "");
				request.setAttribute("note", note);
				request.setAttribute("result", false);
				request.setAttribute("resultText", "[エラー] 新規登録に失敗しました");

				//女性にチェックが入っていたとき
				if (isCheckedFemale) {

					//checkboxにチェックを入れる
					request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
				}

				//管理者にチェックが入っていたとき
				if (isCheckedAdminFlag) {

					//checkboxにチェックを入れる
					request.setAttribute("checkAdminFlag", Const.CHECKBOX_CHECKED);
				}

				//画面遷移
				request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
				return;
			}


			//受け渡す値を設定
			request.setAttribute("result", true);
			request.setAttribute("resultText", "新規登録に成功しました");

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
		}


		//Getで受け取ったとき
		if (Const.DO_GET.equals(request.getMethod())){

			//返す値を設定
			request.setAttribute("afterFormFlag", false);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
		}
	}
}
