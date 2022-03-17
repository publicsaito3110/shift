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
		boolean er = true;  //エラーかどうかの判定


		//-------------------------------------
		//scheduleDay.jsp からの値を受け取る
		//-------------------------------------
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		//dayが2桁でないときdayに"0"をつける
		String ymd = CommonUtil.ymdFormatEight(year, month, day);

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


		//--------------------------
		//バリデーションチェック
		//--------------------------

		if(CommonUtil.typeSQLCheck(sqlType)) {        //sqlTypeの値がINSERT,UPDATE,DELETE以外のとき
			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 不正な入力値を検知しました");
			request.setAttribute("er", er);
			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}

		if(afterMemo1.isEmpty() && afterMemo2.isEmpty() && afterMemo3.isEmpty() && sqlType != "DELETE") {  //afterMemoが全て""で削除ボタン以外が押されたとき

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 修正後のシフトを入力してください");
			request.setAttribute("er", er);
			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}



		//aftermemoが""(未入力)のときnullを代入する
		afterMemo1 = CommonUtil.changeNull(afterMemo1);
		afterMemo2 = CommonUtil.changeNull(afterMemo2);
		afterMemo3 = CommonUtil.changeNull(afterMemo3);

		//受け取った値をArrayListに格納
		List<ScheduleBean> beanList = new ArrayList<>();
		ScheduleBean bean = new ScheduleBean();

		bean.setYmd(ymd);
		bean.setMemo1(afterMemo1);
		bean.setMemo2(afterMemo2);
		bean.setMemo3(afterMemo3);

		beanList.add(bean);


		//-------------------------ー------------
		//UPDATE,DELETE,INSERTを判別し、SQL実行
		//---------------------------------------
		boolean recordResult = false;   //結果をbooleanで受け取る
		ScheduleBl bl = new ScheduleBl(); //BLをインスタンス化

		switch(sqlType){

		case "UPDATE":    //修正ボタンが押されたとき

			recordResult = bl.updateScheduleDB(beanList);
			break;

		case "DELETE":    //削除ボタンが押されたとき

			recordResult = bl.deleteScheduleDB(beanList);
			break;

		case "INSERT":    //登録ボタンが押されたとき

			recordResult = bl.insertScheduleDB(beanList);
			break;

		default:    //不正な値を受け取ったとき

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 不正な入力値を検知しました");
			request.setAttribute("er", er);
			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;

		}


		//-----------------
		//DBの更新が失敗
		//-----------------
		if(recordResult == false) {

			// schedule-day.jspに返す値
			request.setAttribute("memo1", beforeMemo1);
			request.setAttribute("memo2", beforeMemo2);
			request.setAttribute("memo3", beforeMemo3);
			request.setAttribute("resultText", "[エラー] 修正に失敗しました");
			request.setAttribute("er", er);
			// 画面遷移
			request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);
			return;
		}

		//-----------------
		//DBの更新が成功
		//-----------------

		// schedule-day.jspに返す値の設定
		if(sqlType.equals("UPDATE") || sqlType.equals("INSERT")) {     //INSERT,UPDATEのとき
			request.setAttribute("memo1", afterMemo1);
			request.setAttribute("memo2", afterMemo2);
			request.setAttribute("memo3", afterMemo3);

		}else {       //DELETEのとき
			request.setAttribute("memo1", "");
			request.setAttribute("memo2", "");
			request.setAttribute("memo3", "");
		}

		er = false;

		request.setAttribute("resultText", "修正に成功しました");
		request.setAttribute("er", er);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);

	}
}
