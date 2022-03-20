package bl;

import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import dao.ScheduleDao;

public class ScheduleBl {

	/**
	 * カレンダーに1ヵ月分のスケジュールを表示するメソッド
	 */
	public List<ScheduleBean> selectScheduleMonthDB(){

		//DAOの戻り値をscheduleListで受け取る
		List<ScheduleBean> scheduleList = new ArrayList<>();
		ScheduleDao dao = new ScheduleDao();
		scheduleList = dao.selectScheduleMonthDB();

		return scheduleList;
	}


	/**
	 * 指定された日付のスケジュールを取得するメソッド
	 */
	public List<ScheduleBean> searchScheduleDay(String ymd) {

		//DAOの戻り値をscheduleListで受け取る
			List<ScheduleBean> scheduleList = new ArrayList<>();
			ScheduleDao dao = new ScheduleDao();
			scheduleList = dao.selectScheduleDayDB(ymd);

			return scheduleList;
	}


	/**
	 * スケジュールをINSERTするメソッド
	 */
	public boolean insertScheduleDB(List<ScheduleBean> beanList) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.insertScheduleDB(beanList);

		return result;
	}


	/**
	 * スケジュールをUPDATEするメソッド
	 */
	public boolean updateScheduleDB(List<ScheduleBean> beanList) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.updateScheduleDB(beanList);

		return result;
	}


	/**
	 * スケジュールをDELETEするメソッド
	 */
	public boolean deleteScheduleDB(List<ScheduleBean> beanList) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.deleteScheduleDB(beanList);

		return result;
	}
}
