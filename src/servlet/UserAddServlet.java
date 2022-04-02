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
import common.ValidationUtil;

/**
 * Servlet implementation class UserAddServlet
 */
@WebServlet("/UserAddServlet")
public class UserAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAddServlet() {
        super();
        // Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//返す値を設定
		request.setAttribute("afterFormFlag", false);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//共通で返す値を設定
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("popTitle", "ユーザー新規登録結果");


		//sighn-up.jsp からの値を受け取る
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


		//genderを識別し、女性にチェックがあるか判別
		boolean isCheckedGender2 = CommonUtil.isCheckGenderFemaleByGender(gender);

		//adminFlagを識別し、管理者にチェックがあるか判別
		boolean isCheckedAdminFlag = CommonUtil.isCheckAdministratorByAdminFlag(adminFlag);


		//userBeanに値を詰める
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


		//--------------------------
		//バリデーションチェック
		//--------------------------

		//userInfoを引き渡し、結果とエラーテキストを受け取る
		ValidationBean valiBean = new ValidationBean();
		valiBean = ValidationUtil.validInputAllStatus(userBean);

		//バリデーションチェックの結果を抽出
		boolean isVali1 = valiBean.isValiId();
		boolean isVali2 = valiBean.isValiName();
		boolean isVali3 = valiBean.isValiNameKana();
		boolean isVali4 = valiBean.isValiGender();
		boolean isVali5 = valiBean.isValiPassword();
		boolean isVali6 = valiBean.isValiAddress();
		boolean isVali7 = valiBean.isValiTel();
		boolean isVali8 = valiBean.isValiEmail();
		boolean isVali9 = valiBean.isValiAdminFlag();
		boolean isVali10 = valiBean.isValiNote();

		//バリデーションチェックのエラーテキストを抽出
		String erId = valiBean.getErId();
		String erName = valiBean.getErName();
		String erNameKana = valiBean.getErNameKana();
		String erGender = valiBean.getErGender();
		String erPassword = valiBean.getErPassword();
		String erAddress = valiBean.getErAddress();
		String erTel = valiBean.getErTel();
		String erEmail = valiBean.getErEmail();
		String erAdminFlag = valiBean.getErAdminFlag();
		String erNote = valiBean.getErNote();

		//エラーテキストを返す
		request.setAttribute("erId", erId);
		request.setAttribute("erName", erName);
		request.setAttribute("erNameKana", erNameKana);
		request.setAttribute("erGender", erGender);
		request.setAttribute("erPassword", erPassword);
		request.setAttribute("erAddress", erAddress);
		request.setAttribute("erTel", erTel);
		request.setAttribute("erEmail", erEmail);
		request.setAttribute("erAdminFlag", erAdminFlag);
		request.setAttribute("erNote", erNote);


		//バリデーションチェックが1つでもアウトのとき
		if(!isVali1 || !isVali2 || !isVali3 || !isVali4 || !isVali5 || !isVali6 || !isVali7 || !isVali8 || !isVali9 || ! isVali10) {

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
			if(isCheckedGender2) {

				//checkboxにチェックを入れる
				request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
			}

			//管理者にチェックが入っていたとき
			if(isCheckedAdminFlag) {

				//checkboxにチェックを入れる
				request.setAttribute("checkAdminFlag", Const.CHECKBOX_CHECKED);
			}

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
			return;
		}



		//エスケープ処理
		note = CommonUtil.replaceEscapeChar(note);


		//------------
		//SQLの実行
		//------------
		//userInfoListをBLに引き渡し、実行結果を受け取る
		UserBl bl = new UserBl();
		boolean isSignupResult = bl.insertUserSignup(userBean);


		//SQLが実行失敗したとき
		if(!isSignupResult) {

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
			if(isCheckedGender2) {

				//checkboxにチェックを入れる
				request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
			}

			//管理者にチェックが入っていたとき
			if(isCheckedAdminFlag) {

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

}
