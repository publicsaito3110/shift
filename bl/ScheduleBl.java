package bl;

import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import dao.ScheduleDao;

public class ScheduleBl {


	public List<ScheduleBean> selectScheduleMonthDB(){        //カレンダーに1ヵ月分のスケジュールを表示するメソッド

		//DAOの戻り値をscheduleListで受け取る
		List<ScheduleBean> scheduleList = new ArrayList<>();
		ScheduleDao dao = new ScheduleDao();
		scheduleList = dao.selectScheduleMonthDB();

		return scheduleList;
	}


	public List<ScheduleBean> searchScheduleDay(String ymd) {   //指定された日付のスケジュールを取得するメソッド

		//DAOの戻り値をscheduleListで受け取る
			List<ScheduleBean> scheduleList = new ArrayList<>();
			ScheduleDao dao = new ScheduleDao();
			scheduleList = dao.selectScheduleDayDB(ymd);

			return scheduleList;
	}


	public boolean insertScheduleDB(List<ScheduleBean> beanList) {   //スケジュールをINSERTするメソッド

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.insertScheduleDB(beanList);

		return result;
	}


	public boolean updateScheduleDB(List<ScheduleBean> beanList) {            //スケジュールをUPDATEするメソッド

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.updateScheduleDB(beanList);

		return result;
	}


	public boolean deleteScheduleDB(List<ScheduleBean> beanList) {            //スケジュールをDELETEするメソッド

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean result = dao.deleteScheduleDB(beanList);

		return result;
	}
}
