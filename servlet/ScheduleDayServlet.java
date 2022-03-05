package servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ScheduleBean;
import bl.ScheduleBl;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/ScheduleDayServlet")
public class ScheduleDayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//初期値をnullとしてBLから受け取る
		String memo1 = null;
		String memo2 = null;
		String memo3 = null;


		//スケジュールの修正 calendar.jspから受け取る
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		String ymd = "";      //DAOに渡すためのymd

		//dayが1桁のとき0をつける 例) "1" => "01"
		if (day.length() == 1) {
			ymd = year + month + "0" + day;
		} else {
			ymd = year + month + day;
		}

		//BLの戻り値をdbListで受け取る
		ScheduleBl bl = new ScheduleBl();
		List<ScheduleBean> dbList = new ArrayList<>();
		dbList = bl.searchScheduleDay(ymd);              //戻り値はmemo1, memo2, memo3

		//dbListの値がnullでなければ抽出
		try{
			memo1 = dbList.get(0).getMemo1();
			memo2 = dbList.get(0).getMemo2();
			memo3 = dbList.get(0).getMemo3();
		} catch(Exception e) {
			e.printStackTrace();
		}

		// 引き渡す値を設定
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("memo1", memo1);
		request.setAttribute("memo2", memo2);
		request.setAttribute("memo3", memo3);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/scheduleDay.jsp").forward(request, response);

	}

}
