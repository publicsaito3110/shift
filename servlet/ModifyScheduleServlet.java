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
 * Servlet implementation class ModifyScheduleServlet
 */
@WebServlet("/ModifyScheduleServlet")
public class ModifyScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyScheduleServlet() {
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


		//結果をbooleanで受け取るため
		boolean ModifyResult = false;
		//scheduleDay.jsp からの値を格納する
		String afterMemo1 = "";
		String afterMemo2 = "";
		String afterMemo3 = "";

		//scheduleDay.jsp からの値を受け取る
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String btn = request.getParameter("btn");

		String ymd = "";
		//dayが2桁でないときdayに "0" をつける
		if(day.length() != 2) {
			ymd = year + month + "0" + day;
		}else {
			ymd = year + month + day;
		}

		String beforeMemo1 = request.getParameter("beforeMemo1");
		String beforeMemo2 = request.getParameter("beforeMemo2");
		String beforeMemo3 = request.getParameter("beforeMemo3");


		afterMemo1 = request.getParameter("afterMemo1");
		afterMemo2 = request.getParameter("afterMemo2");
		afterMemo3 = request.getParameter("afterMemo3");


		// beforeMemo, afterMemo の値が "" のときはnull を代入させる
		if(beforeMemo1 == "") {
			beforeMemo1 = null;
		}
		if(beforeMemo2 == "") {
			beforeMemo2 = null;
		}

		if(beforeMemo3 == "") {
			beforeMemo3 = null;
		}
		if(afterMemo1 == "") {
			afterMemo1 = null;
		}
		if(afterMemo2 == "") {
			afterMemo2 = null;
		}
		if(afterMemo3 == "") {
			afterMemo3 = null;
		}


		//バリデーションチェック---------------------------------------------------------------------------------------
		if(beforeMemo1 == afterMemo1 && beforeMemo2 == afterMemo2 && beforeMemo3 == afterMemo3 && btn == "") {  //afterMemoがbeforeMemo全く同じ値で修正ボタンが押されたとき
			//エラー発生ページへ
			System.out.println("修正に失敗しました!  バリデーションチェック");
		}else if(afterMemo1 == null && afterMemo2 == null && afterMemo3 == null && btn == "") {  //afterMemoが""で修正ボタンが押されたとき
			//エラー発生ページへ
			System.out.println("修正に失敗しました!  バリデーションチェック");
		}



		//修正箇所の入力のみで該当部分を修正できるようにするため、afterMemo = null のとき beforeMemo を代入する                                                            //INSERT以外のとき
		if(afterMemo1 == null) {
			afterMemo1 = beforeMemo1;
		}
		if(afterMemo2 == null) {
			afterMemo2 = beforeMemo2;
		}
		if(afterMemo3 == null) {
			afterMemo3 = beforeMemo3;
		}


		//受け取った値をArrayListに格納
		List<ScheduleBean> beanList = new ArrayList<>();
		ScheduleBean bean = new ScheduleBean();

		bean.setYmd(ymd);
		bean.setMemo1(afterMemo1);
		bean.setMemo2(afterMemo2);
		bean.setMemo3(afterMemo3);


		//INSERT, UPDATE, DELETE を判別する
		if(beforeMemo1 == null && beforeMemo2 == null && beforeMemo3 == null && btn == "") {      //beforeMemo が全て null で修正ボタンが押されたとき
			//bean の typeIUD に "I" をsetし、BLに引き渡す
			bean.setTypeIUD("I");
			beanList.add(bean);      //INSERTのとき

			ScheduleBl bl = new ScheduleBl();
			ModifyResult = bl.modifyScheduleDay(beanList);

		}else if((beforeMemo1 != afterMemo1 || beforeMemo2 != afterMemo2 || beforeMemo3 != afterMemo3) && btn == "") {  //beforeMemo のいずれかに値が入っていて修正ボタンが押されたとき
			//bean の typeIUD に "U" をsetし、BLに引き渡す
			bean.setTypeIUD("U");
			beanList.add(bean);  //UPDATEのとき

			ScheduleBl bl = new ScheduleBl();
			ModifyResult = bl.modifyScheduleDay(beanList);

		}else if(btn != "") {                //削除ボタンが押されたとき
			//bean の typeIUD に "D" をsetし、BLに引き渡す
			bean.setTypeIUD("D");
			beanList.add(bean);

			ScheduleBl bl = new ScheduleBl();
			ModifyResult = bl.modifyScheduleDay(beanList);   //DELETEのとき

		}else {
			//エラー発生ページへ
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
		}

		//結果
		if(ModifyResult) {
			//修正成功ページへ
			System.out.println("修正に成功しました!  SQL実行成功");

		}else {
			//エラー発生ページへ
			System.out.println("修正に失敗しました!  SQL実行失敗");
		}
	}
}

