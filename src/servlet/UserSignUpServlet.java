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
		String note = request.getParameter("note");

		//ArrayListに値を詰める
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

		//--------------------------
		//バリデーションチェック
		//--------------------------

		//userInfoListを引き渡し、結果とエラーテキストを受け取る
		List <ValidationBean> valiList = new ArrayList<>();
		valiList = ValidationUtil.validInputAllStatus(userInfoList);

		//バリデーションチェックの結果を抽出
		boolean isVali1 = valiList.get(0).isValiId();
		boolean isVali2 = valiList.get(0).isValiName();
		boolean isVali3 = valiList.get(0).isValiNameKana();
		boolean isVali4 = valiList.get(0).isValiGender();
		boolean isVali5 = valiList.get(0).isValiPassword();
		boolean isVali6 = valiList.get(0).isValiAddress();
		boolean isVali7 = valiList.get(0).isValiTel();
		boolean isVali8 = valiList.get(0).isValiEmail();
		boolean isVali9 = valiList.get(0).isValiNote();

		//バリデーションチェックのエラーテキストを抽出
		String erId = valiList.get(0).getErId();
		String erName = valiList.get(0).getErName();
		String erNameKana = valiList.get(0).getErNameKana();
		String erGender = valiList.get(0).getErGender();
		String erPassword = valiList.get(0).getErPassword();
		String erAddress = valiList.get(0).getErAddress();
		String erTel = valiList.get(0).getErTel();
		String erEmail = valiList.get(0).getErEmail();
		String erNote = valiList.get(0).getErNote();

		//エラーテキストを返す
		request.setAttribute("erId", erId);
		request.setAttribute("erName", erName);
		request.setAttribute("erNameKana", erNameKana);
		request.setAttribute("erGender", erGender);
		request.setAttribute("erPassword", erPassword);
		request.setAttribute("erAddress", erAddress);
		request.setAttribute("erTel", erTel);
		request.setAttribute("erEmail", erEmail);
		request.setAttribute("erNote", erNote);

		//バリデーションチェックが1つでもアウトのとき
		if(!isVali1 || !isVali2 || !isVali3 || !isVali4 || !isVali5 || !isVali6 || !isVali7 || !isVali8 || !isVali9) {

			//エラーの情報と入力された値を返す
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("nameKana", nameKana);
			request.setAttribute("password", password);
			request.setAttribute("address", address);
			request.setAttribute("tel", tel);
			request.setAttribute("email", email);
			request.setAttribute("note", note);
			request.setAttribute("result", false);
			request.setAttribute("resultText", "[エラー] 入力値が不正です");

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
		UserInfoBl bl = new UserInfoBl();
		boolean sighnUpResult = bl.sighnUpUserDB(userInfoList);


		//SQLが実行失敗したとき
		if(!sighnUpResult) {

			//入力された値を返す
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("nameKana", nameKana);
			request.setAttribute("password", password);
			request.setAttribute("address", address);
			request.setAttribute("tel", tel);
			request.setAttribute("email", email);
			request.setAttribute("note", note);
			request.setAttribute("result", false);
			request.setAttribute("resultText", "[エラー] 新規登録に失敗しました");

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
			return;
		}



		request.setAttribute("result", true);
		request.setAttribute("resultText", "新規登録に成功しました");

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(request, response);
	}

}
