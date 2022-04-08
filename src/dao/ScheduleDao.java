package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ScheduleBean;
import common.Const;
import common.DbConst;

public class ScheduleDao {


	/**
	 *1ヵ月分のスケジュールを取得する
	 *@param String ym, Month is status of YYYYMM
	 *@return List<ScheduleBean>, Recoding schedule in this month
	 */
	public List<ScheduleBean> selectScheduleMonth(String ym) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ScheduleBean> beanList = new ArrayList<>();

	try {

		Class.forName(DbConst.DRIVER_NAME);

		//conにDB情報を入れる
		con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

		//SQL発行
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT YMD, USER1, MEMO1, USER2, MEMO2, USER3, MEMO3 ");
		buf.append(" FROM SCHEDULE ");
		buf.append(" WHERE YMD LIKE ? ORDER BY YMD");

		//SQLをセット
		ps = con.prepareStatement(buf.toString());

		// ? にパラメータをセット
		ps.setString(1, ym + Const.PERCENT);

		//SQLの結果を取得
		rs = ps.executeQuery();

		//結果をさらに抽出
		while (rs.next()) {
			ScheduleBean bean = new ScheduleBean();

			//ymdからday部分のみを抽出し、beanにsetする
			String ymd =  rs.getString("ymd");
			String day = ymd.substring(6);

			bean.setDay(day);
			bean.setUser1(rs.getString("user1"));
			bean.setMemo1(rs.getString("memo1"));
			bean.setUser2(rs.getString("user2"));
			bean.setMemo2(rs.getString("memo2"));
			bean.setUser3(rs.getString("user3"));
			bean.setMemo3(rs.getString("memo3"));
			beanList.add(bean);
		}

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



	/**
	 *1日分のスケジュールを取得するメソッド
	 *@param String ymd, A date is status of YYYYMMDD
	 *@return ScheduleBean, Recoding schedule in this date
	 */
	public ScheduleBean selectScheduleDay(String ymd) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//受け渡すための変数
		ScheduleBean scheduleBean = new ScheduleBean();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT USER1, MEMO1, USER2, MEMO2, USER3, MEMO3 ");
			buf.append(" FROM SCHEDULE ");
			buf.append(" WHERE YMD =  ?  ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, ymd);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {

				scheduleBean.setUser1(rs.getString("user1"));
				scheduleBean.setMemo1(rs.getString("memo1"));
				scheduleBean.setUser2(rs.getString("user2"));
				scheduleBean.setMemo2(rs.getString("memo2"));
				scheduleBean.setUser3(rs.getString("user3"));
				scheduleBean.setMemo3(rs.getString("memo3"));
			}

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

		return scheduleBean;
	}


	/**
	 * スケジュールを新規で追加する
	 * @param ScheduleBean scheduleBean, Schedule info taht you want to record
	 * @return boolean, Return true in insert schedule
	 */
	public boolean insertScheduleDay(ScheduleBean scheduleBean){

		//実行結果を取得
		boolean isResult = false;

		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;


		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL発行
			StringBuffer buf = new StringBuffer();

			buf.append("INSERT INTO ");
			buf.append(" SCHEDULE ");
			buf.append(" (YMD, USER1, MEMO1, USER2, MEMO2, USER3, MEMO3) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?, ?, ?, ?, ?, ?) ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ?にパラメーターをセット
			ps.setString(1, scheduleBean.getYmd());
			ps.setString(2, scheduleBean.getUser1());
			ps.setString(3, scheduleBean.getMemo1());
			ps.setString(4, scheduleBean.getUser2());
			ps.setString(5, scheduleBean.getMemo2());
			ps.setString(6, scheduleBean.getUser3());
			ps.setString(7, scheduleBean.getMemo3());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			isResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(isResult){    //SQLの実行成功時、明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{              //SQLの実行失敗時、明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//DBの接続解除
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

		return isResult;
	}


	/**
	 *スケジュールを更新する
	 *@param ScheduleBean scheduleBean, Schedule info taht you want to modify
	 *@return boolean, Return true in update schedule
	 */
	public boolean updateScheduleDay(ScheduleBean scheduleBean){


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;

		//実行結果を取得するための変数
		boolean isResult = false;


		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL発行
			StringBuffer buf = new StringBuffer();

			buf.append("UPDATE ");
			buf.append(" SCHEDULE ");
			buf.append(" SET ");
			buf.append(" USER1 = ?,");
			buf.append(" MEMO1 = ?,");
			buf.append(" USER2 = ?,");
			buf.append(" MEMO2 = ?,");
			buf.append(" USER3 = ?,");
			buf.append(" MEMO3 = ? ");
			buf.append(" WHERE YMD = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, scheduleBean.getUser1());
			ps.setString(2, scheduleBean.getMemo1());
			ps.setString(3, scheduleBean.getUser2());
			ps.setString(4, scheduleBean.getMemo2());
			ps.setString(5, scheduleBean.getUser3());
			ps.setString(6, scheduleBean.getMemo3());
			ps.setString(7, scheduleBean.getYmd());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			isResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(isResult){    //SQLの実行成功時、明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{              //SQLの実行失敗時、明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//DBの接続解除
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

		return isResult;
	}


	/**
	 *スケジュールを削除する
	 *@param ScheduleBean scheduleBean, Schedule taht you want to remove
	 *@return boolean, Return true in delete schedule
	 */
	public boolean deleteScheduleDay(ScheduleBean scheduleBean){


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;


		//実行結果を取得するための変数
		boolean isResult = false;


		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("DELETE ");
			buf.append(" FROM ");
			buf.append(" SCHEDULE ");
			buf.append(" WHERE YMD = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, scheduleBean.getYmd());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			isResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(isResult){    //SQLの実行成功時、明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{              //SQLの実行失敗時、明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//DBの接続解除
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

		return isResult;
	}
}
