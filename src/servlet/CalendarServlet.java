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


		//現在の日付を取得
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();


		String ym = request.getParameter("ym");

		//calendar.jspからym(年月)を指定されたとき
		if (ym != null) {
			year = Integer.parseInt(ym.substring(0, 4));
			month = Integer.parseInt(ym.substring(4, 6));

		}


		//------------------------------------
		// 第1週目の日曜日～初日までを設定
		//------------------------------------
		//LocalDateから1日目の情報を取得
		LocalDate localDate = LocalDate.of(year, month, 1);

		//最終日をLocalDateから取得
		int lastDay = localDate.lengthOfMonth();


		// 第1週目の初日の曜日を取得（月：1, 火：2.....日:7）
		int firstWeek = localDate.getDayOfWeek().getValue();


		//日付けを格納するArrayListを取得
		List<ScheduleBean> dayList = new ArrayList<>();

		//[カレンダー]日曜日～初日の曜日の直前までnullを代入し揃える
		if(firstWeek != 7) {
			for(int i = 1; i <= firstWeek; i++) {      //曜日の取得 7 1 2 3 4 5 6 なので
				dayList.add(null);                     //初日が日曜を除く取得した曜日の回数分代入する
			}
		}



		//--------------------------------
		//登録しているスケジュールを検索
		//--------------------------------

		//year,monthをString6桁に変える
		ym = CommonUtil.ymFormatSixByInt(year, month);

		//BLの戻り値をdbListで受け取る
		ScheduleBl bl = new ScheduleBl();
		List<ScheduleBean> dbList = bl.selectScheduleMonthDB(ym);



		//---------------------------
		// 日付とスケジュールを設定
		//---------------------------

		//dbListの要素を指定するための変数
		int youso = 0;

		//日付と登録されたスケジュールをdayListに格納
		for(int i = 1; i <= lastDay; i++) {

			ScheduleBean bean = new ScheduleBean();
			bean.setDay(String.valueOf(i));

			//iが1桁のとき2桁の文字列に変換する
			String day = String.format("%02d", i);


			//指定したカレンダーに登録されたスケジュール(dbList)が1つもないとき
			if (dbList.size() == 0) {
				dayList.add(bean);
				continue;
			}

			//DAOの戻り値のdayと実際の日付(day)が同じだったときdbListの値をsetする
			if((dbList.get(youso).getDay()).equals(day)) {

				bean.setMemo1(dbList.get(youso).getMemo1());
				bean.setMemo2(dbList.get(youso).getMemo2());
				bean.setMemo3(dbList.get(youso).getMemo3());

				youso++;
			}
			dayList.add(bean);
		}


		//------------------------------------
		// 最終週の終了日～土曜日までを設定
		//------------------------------------
		//[カレンダー]最終日の曜日～土曜日まで null を代入し揃える
		int weekAmari = 7 - (dayList.size() % 7);
		for (int i = 1; i <= weekAmari; i++) {          //曜日の取得 7 1 2 3 4 5 6 なので
			dayList.add(null);                          //日付から÷7のあまりの回数分代入する
		}



		//取得したカレンダーの前月のymをbeforeYmに代入
		LocalDate localDate2 = localDate.minusMonths(1);
		year = localDate2.getYear();
		month = localDate2.getMonthValue();

		//year,monthをString6桁に変える
		String beforeYm = CommonUtil.ymFormatSixByInt(year, month);

		//取得したカレンダーの翌月のymをafterYmに代入
		LocalDate localDate3 = localDate.plusMonths(1);
		year = localDate3.getYear();
		month = localDate3.getMonthValue();

		//year,monthをString6桁に変える
		String afterYm = CommonUtil.ymFormatSixByInt(year, month);


		// 引き渡す値を設定
		request.setAttribute("dayList", dayList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("beforeYm", beforeYm);
		request.setAttribute("afterYm", afterYm);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);

	}

}
