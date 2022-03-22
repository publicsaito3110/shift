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

	<form action="UserInfoChangeServlet" method="post">
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
					  <%--入力値(男か女)によってradioのチェックを分岐 --%>
							<input type="radio" name="gender" value="1" ${checkdGender1} >男
							<input type="radio" name="gender" value="2" ${checkdGender2} >女

					<c:choose>
						<c:when test="${gender == 2}">
							<input type="radio" name="gender" value="1">男
							<input type="radio" name="gender" value="2" checked>女
						</c:when>
						<c:otherwise>
							<input type="radio" name="gender" value="1" checked>男
							<input type="radio" name="gender" value="2">女
						</c:otherwise>
					</c:choose>
					<span>${erGender}</span>
				</td>
			</tr>
			<tr>
				<th>退職済み</th>
				<td>
					  <%--delFlagの有無でチェック済みかを分岐 --%>
					<c:choose>
						<c:when test="${delFlag == 1}">
							<input type="checkbox" name="delFlag" value="1" checked><span>${erDelFlag}</span>
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="delFlag" value="1"><span>${erDelFlag}</span>
						</c:otherwise>
					</c:choose>
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