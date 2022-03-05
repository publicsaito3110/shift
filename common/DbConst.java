package common;

public class DbConst {

	//DBの接続情報
	public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver"; //JDBCの相対パス
	public static final String JDBC_URL = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false"; //接続先のDB
	public static final String USER_ID = "test_user"; //DBのユーザーID
	public static final String USER_PASS = "test_pass"; //DBのユーザーPS

}
