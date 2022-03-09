package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		//新規登録の結果を返すための変数
		boolean sighnUpResult = false;

		//sighn-up.jsp からの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");


		//------------バリデーションチェック------------------------------------------------
		boolean vali1 = false;
		boolean vali2 = false;
		boolean vali3 = false;
		boolean vali4 = false;

		if(CommonUtil.validIdStatus(id)) {                            //idの判定
			vali1 = true;
		}else if(CommonUtil.validNameStatus(name)) {                 //nameの判定
			vali2 = true;
		}else if(CommonUtil.validNameKanaStatus(nameKana)) {        //nameKanaの判定
			vali3 = true;
		}else if(CommonUtil.validGenderStatus(gender)) {            //genderの判定
			vali4 = true;
		}


		System.out.println(vali1);
		System.out.println(vali2);
		System.out.println(vali3);
		System.out.println(vali4);


//		if(vali1 && vali2 && vali3 && vali4) {         //バリデーションチェックがセーフのとき
//
//			//ArrayListに値を詰めてBLに引き渡す
//			List<UserInfoBean> userInfoList = new ArrayList<>();
//			UserInfoBean bean = new UserInfoBean();
//
//			bean.setId(id);
//			bean.setName(nameKana);
//			bean.setNameKana(nameKana);
//			bean.setGender(gender);
//
//			userInfoList.add(bean);
//
//			UserInfoBl bl = new UserInfoBl();
//			sighnUpResult = bl.userSighnUp(userInfoList);
//
//		}else {         //バリデーションチェックに1つでもアウトのとき
//			request.setAttribute("vali1", vali1);
//			request.setAttribute("vali2", vali2);
//			request.setAttribute("vali3", vali3);
//			request.setAttribute("vali4", vali4);
//		}
//
//		//sighn-up.jspに結果を返す
//		request.setAttribute("sighnUpResult", sighnUpResult);
//		request.getRequestDispatcher("/WEB-INF/jsp/sighn-up.jsp").forward(request, response);
	}

}
