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
	public List<ScheduleBean> selectScheduleDay(String ymd) {

		//DAOの戻り値をscheduleListで受け取る
			List<ScheduleBean> scheduleList = new ArrayList<>();
			ScheduleDao dao = new ScheduleDao();
			scheduleList = dao.selectScheduleDay(ymd);

			return scheduleList;
	}


	/**
	 * 指定した日付のスケジュールを新規登録するメソッド
	 */
	public boolean insertScheduleDay(List<ScheduleBean> beanList) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.insertScheduleDay(beanList);

		return result;
	}


	/**
	 * 指定した日付のスケジュールを更新するメソッド
	 */
	public boolean updateScheduleDay(List<ScheduleBean> beanList) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.updateScheduleDay(beanList);

		return result;
	}


	/**
	 * 指定した日付のスケジュールを削除するメソッド
	 */
	public boolean deleteScheduleDay(List<ScheduleBean> beanList) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.deleteScheduleDay(beanList);

		return result;
	}
}
