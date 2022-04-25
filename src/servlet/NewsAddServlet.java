package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.NewsBean;
import bl.NewsBl;
import common.CommonUtil;
import common.Const;

/**
 * @author saito
 *
 */
@WebServlet("/NewsAddServlet")
public class NewsAddServlet extends BaseAdministratorServlet {
	private static final long serialVersionUID = 1L;


    public NewsAddServlet() {
        super();
    }


    @Override
	protected void isAdministorator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    	//news-add.jspから値を受け取る
    	String date = request.getParameter("date");
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");

    	//現在の日付を取得
    	LocalDate now = LocalDate.now();

    	//現在の日付から上限(3ヵ月後)の日付を取得
    	LocalDate limitMonth= now.plusMonths(Const.NEWS_ADD_MAX_MONTH);


    	//------------------------
    	//バリデーションチェック
    	//------------------------

    	//いずれかの入力値が未入力のとき
    	if (date.isEmpty() || title.isEmpty() || content.isEmpty()) {

    		//引き渡す値を設定
    		request.setAttribute("afterFormFlag", true);
    		request.setAttribute("popTitle", "新規登録結果");
    		request.setAttribute("resultText", "[エラー] 未入力の値があります");
    		request.setAttribute("result", false);

    		//画面遷移
    		request.getRequestDispatcher("/WEB-INF/jsp/common/pop-window.jsp").forward(request, response);
    		return;
    	}

    	//入力された日付をLocalDateで取得
    	LocalDate localDate = LocalDate.parse(date);

    	//入力された日付が上限の日付より後または現在より前の日付だったとき
    	if (localDate.isAfter(limitMonth) || localDate.isBefore(now)) {

    		//引き渡す値を設定
    		request.setAttribute("afterFormFlag", true);
    		request.setAttribute("popTitle", "新規登録結果");
    		request.setAttribute("resultText", "[エラー] 不正な値を検知しました");
    		request.setAttribute("result", false);

    		//画面遷移
    		request.getRequestDispatcher("/WEB-INF/jsp/common/pop-window.jsp").forward(request, response);
    		return;
    	}

    	//dateをymd(YYYYMMDD)に変換
    	String ymd = date.replaceAll("-", "");

    	//入力値をエスケープ
    	title = CommonUtil.replaceEscapeChar(title);
    	content = CommonUtil.replaceEscapeChar(content);

    	//-----------
    	//SQLの実行
    	//-----------

    	//newsBeanに受け取った値を格納する
    	NewsBean newsBean = new NewsBean();

    	newsBean.setYmd(ymd);
    	newsBean.setTitle(title);
    	newsBean.setContent(content);

    	NewsBl bl = new NewsBl();

    	boolean isResult = bl.insertNewNews(newsBean);


    	//-----------
    	//SQLの判定
    	//-----------

    	//SQLが失敗したとき
    	if (!isResult) {

    		//引き渡す値を設定
    		request.setAttribute("afterFormFlag", true);
    		request.setAttribute("popTitle", "新規登録結果");
    		request.setAttribute("resultText", "[エラー] 新規登録に失敗しました");
    		request.setAttribute("result", false);

    		//画面遷移
    		request.getRequestDispatcher("/WEB-INF/jsp/common/pop-window.jsp").forward(request, response);
    		return;
    	}

    	//引き渡す値を設定
    	request.setAttribute("afterFormFlag", true);
    	request.setAttribute("popTitle", "新規登録結果");
    	request.setAttribute("resultText", "[エラー] 新規登録に失敗しました");
    	request.setAttribute("result", true);

    	//画面遷移
    	request.getRequestDispatcher("/WEB-INF/jsp/common/pop-window.jsp").forward(request, response);
    }
}
