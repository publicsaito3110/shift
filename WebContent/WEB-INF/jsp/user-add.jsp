<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/mordal.css">
</head>
<body>
	<jsp:include page="header.jsp" flush="true" />

	<h1>ユーザー登録</h1>

	<form action="UserAddServlet" method="post">
		<table border="1" cellspacing="0">
			<tr>
				<th><span>ID</span> <span class="text-red">※</span></th>
				<td>
					<input type="text" name="id" value="${id}" placeholder="A123"><span>${erId}</span>
				</td>
			</tr>
			<tr>
				<th><span>名前</span> <span class="text-red">※</span></th>
				<td>
					<input type="text" name="name" value="${name}" placeholder="田中太郎"><span>${erName}</span>
				</td>
			</tr>
			<tr>
				<th>フリガナ(任意)</th>
				<td>
					<input type="text" name="nameKana" value="${nameKana}" placeholder="タナカタロウ"><span>${erNameKana}</span>
				</td>
			</tr>
			<tr>
				<th>性別</th>
				<td>
					<input type="radio" name="gender" value="1" checked>男
					<input type="radio" name="gender" value="2">女
					<span>${erGender}</span>
				</td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td>
					<input type="password" name="password" value="${password}"><span>${erPassword}</span>
				</td>
			</tr>
			<tr>
				<th>住所</th>
				<td>
					<input type="text" name="address" value="${address}" placeholder="東京都新宿区○○町1-1"><span>${erAddress}</span>
				</td>
			</tr>
			<tr>
				<th>TEL</th>
				<td>
					<input type="tel" name="tel" value="${tel}" placeholder="01234567890"><span>${erTel}</span>
				</td>
			</tr>
			<tr>
				<th>メール</th>
				<td>
					<input type="email" name="email" value="${email}"><span>${erEmail}</span>
				</td>
			</tr>
			<tr>
				<th>備考</th>
				<td>
					<input type="text" name="note" value="${note}"><span>${erNote}</span>
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" value="登録">
	</form>
	<br>
	<a href="UserListServlet">ユーザー一覧へ</a>


	<%--ポップアップウィンドウ  Form送信後に自動的に表示 --%>
	<jsp:include page="pop-window.jsp" flush="true" />

</body>


</html>