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

/**
 * Servlet implementation class UserSighnUpServlet
 */
@WebServlet("/UserSighnUpServlet")
public class UserSighnUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSighnUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//userlist.jspの新規登録ボタンからのdoGet
		request.getRequestDispatcher("/WEB-INF/jsp/sighn-up.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");



		boolean sighnUpResult = false;       //新規登録の結果を返すための変数
		boolean afterForm = true;                   //ポップアップを表示させるための変数


		//sighn-up.jsp からの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");


		//バリデーションチェック
		boolean vali1 = CommonUtil.validIdStatus(id);
		boolean vali2 = CommonUtil.validNameStatus(name);
		boolean vali3 = CommonUtil.validNameKanaStatus(nameKana);
		boolean vali4 = CommonUtil.validGenderStatus(gender);

		//開発用(valiの値を確認)------------------
		System.out.println(vali1);
		System.out.println(vali2);
		System.out.println(vali3);
		System.out.println(vali4);
//---------------------------------------------

		if(vali1 && vali2 && vali3 && vali4) {         //バリデーションチェックがセーフのとき

			//ArrayListに値を詰めてBLに引き渡す
			List<UserInfoBean> userInfoList = new ArrayList<>();
			UserInfoBean bean = new UserInfoBean();

			bean.setId(id);
			bean.setName(name);
			bean.setNameKana(nameKana);
			bean.setGender(gender);

			userInfoList.add(bean);

			UserInfoBl bl = new UserInfoBl();
			//ユーザの新規登録結果を取得
			sighnUpResult = bl.userSighnUp(userInfoList);

		}else{                                             //バリデーションチェックが1つでもアウトのときvaliを渡す
			//valiを渡す
			request.setAttribute("vali1", vali1);
			request.setAttribute("vali2", vali2);
			request.setAttribute("vali3", vali3);
			request.setAttribute("vali4", vali4);

			//入力された値を返す(gender以外)
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("nameKana", nameKana);

			boolean valiEroor = true;                    //バリデーションが1つ以上アウトだった結果を引き渡す
			request.setAttribute("valiEroor", valiEroor);
		}


		//sighn-up.jspに結果を返す
		request.setAttribute("sighnUpResult", sighnUpResult);
		request.setAttribute("afterForm", afterForm);
		request.getRequestDispatcher("/WEB-INF/jsp/sighn-up.jsp").forward(request, response);
	}

}
