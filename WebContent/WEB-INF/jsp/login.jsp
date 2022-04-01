<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<body>

	<h3>ログインフォーム</h3>


	<p>${err}</p>
	<form action="LoginServlet" method="post" >
		<p>ユーザーID:
			<input type ="text" name="id"></input>
		</p>
		<p>パスワード:
			<input type="password" name="password"></input>
		</p>
		<br>
		<input type="submit" value="ログイン"></input>
	</form>


</body>
</html>