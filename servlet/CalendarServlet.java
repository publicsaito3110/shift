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

import bean.ScheduleBean;
import bl.ScheduleBl;
import common.CommonUtil;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		//---開発用 変数取得 (例:2022-11)----
		int settingYear = 2022 ;    //好きな変数(年)を入れてください
		int settingMonth = 11 ;     //好きな変数(月)を入れてください
		//-----------------------------------

		int year = settingYear;
		int month =settingMonth;
		//LocalDateから1日目の情報を取得
		LocalDate localDate = LocalDate.of(year, month, 1);

		//最終日をLocalDateから取得
		int lastDay = localDate.lengthOfMonth();


		// 曜日を取得（月：1, 火：2.....日:7）
		int firstWeek = localDate.getDayOfWeek().getValue();   //第1週


		//日付けを格納するArrayListを取得
		List<ScheduleBean> dayList = new ArrayList<>();

		//[カレンダー]日曜日～初日の曜日の直前に null を代入し揃える
		if(firstWeek != 7) {
			for(int i = 1; i <= firstWeek; i++) {      //曜日の取得 7 1 2 3 4 5 6 なので
				dayList.add(null);                      //初日が日曜を除く取得した曜日の回数分代入する
			}
		}

		//BLの戻り値をdbListで受け取る
		ScheduleBl bl = new ScheduleBl();
		List<ScheduleBean> dbList = bl.selectScheduleMonthDB();


		int yousositei = 0;        //dbListの要素を指定するための変数

		// カレンダーの日付を設定
		for(int i = 1; i <= lastDay; i++) {

			ScheduleBean bean = new ScheduleBean();
			String strDay = String.valueOf(i);
			bean.setDay(strDay);

			String day = CommonUtil.dayFormatTwo(strDay);

			//dbList(DAOの戻り値)のdayと実際の日付(checkDay)が同じだったときdbListの値をsetする
			if((dbList.get(yousositei).getDay()).equals(day)) {

				bean.setMemo1(dbList.get(yousositei).getMemo1());
				bean.setMemo2(dbList.get(yousositei).getMemo2());
				bean.setMemo3(dbList.get(yousositei).getMemo3());

				yousositei++;
			}

			dayList.add(bean);

		}

		//[カレンダー]最終日の曜日～土曜日まで null を代入し揃える
		int weekAmari = 7 - (dayList.size() % 7);
		for (int i = 1; i <= weekAmari; i++) {
			dayList.add(null);
		}


		// 引き渡す値を設定
		request.setAttribute("dayList", dayList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);

	}

}
