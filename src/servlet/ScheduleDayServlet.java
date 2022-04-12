package servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ScheduleDayBean;
import common.CommonLogic;
import common.Const;
import common.ScheduleDayLogic;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/ScheduleDayServlet")
public class ScheduleDayServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void existSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//calendar.jspから受け取る
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		//month,dayが2桁でないとき"0"をつける
		CommonLogic commonLogic = new CommonLogic();
		String ymd = commonLogic.ymdFormatEightByString(year, month, day);

		//指定した日付から値(登録済みのidとユーザ名, スケジュールに登録しているuser,memo)を取得する
		ScheduleDayLogic scheduleDayLogic = new ScheduleDayLogic();

		List<ScheduleDayBean> scheduleDayList = scheduleDayLogic.toListScheduleDayOptionByYmd(ymd);

		//scheduleDayListからmemoのみを抽出する
		String memo1 = scheduleDayList.get(0).getMemo1();
		String memo2 = scheduleDayList.get(0).getMemo2();
		String memo3 = scheduleDayList.get(0).getMemo3();

		//登録済みのスケジュールからsqlTypeを取得
		String sqlType = scheduleDayLogic.checkSqlType(scheduleDayList);


		//----------------------
		// 引き渡す値を設定
		//----------------------
		request.setAttribute("scheduleDayList", scheduleDayList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("memo1", memo1);
		request.setAttribute("memo2", memo2);
		request.setAttribute("memo3", memo3);
		request.setAttribute("afterFormFlag", false);

		//sqlTypeがupdateのとき
		if (Const.SQLTYPE_UPDATE.equals(sqlType)) {
			request.setAttribute("sqlTypeUpdate", true);
			request.setAttribute("btnValue1", "UPDATE");
			request.setAttribute("btn1", "修正する");
			request.setAttribute("btnValue2", "DELETE");
			request.setAttribute("btn2", "削除する");
		}

		//sqlTypeがinsertのとき
		if (Const.SQLTYPE_INSERT.equals(sqlType)) {
			request.setAttribute("sqlTypeUpdate", false);
			request.setAttribute("btnValue1", "INSERT");
			request.setAttribute("btn1", "登録する");
		}

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);

	}
}
