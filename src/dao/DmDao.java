package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.DmBean;

public class DmDao extends BaseDao {



	/**
	 * 最後のデータ取得処理
	 * <br>
	 * ログインしているユーザの最後のメッセージを他のユーザごとに取得する
	 * <br>
	 *@param loginUser ログインユーザー
	 *@return List<DmBean>
	 */
	public List<DmBean> selectLastMsgByLoginId(String loginUser) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DmBean> beanList = new ArrayList<>();

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

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

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

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
				StringBuffer sb = new StringBuffer();

				//msg_dateの値をsbに代入し、不要な値(年とミリ秒)を削除
				sb.append(rs.getString("msg_date"));
				sb.delete(0, 5);
				sb.replace(2, 3, "/");
				sb.delete(11, 16);

				bean.setMsgDate(sb.toString());
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


	/**
	 * 送信されたメッセージを追加する
	 * @param DmBean msgBean, Send mssage and loginUser and To user
	 * @return boolean, Return true in insert message
	 */
	public boolean insertSendMsgByMsgBean(DmBean msgBean){

		//実行結果を取得
		boolean isResult = false;

		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL発行
			StringBuffer buf = new StringBuffer();

			buf.append("INSERT INTO ");
			buf.append(" DM ");
			buf.append(" (MSG, SEND_USER, RECEIVE_USER, MSG_DATE) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?, ?, NOW()) ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ?にパラメーターをセット
			ps.setString(1, msgBean.getMsg());
			ps.setString(2, msgBean.getSendUser());
			ps.setString(3, msgBean.getReceiveUser());

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
