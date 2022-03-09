package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UserInfoBean;
import common.DbConst;

public class UserInfoDao {


	//-------登録済みのユーザを取得するメソッド---------------------------------------------------
	public List<UserInfoBean> selectUserInfoDB() {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserInfoBean> beanList = new ArrayList<UserInfoBean>();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME, NAME_KANA, GENDER FROM USER ORDER BY ID");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				UserInfoBean bean = new UserInfoBean();
				//ymdからday部分のみを抽出し、beanにsetする
				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				bean.setNameKana(rs.getString("name_kana"));
				bean.setGender(rs.getString("gender"));

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



	//----------ユーザを新規登録するメソッド--------------------------------------------------
	public boolean sighnUpUserDB(List<UserInfoBean> userInfoList){

		//実行結果を取得
		boolean sighnUpResult = false;

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
			buf.append(" USER ");
			buf.append(" (ID, NAME, NAME_KANA, GENDER) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?, ?, ?) ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//パラメーターをセット
			ps.setString(1, userInfoList.get(0).getId());
			ps.setString(2, userInfoList.get(0).getName());
			ps.setString(3, userInfoList.get(0).getNameKana());
			ps.setString(4, userInfoList.get(0).getGender());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			sighnUpResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(sighnUpResult){    //SQLの実行成功時、明示的にコミットを実施
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

		return sighnUpResult;
	}
}
