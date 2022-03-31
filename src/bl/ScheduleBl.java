package bl;

import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import dao.ScheduleDao;

public class ScheduleBl {

	/**
	 * カレンダーに1ヵ月分のスケジュールを表示するメソッド
	 */
	public List<ScheduleBean> selectScheduleMonth(String ym){

		//DAOの戻り値をscheduleListで受け取る
		List<ScheduleBean> scheduleList = new ArrayList<>();
		ScheduleDao dao = new ScheduleDao();
		scheduleList = dao.selectScheduleMonth(ym);

		return scheduleList;
	}


	/**
	 * 指定された日付のスケジュールを取得するメソッド
	 */
	public ScheduleBean selectScheduleDay(String ymd) {

		//DAOの戻り値をscheduleBeanで受け取る
			ScheduleBean scheduleBean = new ScheduleBean();
			ScheduleDao dao = new ScheduleDao();
			scheduleBean = dao.selectScheduleDay(ymd);

			return scheduleBean;
	}


	/**
	 * 指定した日付のスケジュールを新規登録するメソッド
	 */
	public boolean insertScheduleDay(ScheduleBean scheduleBean) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean isResult = dao.insertScheduleDay(scheduleBean);

		return isResult;
	}


	/**
	 * 指定した日付のスケジュールを更新するメソッド
	 */
	public boolean updateScheduleDay(ScheduleBean scheduleBean) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean isResult = dao.updateScheduleDay(scheduleBean);

		return isResult;
	}


	/**
	 * 指定した日付のスケジュールを削除するメソッド
	 */
	public boolean deleteScheduleDay(ScheduleBean scheduleBean) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean isResult = dao.deleteScheduleDay(scheduleBean);

		return isResult;
	}
}
