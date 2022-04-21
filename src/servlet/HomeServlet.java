package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.NewsBean;
import bean.UserBean;
import bl.NewsBl;
import common.CommonLogic;
import common.Const;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    @Override
	protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//現在の日付を取得
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		int nowDay = now.getDayOfMonth();

		//ymd(String8桁)へ変換
		CommonLogic logic = new CommonLogic();

		String nowYmd = logic.toStringYmdFormatEightByIntYMD(nowYear, nowMonth, nowDay);


		//------------
		//SQLの実行
		//------------

		//現在日までのお知らせをdbListで受け取る
		NewsBl bl = new NewsBl();
		List<NewsBean> dbList = new ArrayList<>();
		dbList = bl.selectNews(nowYmd);


		//-----------------------------
		//jspに表示するお知らせの処理
		//-----------------------------

		//dbListからお知らせを格納するための変数
		List<NewsBean> newsList = new ArrayList<>();
		NewsBean bean = new NewsBean();

		//表示できるお知らせがない(dbListが空文字)とき
		if (dbList.isEmpty()) {

			bean.setYmd("お知らせはありません");
			bean.setContent("");
			bean.setLabelNew("");
			newsList.add(bean);

			//引き渡す値を設定
			request.setAttribute("newsList", newsList);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
			return;
		}


		//現在の日付からお知らせに表示する下限の日付を取得
		LocalDate limitDate = now.minusDays(Const.NEWS_LIMIT_DAY_BEFORE_NOW);

		//dbListの要素数の回数だけdbListから結果を抽出し、newsListにセットする
		for (int i = 0; i < dbList.size(); i++) {

			//beanを初期化し、dbListからymdとcontentを取得
			bean = new NewsBean();
			String ymd = dbList.get(i).getYmd();
			String content = dbList.get(i).getContent();

			//ymdをLocalDate用(yyyy-mm-dd)に変換し、LocalDateで日付を取得
			String dateYmd = ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6, 8);
			LocalDate dbDate = LocalDate.parse(dateYmd);

			//ymdをjsp表示用(yyyy/mm/dd)に変換する
			String displayYmd = logic.changeDisplayYmdByYMD(ymd);

			//登録されている日付(dbDate)がのお知らせに表示する下限の日付(limitDate)より後のとき
			if (dbDate.isAfter(limitDate)) {

				bean.setYmd(displayYmd);
				bean.setContent(content);
				bean.setLabelNew("NEW");
				newsList.add(bean);
				continue;
			}

			bean.setYmd(displayYmd);
			bean.setContent(content);
			bean.setLabelNew("");
			newsList.add(bean);
		}


		//引き渡す値を設定
		request.setAttribute("newsList", newsList);

		//sessionから名前を取得する
		HttpSession session = request.getSession();
		UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");
		String name = sessionUserBean.getName();

		request.setAttribute("userNameText", name + "さん");

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
		return;
	}
}
