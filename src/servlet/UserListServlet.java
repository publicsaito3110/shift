package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
public class UserListServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;

    public UserListServlet() {
        super();
        //  Auto-generated constructor stub
    }


	@Override
	protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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

		//ログインしているユーザが管理者でないとき
		if (!isAdministrator) {

			//idをキーワードに代入する
			keyWord = sessionUserBean.getId();
		}


		//------------
		//SQLの実行
		//------------

		//keyWordをBLに引き渡し、AllayListを戻り値として受け取る
		UserBl bl = new UserBl();

		List<UserBean> userList = new ArrayList<>();
		userList = bl.selectUserByKeyWord(page, keyWord);


		// 引き渡す値を設定
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("userList", userList);
		request.setAttribute("inputDisabled", Const.INPUT_DISABLED);

		//ログイン済みのユーザが管理者のとき
		if (isAdministrator) {
			request.setAttribute("inputDisabled", "");
			request.setAttribute("isAdministrater", true);
		}

		//jspに表示するページを取得する
		int lastPage = this.toIntReturnLastPageByUserList(userList);
		List<String> pageList = this.toListDisplayPageNoByMaxPage(page, lastPage);

		request.setAttribute("pageList", pageList);

		//keyWordが空文字のとき
		if (keyWord.isEmpty()) {

			//全件表示され、doGetと同様の結果になるためafterFormFlagをfalseにする
			request.setAttribute("afterFormFlag", false);
		}

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/user-list.jsp").forward(request, response);
	}


	/**
	 *SQLの該当件数から最大ページ数(lastPage)を返す
	 *@param List<UserBean> userList, getting UserList
	 *@return int, calc last page
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
		int lastPage = calc.intValue();

		return lastPage;
	}


	/**
	 *最終ページ数(lastPage)からjspに表示するページを返す
	 *@param String page, int lastPage, Got page and calced last page
	 *@return List<String>, Return calced page List for display
	 */
	public List<String> toListDisplayPageNoByMaxPage(String page, int lastPage) {

		//Listにjspに表示するページを格納する
		List<String> pageList = new ArrayList<>();

		//1回で表示する最大ページ数
		int maxPage = Const.MAX_PAGE;


		//------------------------------------------------------
		//全体のページ数が1しかないときpageListをなにもせず返す
		//------------------------------------------------------
		if (lastPage == 1) {

			return pageList;
		}


		//------------------------------------------------------
		//jspに表示するページの処理(動的)p3以上->2,3,4,5,6...
		//------------------------------------------------------

		//pageがnullでないかつ1ページ目以外指定があるのとき
		if (page != null && !"1".equals(page)) {

			//現在のページをintに変換する
			int nowPage = Integer.parseInt(page);

			//現在のループ回数をカウントする
			int loopCount = 0;

			//nowPage - 1を最初のページとしてlastPageの回数分だけjspに表示するページを代入する
			for (int i = nowPage - 1; i <= lastPage; i++) {

				String strPage = String.valueOf(i);

				//maxPageの回数以上ループしたとき(1回で表示する最大ページ5)
				if(maxPage <= loopCount) {

					pageList.add(strPage);
					break;
				}

				pageList.add(strPage);
				loopCount++;
			}

			return pageList;
		}


		//----------------------------------
		//pageがnullまたは1ページ目を指定
		//----------------------------------

		//lastPageの回数分だけjspに表示するページを代入する
		for (int i = 1; i <= lastPage; i++) {

			String strPage = String.valueOf(i);
			pageList.add(strPage);
		}

		return pageList;
	}

}
