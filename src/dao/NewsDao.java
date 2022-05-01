package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.NewsBean;
import common.Const;

/**
 * @author saito
 *
 */
public class NewsDao extends BaseDao {


	/**
	 * 現在日までお知らせ取得処理
	 *
	 * <p>現在までの日付のお知らせを最大5件まで現在日に近い順で取得する</p>
	 *
	 * @param nowYmd 現在の日付(YYYYMMDD)
	 * @return List<NewsBean> お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 */
	public List<NewsBean> selectNewsBeforeNowByNowYmd(String nowYmd) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<NewsBean> newsList = new ArrayList<>();


		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT YMD, CATEGORY, TITLE, CONTENT ");
			buf.append(" FROM NEWS ");
			buf.append(" WHERE YMD <= ? ");
			buf.append(" ORDER BY YMD DESC ");
			buf.append(" LIMIT ");
			buf.append( Const.PAGE_LIMIT );
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, nowYmd);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				NewsBean bean = new NewsBean();

				bean.setYmd(rs.getString("ymd"));
				bean.setCategory(rs.getString("category"));
				bean.setTitle(rs.getString("title"));
				bean.setContent(rs.getString("content"));
				newsList.add(bean);
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

		return newsList;
	}


	/**
	 * 登録済みお知らせ取得処理
	 *
	 * <p>現在日以降に登録されているお知らせを全て日付順で取得する</p>
	 *
	 * @param nowYmd 現在の日付(YYYYMMDD)
	 * @return List<NewsBean> お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 */
	public List<NewsBean> selectAllNewsAfterNowByNowYmd(String nowYmd) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<NewsBean> newsList = new ArrayList<>();


		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/datasource");
			con = ds.getConnection();

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT YMD, CATEGORY, TITLE, CONTENT ");
			buf.append(" FROM NEWS ");
			buf.append(" WHERE ? < YMD ");
			buf.append(" ORDER BY YMD ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ? にパラメータをセット
			ps.setString(1, nowYmd);

			//SQLの結果を取得
			rs = ps.executeQuery();

			//結果をさらに抽出
			while (rs.next()) {
				NewsBean bean = new NewsBean();

				bean.setYmd(rs.getString("ymd"));
				bean.setCategory(rs.getString("category"));
				bean.setTitle(rs.getString("title"));
				bean.setContent(rs.getString("content"));
				newsList.add(bean);
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

		return newsList;
	}


	/**
	 * お知らせ新規登録処理
	 *
	 * <p>指定された日付に表示するお知らせを新規登録する</p>
	 *
	 * @param newsBean お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 * @return boolean true:お知らせの追加が成功したとき false:お知らせの追加が失敗したとき
	 */
	public boolean insertNewNews(NewsBean newsBean){

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
			buf.append(" NEWS ");
			buf.append(" (YMD, CATEGORY, TITLE, CONTENT) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?, ?, ?) ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ?にパラメーターをセット
			ps.setString(1, newsBean.getYmd());
			ps.setString(2, newsBean.getCategory());
			ps.setString(3, newsBean.getTitle());
			ps.setString(4, newsBean.getContent());

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
	 * お知らせ修正処理
	 *
	 * <p>指定された日付のお知らせを修正する</p>
	 *
	 * @param newsBean お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 * @return boolean true:お知らせの修正が成功したとき false:お知らせの修正が失敗したとき
	 */
	public boolean updateNews(NewsBean newsBean){

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

			buf.append("UPDATE NEWS ");
			buf.append(" SET ");
			buf.append(" CATEGORY = ?, ");
			buf.append(" TITLE = ?, ");
			buf.append(" CONTENT = ? ");
			buf.append(" WHERE YMD = ? ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ?にパラメーターをセット
			ps.setString(1, newsBean.getCategory());
			ps.setString(2, newsBean.getTitle());
			ps.setString(3, newsBean.getContent());
			ps.setString(4, newsBean.getYmd());

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