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
import common.CommonUtil;

/**
 * Servlet implementation class ScheduleCorrectServlet
 */
@WebServlet("/ScheduleCorrectServlet")
public class ScheduleCorrectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScheduleCorrectServlet() {
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


		boolean afterFormFlag = true;   //Form後かどうかの判定


		//-------------------------------------
		//scheduleDay.jsp からの値を受け取る
		//-------------------------------------
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");


		String afterMemo1 = request.getParameter("afterMemo1");
		String afterMemo2 = request.getParameter("afterMemo2");
		String afterMemo3 = request.getParameter("afterMemo3");

		String beforeMemo1 = request.getParameter("beforeMemo1");
		String beforeMemo2 = request.getParameter("beforeMemo2");
		String beforeMemo3 = request.getParameter("beforeMemo3");

		String sqlType = request.getParameter("sqlType");

		//------------------------
		//共通で返す値を設定
		//------------------------
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("afterFormFlag", afterFormFlag);
		request.setAttribute("popTitle", "シフトの修正結果");


		//--------------------------
		//バリデーションチェック
		//--------------------------

		//beforememoが""(未入力)のときnullを代入する
		beforeMemo1 = CommonUtil.changeNull(beforeMemo1);
		beforeMemo2 = CommonUtil.changeNull(beforeMemo2);
		beforeMemo3 = CommonUtil.changeNull(beforeMemo3);

		//sqlTypeの値をチェックする
		boolean isVali1 = this.isSqlTypeIUD(sqlType);

		//sqlTypeの値がINSERT,UPDATE,DELETE以外のとき
		if(!isVali1) {

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 不正な入力値を検知しました");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}

		//afterMemoの値とsqlTypeの組み合わせをチェックする
		boolean isVali2 = this.isNotEmptyAllOrDelete(afterMemo1, afterMemo2, afterMemo3, sqlType);

		//afterMemoが全て""かつ削除ボタン以外が押されたとき
		if(!isVali2) {

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 修正後のシフトを入力してください");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}



		//aftermemoが""(未入力)のときnullを代入する
		afterMemo1 = CommonUtil.changeNull(afterMemo1);
		afterMemo2 = CommonUtil.changeNull(afterMemo2);
		afterMemo3 = CommonUtil.changeNull(afterMemo3);

		//aftermemoをエスケープする
		afterMemo1 = CommonUtil.replaceEscapeChar(afterMemo1);
		afterMemo2 = CommonUtil.replaceEscapeChar(afterMemo2);
		afterMemo3 = CommonUtil.replaceEscapeChar(afterMemo3);

		//受け取った値をArrayListに格納
		List<ScheduleBean> beanList = new ArrayList<>();
		ScheduleBean bean = new ScheduleBean();

		//dayが2桁でないときdayに"0"をつける
		String ymd = CommonUtil.ymdFormatEightByString(year, month, day);

		bean.setYmd(ymd);
		bean.setMemo1(afterMemo1);
		bean.setMemo2(afterMemo2);
		bean.setMemo3(afterMemo3);

		beanList.add(bean);


		//-------------------------ー------------
		//UPDATE,DELETE,INSERTを判別し、SQL実行
		//---------------------------------------
		boolean isRecordResult = false;   //結果をbooleanで受け取る
		ScheduleBl bl = new ScheduleBl(); //BLをインスタンス化

		switch(sqlType){

		case "UPDATE":    //修正ボタンが押されたとき

			isRecordResult = bl.updateScheduleDB(beanList);
			break;

		case "DELETE":    //削除ボタンが押されたとき

			isRecordResult = bl.deleteScheduleDB(beanList);
			break;

		case "INSERT":    //登録ボタンが押されたとき

			isRecordResult = bl.insertScheduleDB(beanList);
			break;

		default:    //不正な値を受け取ったとき

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 不正な入力値を検知しました");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;

		}


		//-----------------
		//DBの更新が失敗
		//-----------------
		if(!isRecordResult) {

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 修正に失敗しました");
			request.setAttribute("result", false);

			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}

		//-----------------
		//DBの更新が成功
		//-----------------

		// schedule-day.jspに返す値の設定
		 //INSERT,UPDATEのとき
		if(sqlType == "UPDATE" || sqlType =="INSERT") {
			request.setAttribute("memo1", afterMemo1);
			request.setAttribute("memo2", afterMemo2);
			request.setAttribute("memo3", afterMemo3);

		}else {       //DELETEのとき
			request.setAttribute("memo1", "");
			request.setAttribute("memo2", "");
			request.setAttribute("memo3", "");
		}


		request.setAttribute("resultText", "修正に成功しました");
		request.setAttribute("result", true);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);

	}



	/**
	 * sqlTypeが INSERT,UPDATE,DELETE のときtrueを返すメソッド
	 */
	public boolean isSqlTypeIUD(String sqlType) {

		//sqlTypeがINSERT, UPDATE, DELETEでないとき
		if(sqlType != "INSERT" || sqlType != "UPDATE" || sqlType !="DELETE") {
			return false;
		}

		return true;
	}


	/**
	 * sqlTypeがDELETE以外でmemoが全て""でないときtrueを返すメソッド
	 */
	public boolean isNotEmptyAllOrDelete(String memo1, String memo2, String memo3,String sqlType) {

		//memoが全て空文字かつsqlTypeがDELETEでないとき
		if(memo1.isEmpty() && memo2.isEmpty() && memo3.isEmpty() && sqlType != "DELETE") {
			return false;
		}

		return true;
	}
}
