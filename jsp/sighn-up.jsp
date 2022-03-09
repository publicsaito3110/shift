<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="header.jsp" flush="true" />

	<h1>ユーザー登録</h1>

	<form action="UserSighnUpServlet" method="post">
		<table border="1" cellspacing="0">
			<tr>
				<th><span>ID</span> <span class="required">※</span></th>
				<td><input type="text" name="id" placeholder="英数字4桁"></td>
			</tr>
			<tr>
				<th><span>名前</span> <span class="required">※</span></th>
				<td><input type="text" name="user" placeholder="田中太郎"></td>
			</tr>
			<tr>
				<th>カナ</th>
				<td><input type="text" name="userKana" placeholder="タナカタロウ"></td>
			</tr>
			<tr>
				<th>性別</th>
				<td><input type="radio" name="gender" value="1" checked>男
					<input type="radio" name="gender" value="2">女</td>
			</tr>
		</table>
		<input type="submit" value="登録">
	</form>
	<br>
	<a href="UserServlet">ユーザー一覧へ</a>

</body>
<style>

.required{
	color: red;
}

</style>

</html>