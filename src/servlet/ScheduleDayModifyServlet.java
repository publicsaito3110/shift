package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import bean.ScheduleBean;
import bean.ScheduleDayBean;
import bl.ScheduleBl;
import common.CommonUtil;
import common.ScheduleDayUtil;

/**
 * Servlet implementation class ScheduleDayModifyServlet
 */
@WebServlet("/ScheduleDayModifyServlet")
public class ScheduleDayModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScheduleDayModifyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");



		//-------------------------------------
		//scheduleDay.jsp からの値を受け取る
		//-------------------------------------
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		String inputUser1 = request.getParameter("user1");
		String inputMemo1 = request.getParameter("memo1");
		String inputUser2 = request.getParameter("user2");
		String inputMemo2 = request.getParameter("memo2");
		String inputUser3 = request.getParameter("user3");
		String inputMemo3 = request.getParameter("memo3");

		String btnSqlType = request.getParameter("sqlType");



		//month,dayが2桁でないとき"0"をつける
		String ymd = CommonUtil.ymdFormatEightByString(year, month, day);


		//指定した日付から値(登録済みのidとユーザ名, スケジュールに登録しているuser,memo)を取得する
		List<ScheduleDayBean> scheduleDayList = ScheduleDayUtil.toListScheduleDayByYmd(ymd);

		//scheduleDayListからmemoのみを抽出
		String memo1 = scheduleDayList.get(0).getMemo1();
		String memo2 = scheduleDayList.get(0).getMemo2();
		String memo3 = scheduleDayList.get(0).getMemo3();


		//登録済みのスケジュールからsqlTypeを取得
		String sqlType = ScheduleDayUtil.checkSqlType(scheduleDayList);



		//------------------------
		//共通で返す値を設定
		//------------------------
		request.setAttribute("scheduleDayList", scheduleDayList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("memo1", memo1);
		request.setAttribute("memo2", memo2);
		request.setAttribute("memo3", memo3);
		request.setAttribute("afterFormFlag", true);
		request.setAttribute("popTitle", "シフトの修正結果");

		//sqlTypeがupdateのとき
		if(sqlType.equals("update")) {
			request.setAttribute("sqlTypeUpdate", true);
			request.setAttribute("btnValue1", "UPDATE");
			request.setAttribute("btn1", "修正する");
			request.setAttribute("btnValue2", "DELETE");
			request.setAttribute("btn2", "削除する");
		}

		//sqlTypeがinsertのとき
		if(sqlType.equals("insert")) {
			request.setAttribute("sqlTypeUpdate", false);
			request.setAttribute("btnValue1", "INSERT");
			request.setAttribute("btn1", "登録する");
		}



		//userとmemoが""(未設定または未入力)のときnullを代入する
		inputUser1 = CommonUtil.changeNullByEmpty(inputUser1);
		inputMemo1 = CommonUtil.changeNullByEmpty(inputMemo1);
		inputUser2 = CommonUtil.changeNullByEmpty(inputUser2);
		inputMemo2 = CommonUtil.changeNullByEmpty(inputMemo2);
		inputUser3 = CommonUtil.changeNullByEmpty(inputUser3);
		inputMemo3 = CommonUtil.changeNullByEmpty(inputMemo3);

		//--------------------------
		//バリデーションチェック
		//--------------------------

		//sqlTypeの値をチェックする
		boolean isVali1 = this.isBtnSqlTypeIUD(btnSqlType);

		//sqlTypeの値がINSERT,UPDATE,DELETE以外のとき
		if(!isVali1) {

			// schedule-day.jspに返す値
			request.setAttribute("resultText", "[エラー] 不正な入力値を検知しました");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}


		//afterMemoの値とsqlTypeの組み合わせをチェックする
		boolean isVali2 = this.isNotEmptyAllOrDelete(inputUser1, inputUser2, inputUser3, inputMemo1, inputMemo2, inputMemo3, btnSqlType);

		//afterMemoが全て""かつ削除ボタン以外が押されたとき
		if(!isVali2) {

			// schedule-day.jspに返す値
			request.setAttribute("resultText", "[エラー] 修正後のシフトを入力してください");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}



		//memoをエスケープ
		inputMemo1 = CommonUtil.replaceEscapeChar(inputMemo1);
		inputMemo2 = CommonUtil.replaceEscapeChar(inputMemo2);
		inputMemo3 = CommonUtil.replaceEscapeChar(inputMemo3);



		//受け取った値をArrayListに格納
		List<ScheduleBean> scheduleList = new ArrayList<>();
		ScheduleBean bean = new ScheduleBean();


		bean.setYmd(ymd);
		bean.setUser1(inputUser1);
		bean.setMemo1(inputMemo1);
		bean.setUser2(inputUser2);
		bean.setMemo2(inputMemo2);
		bean.setUser3(inputUser3);
		bean.setMemo3(inputMemo3);

		scheduleList.add(bean);


		//-------------------------ー------------
		//UPDATE,DELETE,INSERTを判別し、SQL実行
		//---------------------------------------

		//結果をbooleanで受け取る
		boolean isRecordResult = false;

		ScheduleBl bl = new ScheduleBl();

		switch(btnSqlType){

		case "UPDATE":    //修正ボタンが押されたとき

			isRecordResult = bl.updateScheduleDay(scheduleList);
			break;

		case "DELETE":    //削除ボタンが押されたとき

			isRecordResult = bl.deleteScheduleDay(scheduleList);
			break;

		case "INSERT":    //登録ボタンが押されたとき

			isRecordResult = bl.insertScheduleDay(scheduleList);
			break;
		}


		//-----------------
		//DBの更新が失敗
		//-----------------
		if(!isRecordResult) {

			// schedule-day.jspに返す値
			request.setAttribute("resultText", "[エラー] 修正に失敗しました");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}

		//-----------------
		//DBの更新が成功
		//-----------------

		//先ほどの日付から値(登録済みのidとユーザ名, 更新したuser,memo)を改めて取得する
		scheduleDayList = ScheduleDayUtil.toListScheduleDayByYmd(ymd);

		//scheduleDayListからmemo(更新済み)のみを抽出
		memo1 = scheduleDayList.get(0).getMemo1();
		memo2 = scheduleDayList.get(0).getMemo2();
		memo3 = scheduleDayList.get(0).getMemo3();

		//更新したスケジュールからsqlTypeを改めて取得
		sqlType = ScheduleDayUtil.checkSqlType(scheduleDayList);


		// schedule-day.jspに返す値の設定
		request.setAttribute("scheduleDayList", scheduleDayList);
		request.setAttribute("memo1", memo1);
		request.setAttribute("memo2", memo2);
		request.setAttribute("memo3", memo3);
		request.setAttribute("resultText", "修正に成功しました");
		request.setAttribute("result", true);

		//sqlTypeがupdateのとき
		if(sqlType.equals("update")) {
			request.setAttribute("sqlTypeUpdate", true);
			request.setAttribute("btnValue1", "UPDATE");
			request.setAttribute("btn1", "修正する");
			request.setAttribute("btnValue2", "DELETE");
			request.setAttribute("btn2", "削除する");
		}

		//sqlTypeがinsertのとき
		if(sqlType.equals("insert")) {
			request.setAttribute("sqlTypeUpdate", false);
			request.setAttribute("btnValue1", "INSERT");
			request.setAttribute("btn1", "登録する");
		}


		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);

	}



	/**
	 * sqlTypeが INSERT,UPDATE,DELETE のときtrueを返すメソッド
	 */
	public boolean isBtnSqlTypeIUD(String sqlType) {

		//sqlTypeがINSERT, UPDATE, DELETEでないとき
		if(sqlType != "INSERT" && sqlType != "UPDATE" && sqlType == "DELETE") {
			return false;
		}

		return true;
	}


	/**
	 * sqlTypeがDELETE以外でuserとmemoが全て""でないときtrueを返すメソッド
	 */
	public boolean isNotEmptyAllOrDelete(String user1, String user2, String user3, String memo1, String memo2, String memo3,String sqlType) {

		boolean isEmpUser1 = StringUtils.isEmpty(user1);
		boolean isEmpUser2 = StringUtils.isEmpty(user2);
		boolean isEmpUser3 = StringUtils.isEmpty(user3);
		boolean isEmpMemo1 = StringUtils.isEmpty(memo1);
		boolean isEmpMemo2 = StringUtils.isEmpty(memo2);
		boolean isEmpMemo3 = StringUtils.isEmpty(memo3);

		//user, memoが全て空文字かつsqlTypeがDELETEでないとき
		if(isEmpUser1 && isEmpUser2 && isEmpUser3 && isEmpMemo1 && isEmpMemo2 && isEmpMemo3 && sqlType != "DELETE") {
			return false;
		}

		return true;
	}
}