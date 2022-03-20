<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<body>

	<h3>ログインフォーム</h3>

	<form action="LoginServlet" method="post" >
		<p>ユーザーID:
			<input type ="text" name="userId"></input>
		</p>
		<p>パスワード:
			<input type="password" name="userPassword"></input>
		</p>
		<br>
		<input type="submit" value="ログイン"></input>
	</form>


	<p>${err}</p>

</body>
</html>