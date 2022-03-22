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
import bean.ValidationBean;
import bl.UserInfoBl;
import common.CommonUtil;
import common.ValidationUtil;

/**
 * Servlet implementation class UserInfoChangeServlet
 */
@WebServlet("/UserInfoChangeServlet")
public class UserInfoChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//Form後かどうかの判別(全て共通でtrueを返す)
		boolean afterFormFlag = true;


		//user-info-change.jspからの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");
		String delFlag = request.getParameter("delFlag");

		//delFlagがnullのとき""を代入(checkboxが未チェック=>nullのため)
		delFlag = CommonUtil.changeEmpty(delFlag);

		//------------------------
		//共通で返す値を設定
		//------------------------
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("nameKana", nameKana);
		request.setAttribute("gender", gender);
		request.setAttribute("delFlag", delFlag);
		request.setAttribute("afterFormFlag", afterFormFlag);
		request.setAttribute("popTitle", "ユーザー情報の更新結果");


		//userInfoListに値を詰める
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoBean bean = new UserInfoBean();

		bean.setId(id);
		bean.setName(name);
		bean.setNameKana(nameKana);
		bean.setGender(gender);
		bean.setDelFlag(delFlag);

		userInfoList.add(bean);


		//-------------------------
		//バリデーションチェック
		//-------------------------
		List <ValidationBean> valiList = new ArrayList<>();
		valiList = ValidationUtil.validInputAllStatus(userInfoList);

		//バリデーションチェックの結果を抽出
		boolean vali1 = valiList.get(0).isValiId();
		boolean vali2 = valiList.get(0).isValiName();
		boolean vali3 = valiList.get(0).isValiNameKana();
		boolean vali4 = valiList.get(0).isValiGender();
		boolean vali5 = valiList.get(0).isValiDelFlag();

		//エラーテキストを抽出
		String erId = valiList.get(0).getErId();
		String erName = valiList.get(0).getErName();
		String erNameKana = valiList.get(0).getErNameKana();
		String erGender = valiList.get(0).getErGender();
		String erDelFlag = valiList.get(0).getErDelFlag();

		//エラーテキストを返す
		request.setAttribute("erId", erId);
		request.setAttribute("erName", erName);
		request.setAttribute("erNameKana", erNameKana);
		request.setAttribute("erGender", erGender);
		request.setAttribute("erDelFlag", erDelFlag);


		//バリデーションチェックが1つでもアウトのとき
		if(!vali1 || !vali2 || !vali3 || !vali4 || !vali5) {

			//入力結果の情報を返す
			request.setAttribute("result", false);
			request.setAttribute("resultText", "[エラー] 入力値が不正です");

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-info-change.jsp").forward(request, response);
			return;

		}



		//------------
		//SQLの実行
		//------------
		//userInfoListをBLに引き渡し、実行結果を受け取る
		UserInfoBl bl = new UserInfoBl();
		boolean updResult = bl.updateUserDB(userInfoList);


		//SQLが実行失敗したとき
		if(!updResult) {

			//入力結果の情報を返す
			request.setAttribute("result", false);
			request.setAttribute("resultText", "[エラー] ユーザー更新に失敗しました");
			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-info-change.jsp").forward(request, response);
			return;
		}


		request.setAttribute("result", true);
		request.setAttribute("resultText", "ユーザー更新に成功しました");
		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-info-change.jsp").forward(request, response);
	}

}
