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
 * Servlet implementation class UserSignUpServlet
 */
@WebServlet("/UserSignUpServlet")
public class UserSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Form後かどうかの判別
		boolean afterFormFlag = false;
		request.setAttribute("afterFormFlag", afterFormFlag);
		request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//SQLの実行結果を返す
		boolean sighnUpResult = false;
		//Form後かどうかの判別(全て共通でtrueを返す)
		boolean afterFormFlag = true;
		request.setAttribute("afterFormFlag", afterFormFlag);


		//sighn-up.jsp からの値を受け取る
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nameKana = request.getParameter("nameKana");
		String gender = request.getParameter("gender");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String note = request.getParameter("note");

		//バリデーションチェックのエラーテキスト(バリデーションエラー時に追加する)
		String erId = "";
		String erName = "";
		String erNameKana = "";
		String erGender = "";
		String erPassword = "";
		String erAddress = "";
		String erTel = "";
		String erEmail = "";
		String erNote = "";



		//--------------------------
		//バリデーションチェック
		//--------------------------
		boolean vali1 = CommonUtil.validIdStatus(id);
		boolean vali2 = CommonUtil.validNameStatus(name);
		boolean vali3 = CommonUtil.validNameKanaStatus(nameKana);
		boolean vali4 = CommonUtil.validGenderStatus(gender);
		boolean vali5 = CommonUtil.validPasswordStatus(password);
		boolean vali6 = CommonUtil.validAddressrStatus(address);
		boolean vali7 = CommonUtil.validTelStatus(tel);
		boolean vali8 = CommonUtil.validEmailStatus(email);
		boolean vali9 = CommonUtil.validNoteStatus(email);

		if(!vali1 || !vali2 || !vali3 || !vali4 || !vali5 || !vali6 || !vali7 || !vali8 || !vali9) {         //バリデーションチェックが1つでもアウトのとき

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
			if(!vali5) {     //passwordがアウトのとき
				erPassword = Const.erPassword;
			}
			if(!vali6) {     //addressがアウトのとき
				erAddress = Const.erAddress;
			}
			if(!vali7) {     //telがアウトのとき
				erTel = Const.erTel;
			}
			if(!vali8) {     //emailがアウトのとき
				erEmail = Const.erEmail;
			}
			if(!vali9) {     //emailがアウトのとき
				erNote = Const.erNote;
			}

			//エラーの情報と入力された値を返す
			request.setAttribute("erId", erId);
			request.setAttribute("erName", erName);
			request.setAttribute("erNameKana", erNameKana);
			request.setAttribute("erGender", erGender);
			request.setAttribute("erPassword", erPassword);
			request.setAttribute("erAddress", erAddress);
			request.setAttribute("erTel", erTel);
			request.setAttribute("erEmail", erEmail);
			request.setAttribute("erNote", erNote);
			request.setAttribute("sighnUpResult", sighnUpResult);
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("nameKana", nameKana);
			request.setAttribute("password", password);
			request.setAttribute("address", address);
			request.setAttribute("tel", tel);
			request.setAttribute("email", email);
			request.setAttribute("note", note);
			request.setAttribute("resulText", "[エラー] 入力値が不正です");

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
			return;
		}


		//エスケープ処理
		note = CommonUtil.replaceEscapeChar(note);

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
		bean.setPassword(password);
		bean.setAddress(address);
		bean.setTel(tel);
		bean.setEmail(email);
		bean.setNote(note);

		userInfoList.add(bean);

		UserInfoBl bl = new UserInfoBl();
		sighnUpResult = bl.userSighnUp(userInfoList);           //ユーザの新規登録結果を取得


		//バリデーションチェックとSQLの結果を共通で返す
		request.setAttribute("erId", erId);
		request.setAttribute("erName", erName);
		request.setAttribute("erNameKana", erNameKana);
		request.setAttribute("erGender", erGender);
		request.setAttribute("erPassword", erPassword);
		request.setAttribute("erAddress", erAddress);
		request.setAttribute("erTel", erTel);
		request.setAttribute("erEmail", erEmail);
		request.setAttribute("erNote", erNote);
		request.setAttribute("sighnUpResult", sighnUpResult);


		//--------------
		//SQLの判定
		//--------------
		if(!sighnUpResult) {      //SQLが実行失敗したとき

			//入力された値を返す
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("nameKana", nameKana);
			request.setAttribute("password", password);
			request.setAttribute("address", address);
			request.setAttribute("tel", tel);
			request.setAttribute("email", email);
			request.setAttribute("note", note);
			request.setAttribute("resulText", "[エラー] 新規登録に失敗しました");
			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
			return;
		}


		request.setAttribute("resulText", "新規登録に成功しました");
		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
	}

}
