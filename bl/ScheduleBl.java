package bl;

import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import dao.ModifyScheduleDao;
import dao.SeachScheduleDao;

public class ScheduleBl {


	//カレンダーに1ヵ月分のスケジュールを表示するメソッド--------------------------------
	public List<ScheduleBean> selectScheduleMonthDB(){

		//DAOの戻り値をscheduleListで受け取る
		List<ScheduleBean> scheduleList = new ArrayList<>();
		SeachScheduleDao dao = new SeachScheduleDao();
		scheduleList = dao.selectScheduleMonthDB();

		return scheduleList;
	}

	//指定された日付のスケジュールを取得するメソッド---------------------------------------
	public List<ScheduleBean> searchScheduleDay(String ymd) {

		//DAOの戻り値をscheduleListで受け取る
			List<ScheduleBean> scheduleList = new ArrayList<>();
			SeachScheduleDao dao = new SeachScheduleDao();
			scheduleList = dao.selectScheduleDayDB(ymd);

			return scheduleList;
	}

	//指定された日付のスケジュールを変更するメソッド-----------------------------------------
	public boolean modifyScheduleDay(List<ScheduleBean> bean) {

		//DAOの戻り値を modifyResult で受け取る
		ModifyScheduleDao dao = new ModifyScheduleDao();
		boolean modifyResult = dao.modifyScheduleDB(bean);

		return modifyResult;
	}
}
