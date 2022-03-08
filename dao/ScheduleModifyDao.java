package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bean.ScheduleBean;
import common.DbConst;

public class ScheduleModifyDao {

	public boolean modifyScheduleDB(List<ScheduleBean> modifyList){

		//実行結果を取得
		boolean modifyResult = false;

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

			//INSERT, UPDATE, DELETE で分ける
			switch(modifyList.get(0).getTypeIUD()){

			case "I":                         //INSERTのとき
				buf.append("INSERT INTO ");
				buf.append(" SCHEDULE ");
				buf.append(" (YMD,MEMO1,MEMO2,MEMO3) ");
				buf.append(" VALUES ");
				buf.append(" (?, ?, ?, ?) ");

				break;

			case "U":                          //UPDATEのとき
				buf.append("UPDATE ");
				buf.append(" SCHEDULE ");
				buf.append(" SET ");
				buf.append(" MEMO1 = ?,");
				buf.append(" MEMO2 = ?,");
				buf.append(" MEMO3 = ? ");
				buf.append(" WHERE YMD = ? ");
				buf.append(" ; ");

				break;

			case "D":                          //DELETEのとき
				buf.append("DELETE ");
				buf.append(" FROM ");
				buf.append(" SCHEDULE ");
				buf.append(" WHERE YMD = ? ");
				buf.append(" ; ");

				break;

			}

			//SQLをセット
			ps = con.prepareStatement(buf.toString());

			//INSERT, UPDATE, DELETE を判別して ? にパラメータをセット
			switch(modifyList.get(0).getTypeIUD()){

			case "I":                         //INSERTのとき
				ps.setString(1, modifyList.get(0).getYmd());
				ps.setString(2, modifyList.get(0).getMemo1());
				ps.setString(3, modifyList.get(0).getMemo2());
				ps.setString(4, modifyList.get(0).getMemo3());

				break;

			case "U":                          //UPDATEのとき
				ps.setString(1, modifyList.get(0).getMemo1());
				ps.setString(2, modifyList.get(0).getMemo2());
				ps.setString(3, modifyList.get(0).getMemo3());
				ps.setString(4, modifyList.get(0).getYmd());

				break;

			case "D":                          //DELETEのとき
				ps.setString(1, modifyList.get(0).getYmd());

				break;

			}


			//SQLを実行
			ps.executeUpdate();

			//SQL実行の成功結果を反映
			modifyResult = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			//トランザクションの終了
			if(modifyResult){    //SQLの実行成功時、明示的にコミットを実施
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

		return modifyResult;
	}
}
