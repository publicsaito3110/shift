package common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import bean.ScheduleBean;
import bean.ScheduleDayBean;
import bean.UserBean;
import bl.ScheduleBl;
import bl.UserBl;

public class ScheduleDayUtil {

	private ScheduleDayUtil() {
		// インスタンス化できないように設定
	}


	public static List<ScheduleDayBean> toListScheduleDayByYmd(String ymd){


		//----------------------------------
		//登録しているid,ユーザ名を取得する
		//----------------------------------
		List <UserBean> userList = new ArrayList<>();
		UserBl bl = new UserBl();
		userList = bl.selectUserIdNameNotDelFlag();


		//---------------------------------------
		//指定した日付のスケジュールを取得する
		//---------------------------------------

		//ymdと一致するスケジュールを取得する
		ScheduleBl bl2 = new ScheduleBl();
		List<ScheduleBean> scheduleList = new ArrayList<>();
		scheduleList = bl2.selectScheduleDay(ymd);

		//userとmemoをあらかじめ定義する
		String user1 = null;
		String user2 = null;
		String user3 = null;
		String memo1 = null;
		String memo2 = null;
		String memo3 = null;

		//scheduleListの要素があるかどうかの判定
		boolean isCheckSizeZeroSL = CommonUtil.isCheckSizeZeroByList(scheduleList);

		//scheduleListの要素が0でないときスケジュールに登録されているuserとmemoを取得
		if(!isCheckSizeZeroSL) {

			user1 = scheduleList.get(0).getUser1();
			user2 = scheduleList.get(0).getUser2();
			user3 = scheduleList.get(0).getUser3();
			memo1 = scheduleList.get(0).getMemo1();
			memo2 = scheduleList.get(0).getMemo2();
			memo3 = scheduleList.get(0).getMemo3();
		}


		//---------------------------------------------
		//userListとscheduleDbListから引き渡す値を設定
		//---------------------------------------------

		//引き渡す値を格納するための変数
		List <ScheduleDayBean> scheduleDayList = new ArrayList<>();
		ScheduleDayBean bean = new ScheduleDayBean();

		String checkUser1 = "";
		String checkUser2 = "";
		String checkUser3 = "";


		//取得したuser,memoを要素(0)に格納
		bean.setUser1(user1);
		bean.setUser2(user2);
		bean.setUser3(user3);
		bean.setMemo1(memo1);
		bean.setMemo2(memo2);
		bean.setMemo3(memo3);


		//共通デフォルトを設定
		bean.setUser("未設定");
		bean.setId("");
		bean.setCheckUser1(Const.optionSelected);
		bean.setCheckUser2(Const.optionSelected);
		bean.setCheckUser3(Const.optionSelected);

		scheduleDayList.add(bean);


		//登録ユーザ(delflgなし)とスケジュールに登録しているユーザを格納
		for(int i = 0; i < userList.size(); i++) {

			//beanとcheckUserをリセット
			bean = new ScheduleDayBean();

			checkUser1 = "";
			checkUser2 = "";
			checkUser3 = "";

			//登録しているユーザとidを取得
			String user = userList.get(i).getName();
			String id = userList.get(i).getId();

			//scheduleListの要素が0でないとき
			if(!isCheckSizeZeroSL) {

				//登録ユーザ(id)とスケジュールに登録されているユーザid(user1)が一致したときselectedをつける
				if(id.equals(user1)) {

					checkUser1 = Const.optionSelected;
				}

				//登録ユーザ(id)とスケジュールに登録されているユーザid(user1)が一致したときselectedをつける
				if(id.equals(user2)) {

					checkUser2 = Const.optionSelected;
				}

				//登録ユーザ(id)とスケジュールに登録されているユーザid(user1)が一致したときselectedをつける
				if(id.equals(user3)) {

					checkUser3 = Const.optionSelected;
				}
			}
			bean.setUser(user);
			bean.setId(id);
			bean.setCheckUser1(checkUser1);
			bean.setCheckUser2(checkUser2);
			bean.setCheckUser3(checkUser3);

			scheduleDayList.add(bean);
		}

		return scheduleDayList;
	}


	/**
	 *user,memoの値を判別し、sqlTypeを返す
	 */
	public static String checkSqlType(List<ScheduleDayBean> scheduleDayList) {


		//user, memoが空文字またはnullのときtrue
		boolean isEmptyUser1 = StringUtils.isEmpty(scheduleDayList.get(0).getUser1());
		boolean isEmptyUser2 = StringUtils.isEmpty(scheduleDayList.get(0).getUser2());
		boolean isEmptyUser3 = StringUtils.isEmpty(scheduleDayList.get(0).getUser3());

		boolean isEmptyMemo1 = StringUtils.isEmpty(scheduleDayList.get(0).getMemo1());
		boolean isEmptyMemo2 = StringUtils.isEmpty(scheduleDayList.get(0).getMemo2());
		boolean isEmptyMemo3 = StringUtils.isEmpty(scheduleDayList.get(0).getMemo3());

		//user,memoが1つでも空文字またはnullでないとき
		if(!isEmptyUser1 || !isEmptyUser2 || !isEmptyUser3 || !isEmptyMemo1 || !isEmptyMemo2 || !isEmptyMemo3) {
			return "update";
		}

		//user,memoが全て空文字またはnullでないとき
		if(isEmptyUser1 && isEmptyUser2 && isEmptyUser3 && isEmptyMemo1 && isEmptyMemo2 && isEmptyMemo3) {
			return "insert";
		}

		return "er";
	}
}
