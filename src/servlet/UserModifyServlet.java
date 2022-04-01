package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import bean.UserBean;
import bean.ValidationBean;
import bl.UserBl;
import common.CommonUtil;
import common.Const;
import common.ValidationUtil;

/**
 * Servlet implementation class UserModifyServlet
 */
@WebServlet("/UserModifyServlet")
public class UserModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserModifyServlet() {
        super();
        //  Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//user-info-change.jspからの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");
		String delFlag = request.getParameter("delFlag");



		//genderを識別し、女性にチェックがあるか判別
		boolean isCheckedGender2 = CommonUtil.isCheckGenderFemaleByGender(gender);

		//delFlagを識別し、退職済みにチェックがあるか判別
		boolean isCheckedDelFlag = this.isCheckDelFlag(delFlag);



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
		if(isCheckedGender2) {

			//checkboxにチェックを入れる
			request.setAttribute("checkGender2", Const.CHECKBOX_CHECKED);
		}

		//退職済みにチェックが入っていたとき
		if(isCheckedDelFlag) {
			request.setAttribute("checkDelFlag", Const.CHECKBOX_CHECKED);
		}



		//userBeanに値を詰める
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
		valiBean = ValidationUtil.validInputAllStatus(userBean);

		//バリデーションチェックの結果を抽出
		boolean isVali1 = valiBean.isValiId();
		boolean isVali2 = valiBean.isValiName();
		boolean isVali3 = valiBean.isValiNameKana();
		boolean isVali4 = valiBean.isValiGender();
		boolean isVali5 = valiBean.isValiDelFlag();

		//エラーテキストを抽出
		String erId = valiBean.getErId();
		String erName = valiBean.getErName();
		String erNameKana = valiBean.getErNameKana();
		String erGender = valiBean.getErGender();
		String erDelFlag = valiBean.getErDelFlag();

		//エラーテキストを返す
		request.setAttribute("erId", erId);
		request.setAttribute("erName", erName);
		request.setAttribute("erNameKana", erNameKana);
		request.setAttribute("erGender", erGender);
		request.setAttribute("erDelFlag", erDelFlag);


		//バリデーションチェックが1つでもアウトのとき
		if(!isVali1 || !isVali2 || !isVali3 || !isVali4 || !isVali5) {

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


		request.setAttribute("result", true);
		request.setAttribute("resultText", "ユーザー更新に成功しました");

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-modify.jsp").forward(request, response);
	}



	/**
	 * delFlagがdelFlagパターンと一致しているときtrueを返すメソッド
	 */
	public boolean isCheckDelFlag(String delFlag) {


		//delFlagがnullまたは空文字のとき
		if(StringUtils.isEmpty(delFlag)) {
			return false;
		}

		boolean isVali =delFlag.equals(Const.PATTERN_DEL_FLAG);

		//delFlagが退職済み("1")のとき
		if(isVali) {
			return true;
		}

		return false;
	}
}
