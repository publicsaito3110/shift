package servlet;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ScheduleBean;
import bl.ScheduleBl;
import common.CommonUtil;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends BaseLoginServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void executeExistSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//現在の日付を取得
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();


		//値を受け取る
		String ym = request.getParameter("ym");

		//calendar.jspからym(年月)を指定されたとき
		if (ym != null) {

			//ymからyear(4文字)month(2文字)を取得
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
			for(int i = 1; i <= firstWeek; i ++) {      //曜日の取得 7 1 2 3 4 5 6 なので
				dayList.add(null);                     //初日が日曜を除く取得した曜日の回数分代入する
			}
		}



		//--------------------------------
		//登録しているスケジュールを検索
		//--------------------------------

		//year,monthをString6桁に変える
		ym = this.toStringYmFormatSixByIntYm(year, month);

		//BLの戻り値をdbListで受け取る
		ScheduleBl bl = new ScheduleBl();
		List<ScheduleBean> dbList = bl.selectScheduleMonth(ym);



		//---------------------------
		// 日付とスケジュールを設定
		//---------------------------

		//dbListの要素を指定するための変数
		int youso = 0;

		//dbListの要素があるかどうかの判定
		boolean isSizeZeroDL = CommonUtil.isCheckSizeZeroByList(dbList);
		int dbListSize = dbList.size();

		//日付と登録されたスケジュールをdayListに格納
		for(int i = 1; i <= lastDay; i++) {

			ScheduleBean bean = new ScheduleBean();
			bean.setDay(String.valueOf(i));

			//iが1桁のとき2桁の文字列に変換する
			String day = String.format("%02d", i);


			//指定したカレンダーに登録されたスケジュール(dbList)が1つもないとき
			if (isSizeZeroDL) {
				dayList.add(bean);
				continue;
			}

			//yousoがdbListの要素数を超えたとき
			if (dbListSize <= youso) {
				dayList.add(bean);
				continue;
			}

			//DAOの戻り値のdayと実際の日付(day)が同じだったときdbListの値をsetする
			if((dbList.get(youso).getDay()).equals(day)) {

				bean.setUser1(dbList.get(youso).getUser1());
				bean.setUser2(dbList.get(youso).getUser2());
				bean.setUser3(dbList.get(youso).getUser3());

				bean.setMemo1(dbList.get(youso).getMemo1());
				bean.setMemo2(dbList.get(youso).getMemo2());
				bean.setMemo3(dbList.get(youso).getMemo3());

				youso++;

				dayList.add(bean);
				continue;
			}

			dayList.add(bean);
		}


		//------------------------------------
		// 最終週の終了日～土曜日までを設定
		//------------------------------------

		//[カレンダー]最終日の曜日～土曜日まで null を代入し揃える
		int weekAmari = 7 - (dayList.size() % 7);

		// weekAmariが7(最終日が土曜日)以外のとき
		if(weekAmari != 7) {

			for (int i = 1; i <= weekAmari; i ++) {          //曜日の取得 7 1 2 3 4 5 6 なので
				dayList.add(null);                          //dayListの要素数÷7のあまりの回数分代入する
			}
		}



		//----------------------
		//前月, 翌月のymを設定
		//----------------------

		//取得したカレンダーの前月のymをbeforeYmに代入
		LocalDate localDateBefore = localDate.minusMonths(1);
		int beforeYear = localDateBefore.getYear();
		int beforeMonth = localDateBefore.getMonthValue();

		//year,monthをString6桁に変える
		String beforeYm = this.toStringYmFormatSixByIntYm(beforeYear, beforeMonth);


		//取得したカレンダーの翌月のymをafterYmに代入
		LocalDate localDateAfter = localDate.plusMonths(1);
		int afterYear = localDateAfter.getYear();
		int afterMonth = localDateAfter.getMonthValue();

		//year,monthをString6桁に変える
		String afterYm = this.toStringYmFormatSixByIntYm(afterYear, afterMonth);



		// 引き渡す値を設定
		request.setAttribute("dayList", dayList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("beforeYm", beforeYm);
		request.setAttribute("afterYm", afterYm);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);

	}


	/**
	 * year, month(int) をym(String6文字)で返すメソッド
	 */
	public String toStringYmFormatSixByIntYm(int year, int month) {

		//monthが1桁のとき2桁の文字列に変換する
		String ym = year + String.format("%02d", month);

		return ym;
	}
}
