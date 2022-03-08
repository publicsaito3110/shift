package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import common.DbConst;

public class ScheduleSeachDao {


		//-------1ヵ月分のスケジュールを取得するメソッド---------------------------------------------------
		public List<ScheduleBean> selectScheduleMonthDB() {


			//JDBCの接続に使用するオブジェクトを宣言
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			List<ScheduleBean> beanList = new ArrayList<ScheduleBean>();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT YMD,MEMO1,MEMO2,MEMO3 FROM SCHEDULE ORDER BY YMD");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				ScheduleBean bean = new ScheduleBean();
				//ymdからday部分のみを抽出し、beanにsetする
				String ymd =  rs.getString("ymd");
				String day = ymd.substring(6);
				bean.setDay(day);
				bean.setYmd(rs.getString("ymd"));
				bean.setMemo1(rs.getString("memo1"));
				bean.setMemo2(rs.getString("memo2"));
				bean.setMemo3(rs.getString("memo3"));
				beanList.add(bean);
			}

			System.out.print("");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			//DBの接続解除
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return beanList;
	}



		//-----------1日分のスケジュールを取得するメソッド----------------------------------------------------
		public List<ScheduleBean> selectScheduleDayDB(String ymd) {


			//JDBCの接続に使用するオブジェクトを宣言
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			List<ScheduleBean> beanList = new ArrayList<ScheduleBean>();

			try {

				Class.forName(DbConst.DRIVER_NAME);

				//conにDB情報を入れる
				con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

				//SQL発行
				StringBuffer buf = new StringBuffer();
				buf.append("SELECT MEMO1,MEMO2,MEMO3 FROM SCHEDULE WHERE ");
				buf.append(" YMD =  ?  ");
				buf.append(" ; ");

				//SQLをセット
				ps = con.prepareStatement(buf.toString());

				// ? にパラメータをセット
				ps.setString(1,ymd);

				//SQLの結果を取得
				rs = ps.executeQuery();

				//結果をさらに抽出
				while (rs.next()) {
					ScheduleBean bean = new ScheduleBean();

					//memoの部分のみを抽出し、beanにsetする
					bean.setMemo1(rs.getString("memo1"));
					bean.setMemo2(rs.getString("memo2"));
					bean.setMemo3(rs.getString("memo3"));
					beanList.add(bean);
				}

				System.out.print("");

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				//DBの接続解除
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			return beanList;
		}

}
