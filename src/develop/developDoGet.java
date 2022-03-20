package develop;

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

/**
 * Servlet implementation class developDoGet
 */
@WebServlet("/developDoGet")
public class developDoGet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public developDoGet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//---開発用 変数取得 (例:2022-11)----
		int settingYear = 2022 ;   //好きな変数(年)を入れてください
		int settingMonth = 11 ;     //好きな変数(月)を入れてください
		//---------------------------------

		int year = settingYear;
		int month =settingMonth;
		//LocalDateから1日目の情報を取得
		LocalDate localDate = LocalDate.of(year, month, 1);

		//最終日をLocalDateから取得
		int lastDay = localDate.lengthOfMonth();
		//LocalDateから最終目の情報を取得
		//		LocalDate localDateLastDay = LocalDate.of(year, month, lastDay);

		// 曜日を取得（月：1, 火：2.....日:7）
		int firstWeek = localDate.getDayOfWeek().getValue();            //第1週
		//		int finalWeek = localDateLastDay.getDayOfWeek().getValue();     //最終週

		//日付けを格納するArrayListを取得
		List<ScheduleBean> dayList = new ArrayList<>();

		//[カレンダー]日曜日～初日の曜日の直前に null を代入し揃える
		if(firstWeek != 7) {
			for( int i = 1; i <= firstWeek; i++) {      //曜日の取得 7 1 2 3 4 5 6 なので
				dayList.add(null);                      //初日が日曜を除く取得した曜日の回数分代入する
			}
		}

		//DAOの戻り値dbListで受け取る
		List<ScheduleBean> dbList = new ArrayList<>();
		ScheduleSeachDao dao = new ScheduleSeachDao();
		dbList = dao.selectScheduleMonthDB();

		String check = "";       //実際の日付とdbListの日付を揃えてを比較するため
		int yousositei = 0;     //dbListの要素を指定するため


		// 日付を設定
		for(int i = 1; i <= lastDay; i++) {

			ScheduleBean bean = new ScheduleBean();
			bean.setDay(String.valueOf(i));

			//dbListのday(戻り値)はStringかつ必ず2桁なので(check)が2桁になるようにする 例) 1 => "01"
			if(String.valueOf(i).length() != 2) {
				check = ( "0" + String.valueOf(i) );
			}else {
				check = ( String.valueOf(i) );
			}

			//dbList(DAOの戻り値)のday と 日付 が同じだったときdbListの値をsetする
			if( (dbList.get(yousositei).getDay()).equals(check) ) {

				//dbListの要素を指定 + dbListのmemoをbeanへ引き渡す
				//(dbListのmemoがnullのときは "" をsetして[午前,午後,夜間]画面のみを表示させる)
				bean.setMemo1( dbList.get(yousositei).getMemo1() );
				if(dbList.get(yousositei).getMemo1() == null) {
					bean.setMemo1("");
				}
				bean.setMemo2( dbList.get(yousositei).getMemo2() );
				if(dbList.get(yousositei).getMemo2() == null) {
					bean.setMemo2("");
				}
				bean.setMemo3( dbList.get(yousositei).getMemo3() );
				if(dbList.get(yousositei).getMemo3() == null) {
					bean.setMemo3("");
				}
				yousositei++;
			}
			dayList.add(bean);

		}

		//[カレンダー]最終日の曜日～土曜日まで null を代入し揃える
		int amari = 7 - (dayList.size() % 7);
		for (int i = 1; i <= amari; i++) {
			dayList.add(null);
		}


		// 引き渡す値を設定
		request.setAttribute("dayList", dayList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/putInCalemder.jsp").forward(request, response);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
