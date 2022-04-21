package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.NewsBean;
import common.Const;
import common.DbConst;

public class NewsDao{


	/**
	 *指定された日付の範囲内のお知らせを取得する
	 *@param String nowYmd, Date of today is state of YYYYMMDD
	 *@return List<NewsBean>, Get news until today
	 */
	public List<NewsBean> selectNews(String nowYmd) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<NewsBean> newsList = new ArrayList<>();


		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT YMD, CONTENT ");
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
	 *登録されているお知らせを全て取得する
	 *@param String nowYmd, Date of today is state of YYYYMMDD
	 *@return List<NewsBean>, Get news until today
	 */
	public List<NewsBean> selectAllNews(String nowYmd) {


		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//ユーザ名を受け取るための変数(nullを宣言しておく)
		List<NewsBean> newsList = new ArrayList<>();


		try {

			Class.forName(DbConst.DRIVER_NAME);

			//conにDB情報を入れる
			con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);

			//SQL発行
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT YMD, CONTENT ");
			buf.append(" FROM NEWS ");
			buf.append(" WHERE YMD <= ? ");
			buf.append(" ORDER BY YMD DESC ");
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
	 *指定された日付のお知らせを登録する
	 * @param NewsBean newsBean, you wont to add news of date and content
	 * @return boolean, Return true in insert news
	 */
	public boolean insertNews(NewsBean newsBean){

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
			buf.append(" NEWS ");
			buf.append(" (YMD, CONTENT) ");
			buf.append(" VALUES ");
			buf.append(" (?, ?) ");
			buf.append(" ; ");

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			// ?にパラメーターをセット
			ps.setString(1, newsBean.getYmd());
			ps.setString(2, newsBean.getContent());

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
