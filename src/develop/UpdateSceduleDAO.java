package develop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bean.ScheduleBean;

public class UpdateSceduleDAO {

	public boolean updateDB(List<ScheduleBean> updateList) {

		//JDBCの接続に使用するオブジェクトを宣言
		Connection con = null;
		PreparedStatement ps = null;

		//実行結果を返すための変数
		boolean updateResult = true;

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

			for(int i = 0; i <= updateList.size(); i++) {
				//SQL発行
				StringBuffer buf = new StringBuffer();
				buf.append(" UPDATE ");
				buf.append(" SCHEDULE ");
				buf.append(" SET ");
				buf.append(" MEMO1 ");
				buf.append(" = ");
				buf.append(" ? ");
				buf.append(" , ");
				buf.append(" MEMO2 ");
				buf.append(" = ");
				buf.append(" ? ");
				buf.append(" , ");
				buf.append(" MEMO3 ");
				buf.append(" = ");
				buf.append(" ? ");
				buf.append(" WHERE ");
				buf.append(" YMD ");
				buf.append(" = ");
				buf.append(" ? ");
				buf.append(" ; ");


				//SQLをセット
				ps = con.prepareStatement(buf.toString());


				// ? にパラメータをセット (insertList の memo1, memo2, memo3 の値が null でなければ ' ' をつける)
				if(updateList.get(yousositei).getMemo1() != null) {
					ps.setString( 1,  " ' " + updateList.get(yousositei).getMemo1() + " ' " );
				}else {
					ps.setString( 1, updateList.get(yousositei).getMemo1() );
				}

				if(updateList.get(yousositei).getMemo2() != null) {
					ps.setString( 2,  " ' " + updateList.get(yousositei).getMemo2() + " ' " );
				}else {
					ps.setString( 2, updateList.get(yousositei).getMemo2() );
				}

				if(updateList.get(yousositei).getMemo3() != null) {
					ps.setString( 3,  " ' " + updateList.get(yousositei).getMemo3() + " ' " );
				}else {
					ps.setString( 3, updateList.get(yousositei).getMemo3() );
				}

					ps.setString( 4, updateList.get(yousositei).getYmd() );


				//SQLを実行
				ps.executeUpdate();

				yousositei++;
			}

		} catch (SQLException e) {
			//SQLの実行失敗
			e.printStackTrace();
			updateResult = false;

		} finally {

			//トランザクションの終了

			if(updateResult){    //SQLの実行成功時、明示的にコミットを実施
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
		return updateResult;
	}
}
