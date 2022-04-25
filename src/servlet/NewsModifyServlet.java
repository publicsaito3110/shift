package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.NewsBean;
import bl.NewsBl;
import common.CommonUtil;

/**
 * @author saito
 *
 */
@WebServlet("/NewsModifyServlet")
public class NewsModifyServlet extends BaseAdministratorServlet {
	private static final long serialVersionUID = 1L;


    public NewsModifyServlet() {
        super();
    }


    @Override
	protected void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//news-edit.jspから値を受け取る
		String date = request.getParameter("date");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		//入力値(msg)をエスケープ処理
		title = CommonUtil.replaceEscapeChar(title);
		content = CommonUtil.replaceEscapeChar(content);

		//dateをymd(YYYYMMDD)に変換
		String ymd = date.replaceAll("/", "");

		//-----------
		//SQLの実行
		//-----------

		//newsBeanに受け取った値を格納する
		NewsBean newsBean = new NewsBean();

		newsBean.setYmd(ymd);
		newsBean.setContent(title);
		newsBean.setContent(content);

		NewsBl bl = new NewsBl();

		boolean isResult = bl.updateNews(newsBean);


		//-----------
		//SQLの判定
		//-----------

		//SQLが失敗したとき
		if (!isResult) {

			//引き渡す値を設定
			request.setAttribute("afterFormFlag", true);
			request.setAttribute("popTitle", "修正結果");
			request.setAttribute("resultText", "[エラー] 修正に失敗しました");
			request.setAttribute("result", false);

			//画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/common/pop-window.jsp").forward(request, response);
			return;
		}

		//引き渡す値を設定
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("popTitle", "修正結果");
		request.setAttribute("resultText", "修正に成功しました");
		request.setAttribute("result", true);

		//画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/common/pop-window.jsp").forward(request, response);
	}
}
