package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import bl.UserBl;
import common.CommonUtil;
import common.Const;

/**
 * Servlet implementation class UserListServlet
 */

@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserListServlet() {
        super();
        //  Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		//セッションのデータをuserBeanで取得
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");


		//sessionから管理者かどうかを取得
		boolean isAdministrator = sessionUserBean.isAdministrator();



		//user-list.jsp からの値を受け取る
		String page = request.getParameter("page");
		String keyWord = request.getParameter("keyWord");

		//keyWordがnullのとき空文字を代入
		keyWord = CommonUtil.changeEmptyByNull(keyWord);

		//指定したページに対応したoffsetを取得する
		int offset = this.toIntReturnOffsetByPage(page);


		//ログインしているユーザが管理者でないとき
		if(!isAdministrator) {

			//idをキーワードに代入する
			keyWord = sessionUserBean.getId();
		}



		//------------
		//SQLの実行
		//------------

		//keyWordをBLに引き渡し、AllayListを戻り値として受け取る
		List<UserBean> userList = new ArrayList<>();

		UserBl bl = new UserBl();
		userList = bl.selectUserByKeyWord(offset, keyWord);




		// 引き渡す値を設定
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("userList", userList);
		request.setAttribute("formAction", "");
		request.setAttribute("inputDisabled", Const.INPUT_DISABLED);
		request.setAttribute("displayLastPage", "");

		//ログイン済みのユーザが管理者のとき
		if(isAdministrator) {
			request.setAttribute("formAction", "UserOneServlet");
			request.setAttribute("inputDisabled", "");
			request.setAttribute("administrater", true);
		}

		//jspに表示するページを取得する
		int lastPage = this.toIntReturnLastPageByUserList(userList);
		List<String> pageList = this.toListDisplayPageNoByMaxPage(page, lastPage);

		request.setAttribute("pageList", pageList);

		//keyWordが空文字のとき
		if(keyWord.isEmpty()) {

			//全件表示され、doGetと同様の結果になるためafterFormFlagをfalseにする
			request.setAttribute("afterFormFlag", false);
		}



		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);
	}



	/**
	 *指定したページに対応したoffsetを返す
	 */
	public int toIntReturnOffsetByPage(String page) {

		//1回のSQLで表示する件数
		int pageLimitPer = Integer.parseInt(Const.PAGE_LIMIT);


		//ユーザ一覧に表示するためページに対応した(データn件目～)に初期値0を代入する
		int offset = Const.OFFSET_FIRST;


		//ページ数を指定されたとき
		if (page != null) {

			//ユーザ一覧するため指定したページに対応した(データn件目～)をoffsetに代入する
			int nowPage = Integer.parseInt(page);
			nowPage = (nowPage - 1) * pageLimitPer;

			offset = nowPage;
		}

		return offset;
	}


	/**
	 *SQLの該当件数から最大ページ数(maxPage)を返す
	 */
	public int toIntReturnLastPageByUserList(List<UserBean> userList) {

		//SQLの結果(COUNT)を取得
		String sqlCount = userList.get(0).getCountAll();

		//SQLの結果(COUNT) ÷ 1ページあたりの表示件数 = ページ数(切り上げ) とする
		BigDecimal sqlCountBd = new BigDecimal(sqlCount);
		BigDecimal pageLimitBd = new BigDecimal(Const.PAGE_LIMIT);

		BigDecimal calc = sqlCountBd.divide(pageLimitBd);
		calc = calc.setScale(0, RoundingMode.UP);

		//ページ数をint型に変換
		int maxPage = calc.intValue();

		return maxPage;
	}


	/**
	 *最終ページ数(lastPage)からjspに表示するページを返す
	 */
	public List<String> toListDisplayPageNoByMaxPage(String page, int lastPage) {

		//Listにjspに表示するページを格納する
		List<String> pageList = new ArrayList<>();


//		//1回で表示する最大ページ数
//		int maxPage = Const.MAX_PAGE;



		//-------------------------------------------------
		//ページ数が1しかないときpageListをなにもせず返す
		//-------------------------------------------------
		if(lastPage == 1) {

			return pageList;
		}

//TODO
//		//jspに表示するページの処理(動的)p3以上->2,3,4,5,6... あとで
//
//		//---------------------------------------------------
//		//pageがnullでないかつ1ページ目以外指定があるのとき
//		//---------------------------------------------------
//
//		if(page != null) {
//
//			int nowPage = Integer.parseInt(page);
//
//			//maxPageの回数分だけjspに表示するページを代入する
//			for(int i = nowPage - 1; i <= lastPage; i++) {
//
//				String strPage = String.valueOf(i);
//
//				//maxPageの回数以上ループしたとき(1回で表示する最大ページ5)
//				if(maxPage <= i) {
//
//					pageList.add(strPage);
//					break;
//				}
//
//				pageList.add(strPage);
//			}
//
//			return pageList;
//		}


		//----------------------------------
		//pageがnullまたは1ページ目を指定
		//----------------------------------

		//lastPageの回数分だけjspに表示するページを代入する
		for(int i = 1; i <= lastPage; i++) {

			String strPage = String.valueOf(i);
			pageList.add(strPage);
		}

		return pageList;
	}

}
