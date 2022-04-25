package bl;

import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import dao.ScheduleDao;

/**
 * @author saito
 *
 */
public class ScheduleBl extends BaseBl {

	/**
	 * 1ヵ月スケジュール取得処理
	 *
	 * <p>指定した年月から1ヵ月分のスケジュールを取得する<br>
	 * ただし、スケジュールがないときは取得しない</p>
	 *
	 * @param ym 年月(YYYYMM)
	 * @return List<ScheduleBean> 登録されているスケジュールの日付(day), スケジュールに登録されているユーザ(user), メモ(memo)
	 */
	public List<ScheduleBean> selectScheduleMonth(String ym){

		//DAOの戻り値をscheduleListで受け取る
		List<ScheduleBean> scheduleList = new ArrayList<>();
		ScheduleDao dao = new ScheduleDao();
		scheduleList = dao.selectScheduleMonth(ym);

		return scheduleList;
	}


	/**
	 * 1日スケジュール取得処理
	 *
	 * <p>指定した年月から1日分のスケジュールを取得する<br>
	 * ただし、スケジュールがないときは取得しない</p>
	 *
	 * @param ymd 日付(YYYYMMDD)
	 * @return ScheduleBean スケジュールに登録されているユーザ(user), メモ(memo)
	 */
	public ScheduleBean selectScheduleDay(String ymd) {

		//DAOの戻り値をscheduleBeanで受け取る
			ScheduleBean scheduleBean = new ScheduleBean();
			ScheduleDao dao = new ScheduleDao();
			scheduleBean = dao.selectScheduleDay(ymd);

			return scheduleBean;
	}


	/**
	 * スケジュール新規登録処理
	 *
	 * <p>登録されていない日付にスケジュールを新規で追加する</p>
	 *
	 * @param scheduleBean 登録したいスケジュールの日付(day), スケジュールに登録されているユーザ(user), メモ(memo)
	 * @return boolean true:スケジュールの登録が成功したとき false:スケジュールの登録が失敗したとき
	 */
	public boolean insertScheduleDay(ScheduleBean scheduleBean) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean isResult = dao.insertScheduleDay(scheduleBean);

		return isResult;
	}


	/**
	 * スケジュール修正処理
	 *
	 * <p>登録されている日付にスケジュールを更新する</p>
	 *
	 * @param scheduleBean 修正したいスケジュールの日付(day), スケジュールに登録されているユーザ(user), メモ(memo)
	 * @return boolean true:スケジュールの修正が成功したとき false:スケジュールの修正が失敗したとき
	 */
	public boolean updateScheduleDay(ScheduleBean scheduleBean) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean isResult = dao.updateScheduleDay(scheduleBean);

		return isResult;
	}


	/**
	 * スケジュール削除処理
	 *
	 * <p>登録されている日付のスケジュールを削除する</p>
	 *
	 * @param scheduleBean 削除したいスケジュールの日付(day), スケジュールに登録されているユーザ(user), メモ(memo)
	 * @return boolean true:スケジュールの修正が成功したとき false:スケジュールの修正が失敗したとき
	 */
	public boolean deleteScheduleDay(ScheduleBean scheduleBean) {

		//DAOの戻り値を modifyResult で受け取る
		ScheduleDao dao = new ScheduleDao();
		boolean isResult = dao.deleteScheduleDay(scheduleBean);

		return isResult;
	}
}
