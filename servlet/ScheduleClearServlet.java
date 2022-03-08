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
 * Servlet implementation class ScheduleClearServlet
 */
@WebServlet("/ScheduleClearServlet")
public class ScheduleClearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleClearServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードをUTF-8で設定
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		boolean removeResult = false;              //結果をbooleanで受け取るため
		boolean pop = true;                         //ポップアップウィンドウを表示させるため

		//scheduleDay.jsp からの値を受け取る
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		//dayが2桁でないときdayに"0"をつける
		String ymd = CommonUtil.ymdFormatEight(year, month, day);

		//受け取った値をArrayListに格納
		List<ScheduleBean> beanList = new ArrayList<>();
		ScheduleBean bean = new ScheduleBean();

		bean.setYmd(ymd);
		bean.setTypeIUD("D");
		beanList.add(bean);  //DELETEのとき

		ScheduleBl bl = new ScheduleBl();
		removeResult = bl.modifyScheduleDay(beanList);


		// 引き渡す値(スケジュール修正後) + recodResultを設定し、scheduleDay.jspへ返す
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("memo1", null);                    //反映した結果(値なし)を返す
		request.setAttribute("memo2", null);
		request.setAttribute("memo3", null);
		request.setAttribute("modifyResult", removeResult);
		request.setAttribute("pop", pop);

		// 画面遷移
		request.getRequestDispatcher("/WEB-INF/jsp/schedule-day.jsp").forward(request, response);

	}

}
