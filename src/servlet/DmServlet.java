package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DmServlet
 */
@WebServlet("/DmServlet")
public class DmServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public DmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		String sendUser = "A001";
		String receiveUser = "A002";


//		/**
//		 *ユーザを新規登録するメソッド
//		 */
//		public boolean insertUserSignup(UserBean userBean){
//
//			//実行結果を取得
//			boolean isSignupResult = false;
//
//			//JDBCの接続に使用するオブジェクトを宣言
//			Connection con = null;
//			PreparedStatement ps = null;
//
//
//			try {
//
//				Class.forName(DbConst.DRIVER_NAME);
//
//				//conにDB情報を入れる
//				con = DriverManager.getConnection(DbConst.JDBC_URL, DbConst.USER_ID, DbConst.USER_PASS);
//
//				//オートコミットをオフにする（トランザクション開始）
//				con.setAutoCommit(false);
//
//				//SQL発行
//				StringBuffer buf = new StringBuffer();
//				buf.append("INSERT INTO ");
//				buf.append(" USER ");
//				buf.append(" (ID, NAME, NAME_KANA, GENDER, PASSWORD, ADDRESS, TEL, EMAIL, ADMIN_FLG, NOTE) ");
//				buf.append(" VALUES ");
//				buf.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ; ");
//
//				//SQLをセット
//				ps = con.prepareStatement(buf.toString());
//
//				//パラメーターをセット
//				ps.setString(1, userBean.getId());
//				ps.setString(2, userBean.getName());
//				ps.setString(3, userBean.getNameKana());
//				ps.setString(4, userBean.getGender());
//				ps.setString(5, userBean.getPassword());
//				ps.setString(6, userBean.getAddress());
//				ps.setString(7, userBean.getTel());
//				ps.setString(8, userBean.getEmail());
//				ps.setString(9, userBean.getAdminFlag());
//				ps.setString(10, userBean.getNote());
//
//				//SQLを実行
//				ps.executeUpdate();
//
//				//SQL実行の成功結果を反映
//				isSignupResult = true;
//
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			} finally {
//
//				//トランザクションの終了
//				if(isSignupResult){    //SQLの実行成功時、明示的にコミットを実施
//					try {
//						con.commit();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}else{              //SQLの実行失敗時、明示的にロールバックを実施
//					try {
//						con.rollback();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//
//				//DBの接続解除
//				if (ps != null) {
//					try {
//						ps.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//			return isSignupResult;
//		}

	}

}
