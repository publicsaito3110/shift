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

public class ScheduleDao {


	/**
	 *1ヵ月分のスケジュールを取得するメソッド
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
		ps.setString(1, ym + "%");

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



	/**
	 *1日分のスケジュールを取得するメソッド
	 */
	public List<ScheduleBean> selectScheduleDay(String ymd) {


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
			buf.append("SELECT USER1, MEMO1, USER2, MEMO2, USER3, MEMO3 ");
			buf.append(" FROM SCHEDULE ");
			buf.append(" WHERE YMD =  ?  ");
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

				bean.setUser1(rs.getString("user1"));
				bean.setMemo1(rs.getString("memo1"));
				bean.setUser2(rs.getString("user2"));
				bean.setMemo2(rs.getString("memo2"));
				bean.setUser3(rs.getString("user3"));
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


	/**
	 * スケジュールを新規で追加する(INSERT)
	 */
	public boolean insertScheduleDay(List<ScheduleBean> beanList){

		//実行結果を取得
		boolean result = false;

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
			ps.setString(1, beanList.get(0).getYmd());
			ps.setString(2, beanList.get(0).getUser1());
			ps.setString(3, beanList.get(0).getMemo1());
			ps.setString(4, beanList.get(0).getUser2());
			ps.setString(5, beanList.get(0).getMemo2());
			ps.setString(6, beanList.get(0).getUser3());
			ps.setString(7, beanList.get(0).getMemo3());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			result = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(result){    //SQLの実行成功時、明示的にコミットを実施
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

		return result;
	}


	/**
	 *スケジュールを更新する(UPDATE)
	 */
	public boolean updateScheduleDay(List<ScheduleBean> beanList){

		//実行結果を取得
		boolean result = false;

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
			ps.setString(1, beanList.get(0).getUser1());
			ps.setString(2, beanList.get(0).getMemo1());
			ps.setString(3, beanList.get(0).getUser2());
			ps.setString(4, beanList.get(0).getMemo2());
			ps.setString(5, beanList.get(0).getUser3());
			ps.setString(6, beanList.get(0).getMemo3());
			ps.setString(7, beanList.get(0).getYmd());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			result = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(result){    //SQLの実行成功時、明示的にコミットを実施
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

		return result;
	}


	/**
	 *スケジュールを更新する(DELETE)
	 */
	public boolean deleteScheduleDay(List<ScheduleBean> beanList){

		//実行結果を取得
		boolean result = false;

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
			buf.append("DELETE ");
			buf.append(" FROM ");
			buf.append(" SCHEDULE ");
			buf.append(" WHERE YMD = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, beanList.get(0).getYmd());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			result = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(result){    //SQLの実行成功時、明示的にコミットを実施
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

		return result;
	}
}
