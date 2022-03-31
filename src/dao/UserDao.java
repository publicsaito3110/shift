package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import common.Const;
import common.DbConst;


public class UserDao {


	/**
	 *delFlgがないユーザ名,idを取得するメソッド
	 */
	public List<UserBean> selectUserIdNameNotDelFlag() {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<UserBean> userList = new ArrayList<>();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME ");
			buf.append(" FROM USER ");
			buf.append(" WHERE DEL_FLG != '1' OR DEL_FLG IS NULL ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//SQLの結果を取得
			rs = ps.executeQuery();


			//結果をさらに抽出
			while (rs.next()) {
				UserBean bean = new UserBean();

				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				userList.add(bean);
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

		return userList;
	}



	/**
	 *1人のユーザ名を取得するメソッド
	 */
	public String selectUserLogin(List<UserBean> userList) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		String name = null;

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT NAME ");
			buf.append(" FROM USER ");
			buf.append(" WHERE ID = ? AND PASSWORD = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//   ? にパラメータをセット
			ps.setString(1, userList.get(0).getId());
			ps.setString(2, userList.get(0).getPassword());


			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				name = rs.getString("name");
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

		return name;
	}


	/**
	 * 登録済みのユーザ全員を取得するメソッド
	 */
	public List<UserBean> selectUserAll(int offset) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserBean> beanList = new ArrayList<UserBean>();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME, NAME_KANA, GENDER, (SELECT COUNT(ID) FROM USER) AS COUNT ");
			buf.append(" FROM USER ");
			buf.append(" ORDER BY ID ");
			buf.append(" LIMIT ");
			buf.append( Const.PAGE_LIMIT );
			buf.append(" OFFSET  ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setInt(1, offset);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				UserBean bean = new UserBean();

				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				bean.setNameKana(rs.getString("name_kana"));
				bean.setGender(rs.getString("gender"));
				bean.setCountAll(rs.getString("count"));

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
	 *キーワードに該当するユーザ全員を取得するメソッド
	 */
	public List<UserBean> selectUserByKeyWord(int offset, String keyWord) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserBean> beanList = new ArrayList<UserBean>();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME, NAME_KANA, GENDER ");
			buf.append(", (SELECT COUNT(ID) FROM USER WHERE ID LIKE ? OR NAME LIKE ? OR NAME_KANA LIKE ? ) AS COUNT ");
			buf.append(" FROM USER ");
			buf.append(" WHERE ID LIKE ? OR NAME LIKE ? OR NAME_KANA LIKE ? ");
			buf.append(" ORDER BY ID ");
			buf.append(" LIMIT ");
			buf.append( Const.PAGE_LIMIT );
			buf.append(" OFFSET  ? ");
			buf.append(" ; ");


			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			String paramKeyWord = Const.PERCENT + keyWord + Const.PERCENT;

			ps.setString(1, paramKeyWord);
			ps.setString(2, paramKeyWord);
			ps.setString(3, paramKeyWord);
			ps.setString(4, paramKeyWord);      //keyWordが""のとき
			ps.setString(5, paramKeyWord);      //WHERE * LIKE '%%' となり WHERE * と同様の結果になる
			ps.setString(6, paramKeyWord);      //(MySQLの場合)
			ps.setInt(7, offset);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				UserBean bean = new UserBean();
				//ymdからday部分のみを抽出し、beanにsetする
				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				bean.setNameKana(rs.getString("name_kana"));
				bean.setGender(rs.getString("gender"));
				bean.setCountAll(rs.getString("count"));

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
	 *1人のユーザを取得するメソッド
	 */
	public List<UserBean> selectUserOne(String id) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserBean> beanList = new ArrayList<UserBean>();

		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME, NAME_KANA, GENDER, DEL_FLG FROM USER WHERE ID = ? ORDER BY ID");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//   ? にパラメータをセット
			ps.setString(1, id);


			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				UserBean bean = new UserBean();
				//ymdからday部分のみを抽出し、beanにsetする
				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				bean.setNameKana(rs.getString("name_kana"));
				bean.setGender(rs.getString("gender"));
				bean.setDelFlag(rs.getString("del_flg"));

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
	 *ユーザを新規登録するメソッド
	 */
	public boolean insertUserSignup(UserBean userBean){

		//実行結果を取得
		boolean isSignupResult = false;

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
			buf.append(" (ID, NAME, NAME_KANA, GENDER, PASSWORD, ADDRESS, TEL, EMAIL, NOTE) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?) ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//パラメーターをセット
			ps.setString(1, userBean.getId());
			ps.setString(2, userBean.getName());
			ps.setString(3, userBean.getNameKana());
			ps.setString(4, userBean.getGender());
			ps.setString(5, userBean.getPassword());
			ps.setString(6, userBean.getAddress());
			ps.setString(7, userBean.getTel());
			ps.setString(8, userBean.getEmail());
			ps.setString(9, userBean.getNote());

			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			isSignupResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(isSignupResult){    //SQLの実行成功時、明示的にコミットを実施
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

		return isSignupResult;
	}


	/**
	 *ユーザ情報を更新するメソッド
	 */
	public boolean updateUserDB(UserBean userBean){

		//実行結果を取得
		boolean isUpdateResult = false;

		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;


		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//オートコミットをオフにする（トランザクション開始)
			con.setAutoCommit(false);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("UPDATE ");
			buf.append(" USER ");
			buf.append(" SET ");
			buf.append(" NAME = ?, ");
			buf.append(" NAME_KANA = ?, ");
			buf.append(" GENDER = ? ,");
			buf.append(" DEL_FLG = ? ");
			buf.append(" WHERE ID = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//パラメーターをセット
			ps.setString(1, userBean.getName());
			ps.setString(2, userBean.getNameKana());
			ps.setString(3, userBean.getGender());
			ps.setString(4, userBean.getDelFlag());
			ps.setString(5, userBean.getId());


			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			isUpdateResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(isUpdateResult){    //SQLの実行成功時、明示的にコミットを実施
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

		return isUpdateResult;
	}

}