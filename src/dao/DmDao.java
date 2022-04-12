package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.DmBean;
import common.DbConst;

public class DmDao {



	/**
	 *ログインしているユーザが最後に送信したメッセージを非ログインユーザごとに取得する
	 */
	public List<DmBean> selectSendMsgUserIdLastMsg(String LoginUser) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DmBean> beanList = new ArrayList<>();

	try {

		Class.forName(DbConst.DRIVER_NAME);

		//conにDB情報を入れる
		con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

		//SQL発行
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT d.msg_date, d.msg, d.send_user, d.receive_user, CONCAT(d.receive_user, d.send_user) AS DM_ID_PTN, u.name AS MSG_TO_NAME ");
		buf.append(" FROM ( DM d INNER JOIN ");
		buf.append(" (SELECT send_user, receive_user, MAX(msg_date) AS max_date FROM DM WHERE send_user = ? GROUP BY send_user, receive_user) m ");
		buf.append(" ON m.send_user = d.send_user ");
		buf.append(" ) INNER JOIN USER u ON u.id = d.receive_user ");
		buf.append(" WHERE m.max_date = d.msg_date ");
		buf.append(" ORDER BY DM_ID_PTN ");
		buf.append(" ; ");

		//SQLをセット
		ps = con.prepareStatement(buf.toString());

		// ? にパラメータをセット
		ps.setString(1, LoginUser);

		//SQLの結果を取得
		rs = ps.executeQuery();

		//結果をさらに抽出
		while (rs.next()) {
			DmBean bean = new DmBean();

			bean.setMsgDate(rs.getString("msg_date"));
			bean.setMsg(rs.getString("msg"));
			bean.setSendUser(rs.getString("send_user"));
			bean.setReceiveUser(rs.getString("receive_user"));
			bean.setDmIdPtn(rs.getString("dm_id_ptn"));
			bean.setMsgToName(rs.getString("msg_to_name"));
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
	 *ログインしているユーザが最後に受信したメッセージを非ログインユーザごとに取得する
	 */
	public List<DmBean> selectReceiveMsgUserIdLastMsg(String LoginUser) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DmBean> beanList = new ArrayList<>();

	try {

		Class.forName(DbConst.DRIVER_NAME);

		//conにDB情報を入れる
		con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

		//SQL発行
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT d.msg_date, d.msg, d.send_user, d.receive_user, CONCAT(d.receive_user, d.send_user) AS DM_ID_PTN, u.name AS MSG_TO_NAME ");
		buf.append(" FROM ( DM d INNER JOIN ");
		buf.append(" (SELECT send_user, receive_user, MAX(msg_date) AS max_date FROM DM WHERE receive_user = ? GROUP BY send_user, receive_user) m ");
		buf.append(" ON m.receive_user = d.receive_user ");
		buf.append(" ) INNER JOIN USER u ON u.id = d.send_user ");
		buf.append(" WHERE m.max_date = d.msg_date ");
		buf.append(" ORDER BY DM_ID_PTN ");
		buf.append(" ; ");

		//SQLをセット
		ps = con.prepareStatement(buf.toString());

		// ? にパラメータをセット
		ps.setString(1, LoginUser);

		//SQLの結果を取得
		rs = ps.executeQuery();

		//結果をさらに抽出
		while (rs.next()) {
			DmBean bean = new DmBean();

			bean.setMsgDate(rs.getString("msg_date"));
			bean.setMsg(rs.getString("msg"));
			bean.setSendUser(rs.getString("send_user"));
			bean.setReceiveUser(rs.getString("receive_user"));
			bean.setDmIdPtn(rs.getString("dm_id_ptn"));
			bean.setMsgToName(rs.getString("msg_to_name"));
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
	 *ログインしているユーザと取得したいユーザ間のメッセージを取得する
	 */
	public List<DmBean> selectScheduleMonth(String sendUser, String receiveUser) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DmBean> beanList = new ArrayList<>();

	try {

		Class.forName(DbConst.DRIVER_NAME);

		//conにDB情報を入れる
		con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

		//SQL発行
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT a.msg AS MSG, b.name AS SEND_USER, c.name AS RECEIVE_USER, a.msg_date AS MSG_DATE ");
		buf.append(" FROM (DM a INNER JOIN USER b ON b.id = a.send_user) INNER JOIN USER c ON c.id = a.receive_user ");
		buf.append(" WHERE ( a.send_user = ? AND a.receive_user = ? ) OR ( a.send_user = ? AND a.receive_user = ? ) ");
		buf.append(" ORDER BY a.msg_date ");
		buf.append(" ; ");

		//SQLをセット
		ps = con.prepareStatement(buf.toString());

		// ? にパラメータをセット
		ps.setString(1, sendUser);
		ps.setString(2, receiveUser);
		ps.setString(3, receiveUser);
		ps.setString(4, sendUser);

		//SQLの結果を取得
		rs = ps.executeQuery();

		//結果をさらに抽出
		while (rs.next()) {
			DmBean bean = new DmBean();

			bean.setMsg(rs.getString("msg"));
			bean.setSendUser(rs.getString("send_user"));
			bean.setReceiveUser(rs.getString("receive_user"));
			bean.setMsgDate(rs.getString("msg_date"));
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
}
