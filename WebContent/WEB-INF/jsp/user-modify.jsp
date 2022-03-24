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

	<form action="UserModifyServlet" method="post">
		<table border="1" cellspacing="0">
			<tr>
				<th><p>ID</p></th>
				<td>
					<input type="hidden" name="id" value="${id}">${id}<span>${erId}</span>
				</td>
			</tr>
			<tr>
				<th><p>名前 <span class="text-red">※</span></p></th>
				<td>
					<input type="text" name="name" placeholder="${name}" value="${name}"><span>${erName}</span>
				</td>
			</tr>
			<tr>
				<th>フリガナ(任意)</th>
				<td>
					<input type="text" name="nameKana" placeholder="${nameKana}" value="${nameKana}"><span>${erNameKana}</span>
				</td>
			</tr>
			<tr>
				<th>性別</th>
				<td>
					  <%--デフォルトは男性にチェックし、女性のときチェックされる --%>
					<input type="radio" name="gender" value="1" checked>男
					<input type="radio" name="gender" value="2" ${checkGender2}>女
					<span>${erGender}</span>
				</td>
			</tr>
			<tr>
				<th>退職済み</th>
				<td>
					<input type="checkbox" name="delFlag" value="1" ${checkDelFlag}><span>${erDelFlag}</span>
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" value="更新する">
	</form>
	<br>
	<a href="UserListServlet">ユーザー一覧へ</a>


	<%--ポップアップウィンドウ  Form送信後に自動的に表示 --%>
	<jsp:include page="pop-window.jsp" flush="true" />


</body>