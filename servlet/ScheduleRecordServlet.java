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
 * Servlet implementation class ScheduleRecordServlet
 */
@WebServlet("/ScheduleRecordServlet")
public class ScheduleRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScheduleRecordServlet() {
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


		boolean recordResult = false;   //結果をbooleanで受け取る
		boolean pop = true;              //ポップアップウィンドウを表示させるため


		//scheduleDay.jsp からの値を受け取る
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		//dayが2桁でないときdayに"0"をつける
		String ymd = CommonUtil.ymdFormatEight(year, month, day);

		String beforeMemo1 = request.getParameter("beforeMemo1");
		String beforeMemo2 = request.getParameter("beforeMemo2");
		String beforeMemo3 = request.getParameter("beforeMemo3");


		String afterMemo1 = request.getParameter("afterMemo1");
		String afterMemo2 = request.getParameter("afterMemo2");
		String afterMemo3 = request.getParameter("afterMemo3");


		//バリデーションチェック---------------------------------------------------------------------------------------
		boolean valiCheck = false;

		if(beforeMemo1 == afterMemo1 && beforeMemo2 == afterMemo2 && beforeMemo3 == afterMemo3) {    //afterMemoがbeforeMemo全く同じ値
			valiCheck = false;
		}else if(afterMemo1.isEmpty() && afterMemo2.isEmpty() && afterMemo3.isEmpty()) {             //afterMemoが""で修正ボタンが押されたとき
			valiCheck = false;
		}else {
			valiCheck = true;    //バリデーションチェックに引っかからなければ、true
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


		//INSERT, UPDATE を判別する
		if(beforeMemo1.isEmpty() && beforeMemo2.isEmpty() && beforeMemo3.isEmpty() && valiCheck) {      //beforeMemoが全て未登録でvaliCheckもtrueのとき
			//beanのtypeIUD に"I"をsetし、BLに引き渡す
			bean.setTypeIUD("I");
			beanList.add(bean);       //INSERTのとき

			ScheduleBl bl = new ScheduleBl();
			recordResult = bl.modifyScheduleDay(beanList);

		}else if((beforeMemo1 != afterMemo1 || beforeMemo2 != afterMemo2 || beforeMemo3 != afterMemo3) && valiCheck) {  //beforeMemoのいずれかに値が入っていてvaliCheckもtrueのとき
			//beanのtypeIUDに"U"をsetし、BLに引き渡す
			bean.setTypeIUD("U");
			beanList.add(bean);       //UPDATEのとき

			ScheduleBl bl = new ScheduleBl();
			recordResult = bl.modifyScheduleDay(beanList);
		}


		//結果を反映
		if(recordResult) {         //SQLの実行が成功したとき
			// 引き渡す値(スケジュール修正後) + recodResultを設定し、scheduleDay.jspへ返す
			request.setAttribute("memo1", afterMemo1);  //反映した結果を返す
			request.setAttribute("memo2", afterMemo2);
			request.setAttribute("memo3", afterMemo3);

		} else {         //SQLの実行が失敗したとき

			//aftermemoが""(未入力)のときnullを代入する
			request.setAttribute("memo1", CommonUtil.changeNull(beforeMemo1));
			request.setAttribute("memo2", CommonUtil.changeNull(beforeMemo2));     //修正前の結果を引き渡す
			request.setAttribute("memo3", CommonUtil.changeNull(beforeMemo3));
		}

		// 引き渡す値(スケジュール修正前) + recodResultを設定し、scheduleDay.jspへ返す
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("modifyResult", recordResult);
		request.setAttribute("pop", pop);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);

	}
}
