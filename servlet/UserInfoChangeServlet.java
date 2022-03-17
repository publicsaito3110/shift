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
import common.CommonUtil;
import common.Const;

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


		//SQLの実行結果を返す
		boolean updResult = false;
		//Form後かどうかの判別(全て共通でtrueを返す)
		boolean afterFormFlag = true;
		request.setAttribute("afterFormFlag", afterFormFlag);


		//user-info-change.jspからの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");

		//共通で引き渡す値を設定
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("nameKana", nameKana);
		request.setAttribute("gender", gender);


		//バリデーションチェックのエラーテキスト
		String erId = "";
		String erName = "";
		String erNameKana = "";
		String erGender = "";


		//-------------------------
		//バリデーションチェック
		//-------------------------
		boolean vali1 = CommonUtil.validIdStatus(id);
		boolean vali2 = CommonUtil.validNameStatus(name);
		boolean vali3 = CommonUtil.validNameKanaStatus(nameKana);
		boolean vali4 = CommonUtil.validGenderStatus(gender);


		if(!vali1 || !vali2 || !vali3 || !vali4) {               //バリデーションチェックが1つでもアウトのとき

			//バリデーションチェックがエラーのときエラ－テキストをセットする
			if(!vali1) {     //idがアウトのとき
				erId = Const.erId;
			}
			if(!vali2) {     //nameがアウトのとき
				erName = Const.erName;
			}
			if(!vali3) {     //nameKanaがアウトのとき
				erNameKana = Const.erNameKana;
			}
			if(!vali4) {     //genderがアウトのとき
				erGender = Const.erGender;
			}

			//エラーの情報と入力された値を返す
			request.setAttribute("erId", erId);
			request.setAttribute("erName", erName);
			request.setAttribute("erNameKana", erNameKana);
			request.setAttribute("erGender", erGender);
			request.setAttribute("updResult", updResult);
			request.setAttribute("resulText", "[エラー] 入力値が不正です");

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-info-change.jsp").forward(request, response);
			return;

		}

		//------------
		//SQLの実行
		//------------
		//ArrayListに値を詰めてBLに引き渡す
		List<UserInfoBean> userInfoList = new ArrayList<>();
		UserInfoBean bean = new UserInfoBean();

		bean.setId(id);
		bean.setName(name);
		bean.setNameKana(nameKana);
		bean.setGender(gender);

		userInfoList.add(bean);

		//beanListをBLに引き渡す
		UserInfoBl bl = new UserInfoBl();
		updResult = bl.updateUserDB(userInfoList);                //ユーザの更新結果を取得


		//バリデーションチェックとSQLの結果を共通で返す
		request.setAttribute("erId", erId);
		request.setAttribute("erName", erName);
		request.setAttribute("erNameKana", erNameKana);
		request.setAttribute("erGender", erGender);
		request.setAttribute("updResult", updResult);


		//--------------
		//SQLの判定
		//--------------
		if(!updResult) {      //SQLが実行失敗したとき

			//入力された値を返す
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("nameKana", nameKana);
			request.setAttribute("resulText", "[エラー] ユーザー更新に失敗しました");
			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
			return;
		}


		request.setAttribute("resulText", "ユーザー更新に成功しました");
		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-info-change.jsp").forward(request, response);
	}

}
