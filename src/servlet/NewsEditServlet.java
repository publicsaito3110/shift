package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.NewsBean;
import bl.NewsBl;
import common.CommonLogic;
import common.Const;

/**
 * @author saito
 *
 */
@WebServlet("/NewsEditServlet")
public class NewsEditServlet extends BaseAdministratorServlet {
	private static final long serialVersionUID = 1L;


    public NewsEditServlet() {
        super();
    }


    @Override
	protected void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//現在の日付を取得
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		int nowDay = now.getDayOfMonth();

    	//現在の日付(YYYY-MM-DD)を取得する
    	String nowDate = now.toString();

    	//現在の日付から上限(3ヵ月後)の日付を取得
    	LocalDate limitMonth= now.plusMonths(Const.NEWS_ADD_MAX_MONTH);

    	//上限の日付(YYYY-MM-DD)を取得する
    	String maxDate = limitMonth.toString();

		//ymd(String8桁)へ変換
		CommonLogic logic = new CommonLogic();
		String nowYmd = logic.toStringYmdFormatEightByIntYMD(nowYear, nowMonth, nowDay);


		//------------
		//SQLの実行
		//------------

		//現在日までに登録されている全てのお知らせをrecordNewsListで受け取る
		NewsBl bl = new NewsBl();
		List<NewsBean> recordNewsList = new ArrayList<>();
		recordNewsList = bl.selectAllNewsAfterNowByNowYmd(nowYmd);

		//現在日までに表示されているお知らせをdisplayNewsListで受け取る
		List<NewsBean> displayNewsList = new ArrayList<>();
		displayNewsList = bl.selectNewsBeforeNowByNowYmd(nowYmd);


		//引き渡す値を設定
		request.setAttribute("recordNewsList", recordNewsList);
		request.setAttribute("displayNewsList", displayNewsList);
		request.setAttribute("newsCtgArray", Const.NEWS_CTG_ARRAY);
		request.setAttribute("nowDate", nowDate);
		request.setAttribute("maxDate", maxDate);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/news-edit.jsp").forward(request, response);
	}
}