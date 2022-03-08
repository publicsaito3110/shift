<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<body>

	<h3>ログインフォーム</h3>

	<form action="LogInServlet" method="post" >
		<p>ユーザーID:
			<input type ="text" name="USER_ID"></input>
		</p>
		<p>ユーザーパスワード:
			<input type="text" name="USER_PASSWORD"></input>
		</p>
		<br>
		<input type="submit" value="ログイン"></input>
	</form>

<style>

</style>


</body>
</html>