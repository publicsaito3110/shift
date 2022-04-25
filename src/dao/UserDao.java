package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.UserBean;
import common.Const;


/**
 * @author saito
 *
 */
public class UserDao extends BaseDao {


	/**
	 * 未退職ユーザ取得処理
	 *
	 * <p>delFlgがないユーザ全員を取得する</p>
	 *
	 * @param void
	 * @return List<UserBean> ユーザID(id), ユーザ名(name)
	 */
	public List<UserBean> selectAllUserIdNameNotDelFlag() {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<UserBean> userList = new ArrayList<>();

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

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
	 * 未退職非ログインユーザ取得処理
	 *
	 * <p>delFlgがないユーザかつログインユーザ以外のユーザ全員を取得する</p>
	 *
	 * @param loginUser ログインユーザのID
	 * @return List<UserBean> ユーザID(id), ユーザ名(name)
	 */
	public List<UserBean> selectUserIdNameNotDelFlagLoginUser(String loginUser) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<UserBean> userList = new ArrayList<>();

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME ");
			buf.append(" FROM USER ");
			buf.append(" WHERE (DEL_FLG != '1' OR DEL_FLG IS NULL) AND ID != ?");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//   ? にパラメータをセット
			ps.setString(1, loginUser);

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
	 * ログイン情報検索処理
	 *
	 * <p>IDとパスワードから登録済みのユーザか検索する<br>
	 * ただし、IDとパスワードが一致したユーザがいなければ取得しない</p>
	 *
	 * @param userBean ユーザID(id), パスワード(password)
	 * @return UserBean ユーザID(id), 名前(name), 管理者フラグ(adminFlag)
	 */
	public UserBean selectUserIdNameAdmFlgLogin(UserBean userBean) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ情報を受け取るための変数
		UserBean dbBean = new UserBean();

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ID, NAME, ADMIN_FLG ");
			buf.append(" FROM USER ");
			buf.append(" WHERE ID = ? AND PASSWORD = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//   ? にパラメータをセット
			ps.setString(1, userBean.getId());
			ps.setString(2, userBean.getPassword());

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {

				dbBean.setId(rs.getString("id"));
				dbBean.setName(rs.getString("name"));
				dbBean.setAdminFlag(rs.getString("admin_flg"));
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

		return dbBean;
	}


	/**
	 * 該当ユーザ取得処理
	 *
	 * <p>キーワードに該当するユーザ全員を取得する<br>
	 * ただし、offsetの件数目から5件分を取得する<br>
	 * 該当結果がなければ取得しない</p>
	 *
	 * @param offset 表示する件目
	 * @param keyWord ユーザID,名前,フリガナにを対象にしたキーワード
	 * @return List<UserBean> ユーザID(id), 名前(name), フリガナ(kana), 性別(gender), 検索結果の一致件数(countAll)
	 */
	public List<UserBean> selectUserByKeyWord(int offset, String keyWord) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserBean> beanList = new ArrayList<UserBean>();

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

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
	 * ID一致ユーザ取得処理
	 *
	 * <p>idと一致する1人のユーザを取得する</p>
	 *
	 * @param String id, A user id
	 * @return UserBean, a user of muching id
	 */
	public UserBean selectUserOneById(String id) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//受け渡す値を格納する変数
		UserBean userBean = new UserBean();

		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

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

				userBean.setId(rs.getString("id"));
				userBean.setName(rs.getString("name"));
				userBean.setNameKana(rs.getString("name_kana"));
				userBean.setGender(rs.getString("gender"));
				userBean.setDelFlag(rs.getString("del_flg"));
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

		return userBean;
	}


	/**
	 * ユーザ新規登録処理
	 *
	 * <p>ユーザを新規登録する</p>
	 *
	 * @param userBean ユーザの新規追加情報(ID, NAME, NAME_KANA, GENDER, PASSWORD, ADDRESS, TEL, EMAIL, ADMIN_FLG, NOTE)
	 * @return boolean true:ユーザの新規登録が成功したとき false:ユーザの新規登録が失敗したとき
	 */
	public boolean insertUserSignup(UserBean userBean){

		//実行結果を取得
		boolean isSignupResult = false;

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
			buf.append(" USER ");
			buf.append(" (ID, NAME, NAME_KANA, GENDER, PASSWORD, ADDRESS, TEL, EMAIL, ADMIN_FLG, NOTE) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ; ");

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
			ps.setString(9, userBean.getAdminFlag());
			ps.setString(10, userBean.getNote());

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
	 * ユーザ更新処理
	 *
	 * <p>登録済みのユーザを更新する</p>
	 *
	 * @param userBean ユーザの更新情報(ID, NAME, NAME_KANA, GENDER, ADMIN_FLG, DEL_FLAG)
	 * @return boolean true:ユーザの更新が成功したとき false:ユーザの更新が失敗したとき
	 */
	public boolean updateUserDB(UserBean userBean){

		//実行結果を取得
		boolean isUpdateResult = false;

		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;


		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

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
