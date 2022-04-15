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
	 *ログインしているユーザの最後のメッセージを他のユーザごとに取得する
	 *@param String loginUser, Got id by session
	 *@return List<DmBean>, Get finsl message per user
	 */
	public List<DmBean> selectLastMsgByLoginId(String loginUser) {


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
			buf.append("SELECT a.* ");
			buf.append(" FROM ( ");
			buf.append(" SELECT DISTINCT d.msg_date, d.msg, d.send_user, d.receive_user ");
			buf.append(" , CASE WHEN d.send_user = ? THEN s.name WHEN d.receive_user = ? THEN u.name END AS MSG_TO_NAME ");
			buf.append(" , CASE WHEN d.send_user = ? THEN s.id WHEN d.receive_user = ? THEN u.id END AS MSG_TO_ID ");
			buf.append(" FROM (DM d INNER JOIN USER u ON u.id = d.send_user) INNER JOIN USER s ON s.id = d.receive_user  ");
			buf.append(" WHERE d.send_user = ? OR d.receive_user = ? ");
			buf.append(" ORDER BY d.id DESC ");
			buf.append(" ) a ");
			buf.append(" GROUP BY a.MSG_TO_ID ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, loginUser);
			ps.setString(2, loginUser);
			ps.setString(3, loginUser);
			ps.setString(4, loginUser);
			ps.setString(5, loginUser);
			ps.setString(6, loginUser);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				DmBean bean = new DmBean();

				bean.setMsgDate(rs.getString("msg_date"));
				bean.setMsg(rs.getString("msg"));
				bean.setSendUser(rs.getString("send_user"));
				bean.setReceiveUser(rs.getString("receive_user"));
				bean.setMsgToName(rs.getString("msg_to_name"));
				bean.setMsgToId(rs.getString("msg_to_id"));
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
	 *ユーザ同士のメッセージを取得する
	 *@param String loginUser, String user, Got id by session and get form
	 *@return List<DmBean>, Get talk message
	 */
	public List<DmBean> selectTalkByUser(String loginUser, String user) {


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
			buf.append("SELECT d.msg_date, d.msg, CASE WHEN d.send_user = ? THEN 'LOGIN_USER' WHEN d.send_user != ? THEN 'NOT_LOGIN_USER' END AS SEND_USER ");
			buf.append(" FROM DM d ");
			buf.append(" WHERE d.send_user = ? AND d.receive_user = ? OR d.send_user = ? AND d.receive_user = ? ");
			buf.append(" ORDER BY d.id ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, loginUser);
			ps.setString(2, loginUser);
			ps.setString(3, loginUser);
			ps.setString(4, user);
			ps.setString(5, user);
			ps.setString(6, loginUser);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				DmBean bean = new DmBean();

				bean.setMsgDate(rs.getString("msg_date"));
				bean.setMsg(rs.getString("msg"));
				bean.setSendUser(rs.getString("send_user"));
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
}
