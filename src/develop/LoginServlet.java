package develop;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("err", "");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html;charset=UTF-8");     //文字コードをUTF-8で設定
		request.setCharacterEncoding("UTF-8");



		// DBにidとpassが無し
		boolean errorDbFlag = false;

		//リクエストパラメータを取得
		String receiveUI = request.getParameter("userId");
		String receivePW = request.getParameter("userPassword");

		//------------------------------
		// 未入力チェック
		//------------------------------
		if(receiveUI.isEmpty() || receivePW.isEmpty()) {
			request.setAttribute("err", "未入力です");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}

		//------------------------------
		// db存在チェック
		//------------------------------
		// DB検索 TODO
		if(errorDbFlag) {
			request.setAttribute("err", "IDまたはパスワードが違います。");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}


		//------------------------------
		// 値を設定
		//------------------------------

		request.setAttribute("err", "");
		request.getRequestDispatcher("/WEB-INF/jsp/menu.jsp").forward(request, response);

//		//セッション上にユーザー情報があるか判定
//		HttpSession session = request.getSession() ; //オブジェクト + メソッドの起動
//
//		LoginDto dto = (LoginDto)session.getAttribute("USERINFO") ;  //セッションのデータを取得
//
//		//セッションの判別
//		if(session != null) {       //セッションが存在し、ログインしているとき
//			request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
//
//		}else {                     //セッションが存在せず、ログインしていない
//
//			//ユーザーIDとpassを取得
//			String userId = request.getParameter("receiveUI") ;
//			String userPassword = request.getParameter("receivePW") ;
//			//BLを呼び出す
//			LoginBL bl = new LoginBL() ;
//
//			//初回ログインの成功の判定
//			if(dto.getUserId() != null) {      //成功
//				session.setAttribute("USERINFO", dto) ;  //初回ログイン時sessionに"USERINFO"を作成し、dto("USER_ID","USER_PASSWORD")を格納
//				request.getRequestDispatcher("CalendarServlet").forward(request, response);
//
//			}else {                            //失敗
//				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);



	}
}
