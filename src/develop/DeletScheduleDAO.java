package develop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bean.ScheduleBean;

public class DeletScheduleDAO {
	public boolean deleteDB(List<ScheduleBean> deleteList) {

		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;

		//実行結果を返すための変数
		boolean deleteResult = true;

		//引数のListの要素を指定するための変数
		int yousositei = 0;

		//DBの接続情報
		String DRIVER_NAME = "com.mysql.cj.jdbc.Driver"; //JDBCの相対パス
		String JDBC_URL = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false"; //接続先のDB
		String USER_ID = "test_user"; //DBのユーザーID
		String USER_PASS = "test_pass"; //DBのユーザーPS

		//JDBCドライバーをロード
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			//conにDB情報を入れる
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			for(int i = 0; i <= deleteList.size(); i++) {
				//SQL発行
				StringBuffer buf = new StringBuffer();
				buf.append(" DELET ");
				buf.append(" SCHEDULE ");
				buf.append(" WHERE ");
				buf.append(" YMD ");
				buf.append(" = ");
				buf.append(" ' ");
				buf.append(" ? ");
				buf.append(" ' ");
				buf.append(" ; ");


				//SQLをセット
				ps = con.prepareStatement( buf.toString() );

				// ? にパラメータをセット bufに ' ' がついているので不要
				ps.setString( 1,deleteList.get(yousositei).getYmd() );

				//SQLを実行
				ps.executeUpdate();

				yousositei++;
			}

		} catch (SQLException e) {
			//SQLの実行失敗
			e.printStackTrace();
			deleteResult = false;

		} finally {

			//トランザクションの終了
			if(deleteResult){    //SQLの実行成功時、明示的にコミットを実施
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
		//実行結果を返す
		return deleteResult;
	}
}
