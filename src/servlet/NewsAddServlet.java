package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.NewsBean;
import bl.NewsBl;
import common.CommonUtil;
import common.Const;

/**
 * Servlet implementation class NewsAddServlet
 */
@WebServlet("/NewsAddServlet")
public class NewsAddServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//現在の日付を取得
		LocalDate now = LocalDate.now();

		//現在の日付(YYYY-MM-DD)を取得する
		String nowDate = now.toString();

		//現在の日付から上限(3ヵ月後)の日付を取得
		LocalDate limitMonth= now.plusMonths(3);

		//上限の日付(YYYY-MM-DD)を取得する
		String maxDate = limitMonth.toString();


		//Postで受け取ったとき
		if (Const.DO_POST.equals(request.getMethod())) {

			//news-add.jspから値を受け取る
			String date = request.getParameter("date");
			String title = request.getParameter("title");
			String content = request.getParameter("content");


			//------------------------
			//バリデーションチェック
			//------------------------

			//いずれかの入力値が未入力のとき
			if (date.isEmpty() || title.isEmpty() || content.isEmpty()) {

				//引き渡す値を設定
				request.setAttribute("nowDate", nowDate);
				request.setAttribute("maxDate", maxDate);

				//画面遷移
				request.getRequestDispatcher("/WEB-INF/jsp/news-add.jsp").forward(request, response);
				return;
			}

			//入力された日付をLocalDateで取得
			LocalDate localDate = LocalDate.parse(date);

			//入力された日付が上限の日付より後または現在より前の日付だったとき
			if (localDate.isAfter(limitMonth) || localDate.isBefore(now)) {

				//引き渡す値を設定
				request.setAttribute("nowDate", nowDate);
				request.setAttribute("maxDate", maxDate);

				//画面遷移
				request.getRequestDispatcher("/WEB-INF/jsp/news-add.jsp").forward(request, response);
				return;
			}

			//dateをymd(YYYYMMDD)に変換
			String ymd = date.replaceAll("/", "");

			//入力値をエスケープ
			title = CommonUtil.replaceEscapeChar(title);
			content = CommonUtil.replaceEscapeChar(content);

			//-----------
			//SQLの実行
			//-----------

			//newsBeanに受け取った値を格納する
			NewsBean newsBean = new NewsBean();

			newsBean.setYmd(ymd);
			newsBean.setContent(title);
			newsBean.setContent(content);

			NewsBl bl = new NewsBl();

			boolean isResult = bl.insertNews(newsBean);


			//-----------
			//SQLの判定
			//-----------

			//SQLが失敗したとき
			if (!isResult) {

				//引き渡す値を設定
				request.setAttribute("nowDate", nowDate);
				request.setAttribute("maxDate", maxDate);

				//画面遷移
				request.getRequestDispatcher("/WEB-INF/jsp/news-add.jsp").forward(request, response);
				return;
			}

			//引き渡す値を設定
			request.setAttribute("nowDate", nowDate);
			request.setAttribute("maxDate", maxDate);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/news-add.jsp").forward(request, response);
		}

		//Getで受け取ったとき
		if (Const.DO_GET.equals(request.getMethod())) {

			//引き渡す値を設定
			request.setAttribute("nowDate", nowDate);
			request.setAttribute("maxDate", maxDate);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/news-add.jsp").forward(request, response);
		}
	}
}
