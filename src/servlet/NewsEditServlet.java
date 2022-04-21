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

import bean.NewsBean;
import bl.NewsBl;

/**
 * Servlet implementation class NewsEditServlet
 */
@WebServlet("/NewsEditServlet")
public class NewsEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//現在の日付を取得
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		int nowDay = now.getDayOfMonth();

		//ymd(String8桁)へ変換
		String nowYmd = "20220421";


		//------------
		//SQLの実行
		//------------

		//現在日までに登録されているお知らせをrecordNewsListで受け取る
		NewsBl bl = new NewsBl();
		List<NewsBean> recordNewsList = new ArrayList<>();

		recordNewsList = bl.selectAllNews(nowYmd);

		//現在日までに表示されているお知らせをdisplayNewsListで受け取る
		List<NewsBean> displayNewsList = new ArrayList<>();

		displayNewsList = bl.selectNews(nowYmd);

		//引き渡す値を設定
		request.setAttribute("recordNewsList", recordNewsList);
		request.setAttribute("displayNewsList", displayNewsList);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/news-edit.jsp").forward(request, response);
	}
}
